/*
 * 项目名称:platform-plus
 * 类名称:OrderTask.java
 * 包名称:com.platform.modules.job.task
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2019/7/19 11:04    tp      初版完成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.job.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.OrderStatus;
import com.platform.common.OrderType;
import com.platform.common.utils.HttpUtils;
import com.platform.common.utils.StringUtils;
import com.platform.modules.iot.IotService;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.entity.OrderStatusFlowEntity;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.order.service.OrderStatusFlowService;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务
 * confirmOrderTask为spring bean的名称
 *
 * @author zlm
 */
@Slf4j
@Component("confirmOrderTask")
public class ConfirmOrderTask {

	@Value("${sy-wms.base-url}")
	private String baseUrl;
	@Value("${sy-wms.name}")
	private String name;
	@Value("${sy-wms.token}")
	private String token;

	@Autowired
	private OrderHeaderInfoService orderHeaderInfoService;
	@Autowired
	private OrderStatusFlowService orderStatusFlowService;
	@Autowired
	private IotService iotService;

	/**
	 * 订单定时任务，到仓库查询相应的订单信息
	 */
	public void confirmOrder() {
		log.error("--------------- START JOB --------------");
		List<OrderHeaderInfoEntity> orderHeaderInfoEntityList = orderHeaderInfoService.findNewOrderList();
		// 遍历订单集合
		for (OrderHeaderInfoEntity order : orderHeaderInfoEntityList) {
			// 查询货单信息
			JSONObject rDate = getPurchaseOrder(order.getOrderType(), order.getDynamic(), order.getBuyerCode());
			// 判断是否审核成功
			if (rDate != null && "审核成功".equals(rDate.getString("StatusText"))) {
				// 审核成功，改变订单状态为确认订单
				updateOrderStatue(order.getOrderItem(), order.getOrderType(), "1");
				log.error("订单【"+order.getOrderItem()+"】完成确认。");
				iotService.orderSyn(order);
			}
		}
		log.error("--------------- END JOB --------------");
	}

	/**
	 * 查询货单信息
	 *
	 * @param orderType 订单类型
	 * @param dynamic   订单号
	 * @param buyerCode 买家编码
	 * @return Object
	 */
	private JSONObject getPurchaseOrder(String orderType, String dynamic, String buyerCode) {
		try {
			// 接口地址
			String api;
			if ("R".equals(orderType)) {
				// 入库单
				api = "/api/Purchase_Order/GetByCode";
			} else {
				// 出库单
				api = "/api/Sale_Order/GetByCode";
			}
			// 请求地址
			String requestUrl = baseUrl + api + "?OrderCode=" + dynamic;
			// 请求参数
			JSONObject params = new JSONObject();
			params.put("name", name);
			params.put("token", buyerCode);
			// 发送请求
			String rData = HttpUtils.sendPost(requestUrl, "", params);
			if (StringUtils.isNotBlank(rData)) {
				return JSONObject.parseObject(rData);
			}
		} catch (Exception e) {
			log.error("[getPurchaseOrder]", e);
		}
		return null;
	}

	/**
	 * 修改订单状态为确认订单
	 *
	 * @param orderItem 工作单号
	 * @param orderType 订单类型
	 * @param userId 用户ID
	 */
	public void updateOrderStatue(String orderItem, String orderType, String userId) {
		// 订单信息
		Date nowDate = new Date();
		OrderHeaderInfoEntity order = new OrderHeaderInfoEntity();
		order.setOrderItem(orderItem);
		order.setStatusUpdte(nowDate);
		// 状态记录
		OrderStatusFlowEntity status = new OrderStatusFlowEntity();
		status.setCreatedBy(userId);
		status.setCreatedTime(nowDate);
		status.setOrderItem(orderItem);
		status.setStatusDate(nowDate);
		if (OrderType.I_.equals(orderType)) {
			// 出库订单
			order.setStatus(OrderStatus.OutConfirmOder.getCode());
			order.setStatusName(OrderStatus.OutConfirmOder.getStatusName());
			status.setStatusCode(OrderStatus.OutConfirmOder.getCode());
			status.setOrderStatus(OrderStatus.OutConfirmOder.getStatusName());
		} else {
			// 入库订单
			order.setStatus(OrderStatus.InConfirmOder.getCode());
			order.setStatusName(OrderStatus.InConfirmOder.getStatusName());
			status.setStatusCode(OrderStatus.InConfirmOder.getCode());
			status.setOrderStatus(OrderStatus.InConfirmOder.getStatusName());
		}
		// 修改订单表状态信息
		orderHeaderInfoService.updateStatusByOrderNo(order);
		// 添加状态信息记录
		orderStatusFlowService.add(status);
	}
}
