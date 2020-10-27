/*
 * 项目名称:platform-plus
 * 类名称:OrderCarRelationServiceImpl.java
 * 包名称:com.platform.modules.order.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 13:04:44        zlm     初版做成
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
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.modules.order.dao.OrderCarRelationDao;
import com.platform.modules.order.entity.OrderCarRelationEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.entity.OrderStatusFlowEntity;
import com.platform.modules.order.service.OrderCarRelationService;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.order.service.OrderStatusFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author zlm
 * @date 2020-03-24 13:04:44
 */
@Service("orderCarRelationService")
public class OrderCarRelationServiceImpl extends ServiceImpl<OrderCarRelationDao, OrderCarRelationEntity> implements OrderCarRelationService {
    @Autowired
    private OrderHeaderInfoService orderHeaderInfoService;
    @Autowired
    private OrderStatusFlowService orderStatusFlowService;

    @Override
    public List<OrderCarRelationEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<OrderCarRelationEntity> page = new Query<OrderCarRelationEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOrderCarRelationPage(page, params));
    }

    @Override
    public boolean add(OrderCarRelationEntity orderCarRelation) {
        return this.save(orderCarRelation);
    }

    @Override
    public boolean update(OrderCarRelationEntity orderCarRelation) {
        return this.updateById(orderCarRelation);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    /**
     * 根据工作单号获取订单车辆信息
     *
     * @param orderItem 工作单号
     * @return
     */
    @Override
    public List<OrderCarRelationEntity> listByOrderItem(String orderItem) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderItem", orderItem);
        return queryAll(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean perfectOrderCarInfo(JSONObject params, OrderHeaderInfoEntity orderInfo) {
        // 获取参数
        JSONArray orderCarArray = params.getJSONArray("orderCarList");
        List<OrderCarRelationEntity> orderCarList = JSONArray.parseArray(orderCarArray.toJSONString(), OrderCarRelationEntity.class);
        if (orderCarList.size() == 0) {
            return true;
        }
        String wayBillNo = params.getString("wayBillNo");
        String signNumber = params.getString("signNumber");
        String orderItem = params.getString("orderItem");
        for (OrderCarRelationEntity entity : orderCarList) {
            entity.setWayBillNo(wayBillNo);
            entity.setSignNumber(signNumber);
            entity.setOrderItem(orderItem);
        }
        // 保存订单车辆信息
        saveBatch(orderCarList);

        // 判断订单类型，更改订单状态
        if (orderInfo.getOrderType().equals(OrderType.R_)) {
            // 采购订单,对应提货在途
            List<OrderStatusFlowEntity> list = new ArrayList<>();
            list.add(new OrderStatusFlowEntity(orderItem, OrderStatus.TiHuo));
            list.add(new OrderStatusFlowEntity(orderItem, OrderStatus.OnWay));
            // 保存订单状态
            orderStatusFlowService.saveBatch(list);

            // 判断当前订单状态是不是完成状态，如果是完成状态，则不修改当前订单状态了
            if (orderInfo.getStatus().equals(OrderStatus.Complete.getCode())) {
                return orderHeaderInfoService.update(new UpdateWrapper<OrderHeaderInfoEntity>()
                        .set("CAR_SUPPLY_STATUS", Constant.STR_ONE)
                        .eq("ORDER_ITEM", orderItem));
            }
        } else {
            // 销售订单,对应在途
            OrderStatusFlowEntity entity = new OrderStatusFlowEntity(orderItem, OrderStatus.OnWay);
            // 保存订单状态
            orderStatusFlowService.save(entity);
        }
        // 修改订单车辆完善状态
        return orderHeaderInfoService.update(new UpdateWrapper<OrderHeaderInfoEntity>()
                .set("CAR_SUPPLY_STATUS", Constant.STR_ONE)
                .set("STATUS", OrderStatus.OnWay.getCode())
                .set("STATUS_NAME", OrderStatus.OnWay.getStatusName())
                .eq("ORDER_ITEM", orderItem));
    }
}
