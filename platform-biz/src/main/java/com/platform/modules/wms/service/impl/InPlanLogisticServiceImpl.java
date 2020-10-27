/*
 * 项目名称:platform-plus
 * 类名称:InPlanLogisticServiceImpl.java
 * 包名称:com.platform.modules.in.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 16:47:54        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.OrderStatus;
import com.platform.common.utils.Query;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.entity.OrderRelationshipEntity;
import com.platform.modules.order.entity.OrderStatusFlowEntity;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.order.service.OrderRelationshipService;
import com.platform.modules.order.service.OrderStatusFlowService;
import com.platform.modules.wms.dao.InPlanLogisticDao;
import com.platform.modules.wms.entity.InPlanLogisticEntity;
import com.platform.modules.wms.service.InPlanLogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 入库物流信息 Service实现类
 *
 * @author jk
 * @date 2019-09-04 16:47:54
 */
@Service("inPlanLogisticService")
public class InPlanLogisticServiceImpl extends ServiceImpl<InPlanLogisticDao, InPlanLogisticEntity>
        implements InPlanLogisticService {

    @Autowired
    private InPlanLogisticDao inPlanLogisticDao;
    @Autowired
    private OrderHeaderInfoService orderHeaderInfoService;
    @Autowired
    private OrderStatusFlowService orderStatusFlowService;
    @Autowired
    private OrderRelationshipService orderRelationshipService;

    @Override
    public List<InPlanLogisticEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.UPDATED_TIME");
        params.put("asc", false);
        Page<InPlanLogisticEntity> page = new Query<InPlanLogisticEntity>(params).getPage();
        return page.setRecords(baseMapper.selectInPlanLogisticPage(page, params));
    }

    @Override
    public boolean add(InPlanLogisticEntity inPlanLogistic) {
        Date now = new Date();
        inPlanLogistic.setCreatedTime(now);
        inPlanLogistic.setUpdatedTime(now);
        return this.save(inPlanLogistic);
    }

    @Override
    public boolean update(InPlanLogisticEntity inPlanLogistic) {
        inPlanLogistic.setUpdatedTime(new Date());
        return this.updateById(inPlanLogistic);
    }

    @Override
    public boolean delete(String logisticsInfoNo) {
        return this.removeById(logisticsInfoNo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] logisticsInfoNos) {
        return this.removeByIds(Arrays.asList(logisticsInfoNos));
    }

    @Override
    public Page queryPageByTelePhone(Map<String, String> params, String mobile) {
        return null;
    }

    /**
     * 根据运单编号查询运单信息
     *
     * @param wayBillNo 运单编号
     * @return list
     */
    @Override
    public List<InPlanLogisticEntity> findByWayBillNo(String wayBillNo) {
        return inPlanLogisticDao.findByWayBillNo(wayBillNo);
    }

    @Override
    public List<InPlanLogisticEntity> queryLogisticsInfo(String orderItem) {
        List<OrderRelationshipEntity> orderRelationshipList = orderRelationshipService.findByOrderItem(orderItem);
        if(orderRelationshipList.size() == 0){
            return null;
        }
        return inPlanLogisticDao.queryLogisticsInfo(orderItem,orderRelationshipList);
    }

    @Override
    @Transactional
    public Integer setPublish(String orderItem,String userId) {
        //改变入库订单状态和增加状态记录
        OrderHeaderInfoEntity order = orderHeaderInfoService.getByOrderItem(orderItem);
        Date nowDate = new Date();
        //状态记录
        OrderStatusFlowEntity status = new OrderStatusFlowEntity();
        status.setCreatedBy(userId);
        status.setCreatedTime(nowDate);
        status.setOrderItem(orderItem);
        status.setStatusDate(nowDate);
        status.setStatusCode(OrderStatus.InTiHuo.getCode());
        status.setOrderStatus(OrderStatus.InTiHuo.getStatusName());
        //订单状态
        order.setStatus(OrderStatus.InTiHuo.getCode());
        order.setStatusName(OrderStatus.InTiHuo.getStatusName());
        // 修改订单表状态信息
        orderHeaderInfoService.updateById(order);
        // 添加状态信息记录
        orderStatusFlowService.add(status);
        return inPlanLogisticDao.setPublish(orderItem);
    }
}
