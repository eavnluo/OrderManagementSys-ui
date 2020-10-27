/*
 * 项目名称:platform-plus
 * 类名称:OrderDetailGoodInfoServiceImpl.java
 * 包名称:com.platform.modules.order.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:30        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.OrderStatus;
import com.platform.common.OrderType;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.order.dao.OrderDetailGoodsInfoDao;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.entity.OrderStatusFlowEntity;
import com.platform.modules.order.service.OrderDetailGoodsInfoService;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.order.service.OrderStatusFlowService;
import com.platform.modules.wms.entity.StoreInventoryGoodsEntity;
import com.platform.modules.wms.service.StoreInventoryGoodsService;
import com.platform.modules.wms.service.StoreInventoryInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 订单详情表 Service实现类
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:30
 */
@Service("orderDetailGoodsInfoService")
public class OrderDetailGoodsInfoServiceImpl extends ServiceImpl<OrderDetailGoodsInfoDao, OrderDetailGoodsInfoEntity>
		implements OrderDetailGoodsInfoService {

	@Autowired
	private OrderDetailGoodsInfoDao orderDetailGoodInfoDao;
	@Autowired
	private OrderHeaderInfoService orderHeaderInfoService;
	@Autowired
	private OrderStatusFlowService orderStatusFlowService;
	@Autowired
	private StoreInventoryGoodsService storeInventoryGoodsService;
	@Autowired
	private StoreInventoryInfoService storeInventoryInfoService;
	@Autowired
	private BaseCustomerInfoService baseCustomerInfoService;

	@Override
	public List<OrderDetailGoodsInfoEntity> queryAll(Map<String, Object> params) {
		return baseMapper.queryAll(params);
	}

	@Override
	public Page queryPage(Map<String, Object> params) {
		//排序
		params.put("sidx", "T.delFlag");
		params.put("asc", false);
		Page<OrderDetailGoodsInfoEntity> page = new Query<OrderDetailGoodsInfoEntity>(params).getPage();
		return page.setRecords(baseMapper.selectOrderDetailGoodInfoPage(page, params));
	}

	@Override
	public boolean add(OrderDetailGoodsInfoEntity orderDetailGoodInfo) {
		return this.save(orderDetailGoodInfo);
	}

	@Override
	public boolean update(OrderDetailGoodsInfoEntity orderDetailGoodInfo) {
		return this.updateById(orderDetailGoodInfo);
	}

	@Override
	public String queryGoodCount(String orderItem) {
		return orderDetailGoodInfoDao.queryGoodCount(orderItem);
	}

	@Override
	public boolean delete(String delFlag) {
		return this.removeById(delFlag);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatch(String[] delFlags) {
		return this.removeByIds(Arrays.asList(delFlags));
	}

	/**
	 * 根据工作单号获取订单包含的商品列表
	 *
	 * @param orderItem 工作单号
	 * @return list
	 */
	@Override
	public List<OrderDetailGoodsInfoEntity> findByOrderItem(String orderItem) {
		return orderDetailGoodInfoDao.findByOrderItem(orderItem);
	}

	@Override
	public List<OrderDetailGoodsInfoEntity> listByOrderItem(String orderItem) {
		return baseMapper.listByOrderItem(orderItem);
	}

	/**
	 * 完善订单仓储信息
	 *
	 * @param params
	 * @param orderInfo
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean perfectOrderGoodsInfo(JSONObject params, OrderHeaderInfoEntity orderInfo, BaseSupplyInfoEntity supplyInfo) {
		String orderType = orderInfo.getOrderType();
		String orderItem = params.getString("orderItem");
		String outInOddNum = params.getString("outInOddNum");
		List<OrderDetailGoodsInfoEntity> orderGoodsList = JSONArray.parseArray(params.getJSONArray("orderGoodsList").toJSONString(), OrderDetailGoodsInfoEntity.class);
		BaseCustomerInfoEntity customerInfo = baseCustomerInfoService.findByRegistryUserId(orderInfo.getCustomerId());
		if (orderGoodsList.size() == 0) {
			return true;
		}
		// 保存仓储信息
		saveInventoryGoods(orderGoodsList, customerInfo.getCustomerCode(), supplyInfo.getSupplyCode(), outInOddNum, orderItem, orderType);

		String status;
		String statusName;
		// 判断订单类型，更改订单状态
		if (orderType.equals(OrderType.R_)) {
			// 采购订单,对应到达，签收，完成状态
			List<OrderStatusFlowEntity> list = new ArrayList<>();
			list.add(new OrderStatusFlowEntity(orderItem, OrderStatus.Arrive));
			list.add(new OrderStatusFlowEntity(orderItem, OrderStatus.Sign));
			list.add(new OrderStatusFlowEntity(orderItem, OrderStatus.Complete));
			// 保存订单状态
			orderStatusFlowService.saveBatch(list);

			status = OrderStatus.Complete.getCode();
			statusName = OrderStatus.Complete.getStatusName();
		} else {
			// 销售订单,对应提货
			OrderStatusFlowEntity entity = new OrderStatusFlowEntity(orderItem, OrderStatus.TiHuo);
			// 保存订单状态
			orderStatusFlowService.save(entity);

			// 判断当前订单是不是在途状态，如果是在途状态，则不修改该订单的状态了
			if(orderInfo.getStatus().equals(OrderStatus.OnWay.getCode())){
				return orderHeaderInfoService.update(new UpdateWrapper<OrderHeaderInfoEntity>()
						.set("STORAGE_SUPPLY_STATUS", Constant.STR_ONE)
						.eq("ORDER_ITEM", orderItem));
			}

			status = OrderStatus.TiHuo.getCode();
			statusName = OrderStatus.TiHuo.getStatusName();
		}
		// 更新库存
		RestResponse freshResult = storeInventoryInfoService.fresh(orderInfo, supplyInfo, customerInfo);
		if (!freshResult.get("code").toString().equals(Constant.STR_ZERO)) {
			throw new BusinessException(freshResult.get("msg").toString());
		}
		//修改订单仓储完善信息状态和订单状态
		return orderHeaderInfoService.update(new UpdateWrapper<OrderHeaderInfoEntity>()
				.set("STORAGE_SUPPLY_STATUS", Constant.STR_ONE)
				.set("STATUS", status)
				.set("STATUS_NAME", statusName)
				.eq("ORDER_ITEM", orderItem));
	}

	/**
	 * 保存仓储信息
	 *
	 * @param orderGoodsList
	 * @param customerCode
	 * @param storeCode
	 * @param outInOddNum
	 * @param orderItem
	 * @param orderType
	 * @return
	 */
	private boolean saveInventoryGoods(List<OrderDetailGoodsInfoEntity> orderGoodsList, String customerCode, String storeCode, String outInOddNum, String orderItem, String orderType) {
		List<StoreInventoryGoodsEntity> inventoryGoodsEntityList = new ArrayList<>(orderGoodsList.size());
		for (OrderDetailGoodsInfoEntity entity : orderGoodsList) {
			StoreInventoryGoodsEntity e = new StoreInventoryGoodsEntity();
			BeanUtils.copyProperties(entity, e, "createdBy", "createdTime", "updatedBy", "updatedTime");
			e.setOrderGoodsId(entity.getRowId());
			e.setGoodsId(e.getGoodsId());
			e.setOrderItem(orderItem);
			e.setOrderType(orderType);
			e.setOutInOddNum(outInOddNum);
			e.setStoreCode(storeCode);
			e.setCustomerCode(customerCode);
			e.setCreatedTime(new Date());
			e.setUpdatedTime(new Date());
			inventoryGoodsEntityList.add(e);
		}
		return storeInventoryGoodsService.saveBatch(inventoryGoodsEntityList);
	}

}
