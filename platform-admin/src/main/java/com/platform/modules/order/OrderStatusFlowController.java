/*
 * 项目名称:platform-plus
 * 类名称:OrderStatusFlowController.java
 * 包名称:com.platform.modules.order.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:31        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.RestResponse;
import com.platform.e6yun.service.E6yunService;
import com.platform.modules.order.entity.OrderStatusFlowEntity;
import com.platform.modules.order.entity.OrderTrackInfoEntity;
import com.platform.modules.order.service.OrderStatusFlowService;
import com.platform.modules.order.service.OrderTrackInfoService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单状态流水表 Controller
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:31
 */
@RestController
@RequestMapping("order/statusflow")
public class OrderStatusFlowController extends AbstractController {
	@Autowired
	private OrderStatusFlowService orderStatusFlowService;
	@Autowired
	private E6yunService e6yunService;
	@Autowired
	private OrderTrackInfoService orderTrackInfoService;

	/**
	 * 查看所有列表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@RequestMapping("/queryAll")
	public RestResponse queryAll(@RequestParam Map<String, Object> params) {
		List<OrderStatusFlowEntity> list = orderStatusFlowService.queryAll(params);

		return RestResponse.success().put("list", list);
	}

	/**
	 * 分页查询订单状态流水表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@GetMapping("/list")
	@RequiresPermissions("order:statusflow:list")
	public RestResponse list(@RequestParam Map<String, Object> params) {
		Page page = orderStatusFlowService.queryPage(params);

		return RestResponse.success().put("page", page);
	}

	/**
	 * 根据主键查询详情
	 *
	 * @param id 主键
	 * @return RestResponse
	 */
	@RequestMapping("/info/{id}")
	public RestResponse info(@PathVariable("id") String id) {
		OrderStatusFlowEntity orderStatusFlow = orderStatusFlowService.getById(id);

		return RestResponse.success().put("statusflow", orderStatusFlow);
	}

	/**
	 * 新增订单状态流水表
	 *
	 * @param orderStatusFlow orderStatusFlow
	 * @return RestResponse
	 */
	@SysLog("新增订单状态流水表 ")
	@RequestMapping("/save")
	@RequiresPermissions("order:statusflow:save")
	public RestResponse save(@RequestBody OrderStatusFlowEntity orderStatusFlow) {

		orderStatusFlowService.add(orderStatusFlow);

		return RestResponse.success();
	}

	/**
	 * 修改订单状态流水表
	 *
	 * @param orderStatusFlow orderStatusFlow
	 * @return RestResponse
	 */
	@SysLog("修改订单状态流水表 ")
	@RequestMapping("/update")
	public RestResponse update(@RequestBody OrderStatusFlowEntity orderStatusFlow) {

		orderStatusFlowService.update(orderStatusFlow);

		return RestResponse.success();
	}

	/**
	 * 根据主键删除订单状态流水表
	 *
	 * @param ids ids
	 * @return RestResponse
	 */
	@SysLog("删除订单状态流水表 ")
	@RequestMapping("/delete")
	@RequiresPermissions("order:statusflow:delete")
	public RestResponse delete(@RequestBody String[] ids) {
		orderStatusFlowService.deleteBatch(ids);

		return RestResponse.success();
	}

	/**
	 * 获取车辆轨迹信息
	 *
	 * @param params 参数
	 * @return
	 */
	@SysLog("获取车辆轨迹信息")
	@RequestMapping("/getTrackDetail")
	public RestResponse getTrackDetail(@RequestParam Map<String, String> params) {
		JSONObject data = e6yunService.getTrackDetail(params);
		return RestResponse.success().put("data", data);
	}

	/**
	 * 追踪宝轨迹信息，以地图显示的url地址
	 *
	 * @param params 参数
	 * @return map url
	 */
	@SysLog("追踪宝轨迹信息，以地图显示的url地址")
	@RequestMapping("/createMapUrl")
	public RestResponse createMapUrl(@RequestParam Map<String, String> params) {
		String orderItem = params.get("orderItem");
		OrderTrackInfoEntity info = orderTrackInfoService.getByOrderItem(orderItem);
		String url;
		if (info != null) {
			String startTime;
			String endTime;
			if (info.getStartTime() != null) {
				startTime = DateUtils.format(info.getStartTime(), DateUtils.DATE_TIME_PATTERN);
			} else {
				startTime = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);
			}
			if (info.getEndTime() != null) {
				endTime = DateUtils.format(info.getEndTime(), DateUtils.DATE_TIME_PATTERN);
			} else {
				endTime = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);
			}
			url = e6yunService.createMapUrl(info.getDeviceCode(), startTime, endTime);
		} else {
			// 模拟数据
			Date now = new Date();
			url = e6yunService.createMapUrl("863014532875684",
					DateUtils.format(now, DateUtils.DATE_TIME_PATTERN),
					DateUtils.format(now, DateUtils.DATE_TIME_PATTERN));
			logger.error("工作单：" + orderItem + "未获取去到轨迹信息");
		}
		return RestResponse.success().put("url", url);
	}
}
