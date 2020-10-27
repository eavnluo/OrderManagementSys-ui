/*
 * 项目名称:platform-plus
 * 类名称:OrderHeaderInfoService.java
 * 包名称:com.platform.modules.order.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:30        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.utils.RestResponse;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.register.entity.RegisterUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单头表 Service接口
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:30
 */
public interface OrderHeaderInfoService extends IService<OrderHeaderInfoEntity> {

	/**
	 * 查询所有列表
	 *
	 * @param params 查询参数
	 * @return List
	 */
	List<OrderHeaderInfoEntity> queryAll(Map<String, Object> params);

	/**
	 * 分页查询订单头表
	 *
	 * @param params 查询参数
	 * @return Page
	 */
	Page queryPage(Map<String, Object> params);
	/**
	 * 分页查询订单头表，查询对应用户类型的订单列表
	 *
	 * @param params 查询参数
	 * @return Page
	 */
	Page queryPage(Map<String, Object> params, RegisterUserEntity user);

	/**
	 * 新增订单头表
	 *
	 * @param orderHeaderInfo 订单头表
	 * @return 新增结果
	 */
	boolean add(OrderHeaderInfoEntity orderHeaderInfo);

	/**
	 * 根据主键更新订单头表
	 *
	 * @param orderHeaderInfo 订单头表
	 * @return 更新结果
	 */
	boolean update(OrderHeaderInfoEntity orderHeaderInfo);

	/**
	 * 根据主键删除订单头表
	 *
	 * @param id id
	 * @return 删除结果
	 */
	boolean delete(String id);

	/**
	 * 根据主键批量删除
	 *
	 * @param ids ids
	 * @return 删除结果
	 */
	boolean deleteBatch(String[] ids);

	/**
	 * 根据订单号获取订单类型
	 *
	 * @param orderItem
	 * @return
	 */
	String getByOrderNo(String orderItem);

	/**
	 * 获取订单图表所需要的数量
	 *
	 * @param orderInfo   查询条件集合
	 * @param isYesterday
	 * @return number
	 */
	int findNumberForChart(OrderHeaderInfoEntity orderInfo, boolean isYesterday);

	/**
	 * 获取当前月份监控图表数据
	 *
	 * @param orderHeaderInfoEntity 查询条件集合
	 * @return object
	 */
	JSONObject findChartData(OrderHeaderInfoEntity orderHeaderInfoEntity);

	/**
	 * 月度订单环比总数量变化
	 *
	 * @param orderInfo 查询条件集合
	 * @return object
	 */
	JSONObject findChartDataHb(OrderHeaderInfoEntity orderInfo);

	/**
	 * 更改订单状态
	 *
	 * @param orderHeaderInfo
	 */
	void changeStatus(OrderHeaderInfoEntity orderHeaderInfo);

	/**
	 * 查询在途的订单数量
	 *
	 * @param orderInfo   查询条件
	 * @param isYesterday 是否查询昨天的数据
	 * @return number 数量
	 */
	int findNumberOnWay(OrderHeaderInfoEntity orderInfo, boolean isYesterday);

	/**
	 * 新增订单（订单头和商品信息）
	 *
	 * @param orderHeaderInfo 订单信息
	 * @return orderItem 工作单号
	 */
	String addCompleteOrder(OrderHeaderInfoEntity orderHeaderInfo);

	/**
	 * 生成订单号
	 *
	 * @param orderType 订单类型
	 * @return orderItem 工作单号
	 */
	String getOrderItem(String orderType);

	/**
	 * 根据内部订单编号获取订单信息
	 *
	 * @param orderItem 内部订单编号
	 * @return order
	 */
	OrderHeaderInfoEntity getByOrderItem(String orderItem);

	/**
	 * 通过工作单号查询订单中运输信息
	 *
	 * @param orderItem
	 * @return
	 */
	Map<String, Object> queryTransportOrder(String orderItem);

	/**
	 * 通过工作单号查询收货方信息
	 *
	 * @param orderItem
	 * @return
	 */
	Map<String, Object> queryTransportReceive(String orderItem);

	/**
	 * 通过工作单号查询配货方信息
	 *
	 * @param orderItem
	 * @return
	 */
	Map<String, Object> queryTransportDelivery(String orderItem);

	/**
	 * 通过工作单号查询承运商信息
	 *
	 * @param orderItem
	 * @return
	 */
	Map<String, Object> queryTransportCarrier(String orderItem);

	/**
	 * 通过工作单号查询运输信息
	 *
	 * @param orderItem
	 * @return
	 */
	Map<String, Object> queryTransport(String orderItem);

	/**
	 * 判断订单号是否存在
	 *
	 * @param orderNo
	 * @return
	 */
	boolean isOrderNoExist(String orderNo);

	/**
	 * 根据内部订单编号获取订单类型
	 *
	 * @param orderItem
	 * @return
	 */
	Map<String, String> queryOrder(String orderItem);

	/**
	 * 根据内部订单编号获取入库信息
	 *
	 * @param orderItem
	 * @return
	 */
	List<Map<String, Object>> queryScanInAll(String orderItem, String operateType);

	/**
	 * 根据内部订单编号获取出库信息
	 *
	 * @param orderItem   工作单号
	 * @param operateType 操作类型
	 * @return
	 */
	List<Map<String, Object>> queryScanOutAll(String orderItem, String operateType);

	/**
	 * 查询用于工作单号
	 *
	 * @return
	 */
	List<String> queryOrderItem();

	/**
	 * 确认订单
	 *
	 * @param orderItem 工作单号
	 * @param orderType 订单类型，出库、入库
	 * @param userId    当前用户ID
	 * @return Object
	 */
	RestResponse confirmOrder(String orderItem, String orderType, String userId);

	/**
	 * 查找指定买方的订单信息
	 *
	 * @param ownerCode 买方编码
	 * @return list
	 */
	List<OrderHeaderInfoEntity> findByOwnerCode(String ownerCode);

	/**
	 * 根据订单号查询入库
	 */
	Map<String, Object> OutInfo(String orderItem);


	/**
	 * 根据订单号查询出库
	 */
	Map<String, Object> InInfo(String orderItem);

	/**
	 * 查询所有订单订单状态为“订单创建”且DYNMIC不为空的订单
	 *
	 * @return list
	 */
	List<OrderHeaderInfoEntity> findNewOrderList();

	/**
	 * 修改订单状态
	 *
	 * @param orderHeaderInfo
	 * @return
	 */
	boolean updateStatusByOrderNo(OrderHeaderInfoEntity orderHeaderInfo);

	/**
	 * 统计本月各仓库的订单数
	 *
	 * @return
	 */
	List<Map<String, Object>> countStoreOrderEveryMonth();

	/**
	 * 查询每天订单数量
	 * @param orderType 订单类型（出库/入库）
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @return
	 */
	List<Map<String, Object>> getByOrderCount(String orderType, String startTime, String endTime);

	/**
	 * 获取订单数量
	 * @param time 时间
	 * @param pattern 格式
	 * @param orderType 订单类型
	 * @return 数量
	 */
	Integer getNum(String time, String pattern, String orderType);


	List<String> getOrderItemList(String registerUserId, String storageSupplyId);

	/**
	 * 获取账单头部信息
	 * @param orderItem
	 * @return
	 */
	Map<String, Object> findByOrderItem(String orderItem);

	/**
	 * 根据订单类型获取当前登录用户近12个月的订单数量
	 * @param type
	 * @param params
	 * @return
	 */
    List<Map<String, Object>> getOrderNumByType(String type, Map<String,Object> params);
}
