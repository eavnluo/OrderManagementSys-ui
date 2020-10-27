/*
 * 项目名称:platform-plus
 * 类名称:OrderDetailGoodInfoService.java
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
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单详情表 Service接口
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:30
 */
public interface OrderDetailGoodsInfoService extends IService<OrderDetailGoodsInfoEntity> {

	/**
	 * 查询所有列表
	 *
	 * @param params 查询参数
	 * @return List
	 */
	List<OrderDetailGoodsInfoEntity> queryAll(Map<String, Object> params);

	/**
	 * 分页查询订单详情表
	 *
	 * @param params 查询参数
	 * @return Page
	 */
	Page queryPage(Map<String, Object> params);

	/**
	 * 新增订单详情表
	 *
	 * @param orderDetailGoodInfo 订单详情表
	 * @return 新增结果
	 */
	boolean add(OrderDetailGoodsInfoEntity orderDetailGoodInfo);

	/**
	 * 根据主键更新订单详情表
	 *
	 * @param orderDetailGoodInfo 订单详情表
	 * @return 更新结果
	 */
	boolean update(OrderDetailGoodsInfoEntity orderDetailGoodInfo);

	/**
	 * 根据主键删除订单详情表
	 *
	 * @param delFlag delFlag
	 * @return 删除结果
	 */
	boolean delete(String delFlag);

	/**
	 * 根据主键批量删除
	 *
	 * @param delFlags delFlags
	 * @return 删除结果
	 */
	boolean deleteBatch(String[] delFlags);

	/***
	 * 通过订单号查询对应的商品信息
	 * @param orderItem
	 * @return
	 */
	List<OrderDetailGoodsInfoEntity> listByOrderItem(String orderItem);

	/**
	 * 根据工作单号获取订单包含的商品列表
	 *
	 * @param orderItem 工作单号
	 * @return list
	 */
	List<OrderDetailGoodsInfoEntity> findByOrderItem(String orderItem);

	/**
	 * 根据内部订单编号获取订单类型
	 *
	 * @param orderItem
	 * @return
	 */
	String queryGoodCount(String orderItem);

	/**
	 * 完善订单仓储信息
	 *
	 * @param params
	 * @param orderInfo
	 * @return
	 */
	boolean perfectOrderGoodsInfo(JSONObject params, OrderHeaderInfoEntity orderInfo, BaseSupplyInfoEntity supplyInfo);
}
