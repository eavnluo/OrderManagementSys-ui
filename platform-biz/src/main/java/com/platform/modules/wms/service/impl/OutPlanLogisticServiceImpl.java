/*
 * 项目名称:platform-plus
 * 类名称:OutPlanLogisticServiceImpl.java
 * 包名称:com.platform.modules.out.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 09:31:42        stg     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.platform.modules.wms.dao.OutPlanLogisticDao;
import com.platform.modules.wms.entity.OutPlanLogisticEntity;
import com.platform.modules.wms.service.OutPlanLogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 出库物流信息表 Service实现类
 *
 * @author stg
 * @date 2019-09-05 09:31:42
 */
@Service("outPlanLogisticService")
public class OutPlanLogisticServiceImpl extends ServiceImpl<OutPlanLogisticDao, OutPlanLogisticEntity>
        implements OutPlanLogisticService {

    @Autowired
    private OutPlanLogisticDao outPlanLogisticDao;
    @Autowired
    private OrderHeaderInfoService orderHeaderInfoService;
    @Autowired
    private OrderStatusFlowService orderStatusFlowService;
    @Autowired
    private OrderRelationshipService orderRelationshipService;


    @Override
    public List<OutPlanLogisticEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.UPDATED_TIME");
        params.put("asc", false);
        Page<OutPlanLogisticEntity> page = new Query<OutPlanLogisticEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOutPlanLogisticPage(page, params));
    }

    @Override
    public boolean add(OutPlanLogisticEntity outPlanLogistic) {
        return this.save(outPlanLogistic);
    }

    @Override
    public boolean update(OutPlanLogisticEntity outPlanLogistic) {
        return this.updateById(outPlanLogistic);
    }

    @Override
    public boolean delete(String carrierCompany) {
        return this.removeById(carrierCompany);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] carrierCompanys) {
        return this.removeByIds(Arrays.asList(carrierCompanys));
    }

    /***
     *
     * @param params
     * @param mobile 手机号码
     * @return
     */
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
    public List<OutPlanLogisticEntity> findByWayBillNo(String wayBillNo) {
        return outPlanLogisticDao.findByWayBillNo(wayBillNo);
    }

    @Override
    public List<OutPlanLogisticEntity> queryLogisticsInfo(String orderItem) {
        List<OrderRelationshipEntity> orderRelationshipList = orderRelationshipService.findByOrderItem(orderItem);
        if(orderRelationshipList.size() == 0){
            return null;
        }
        return outPlanLogisticDao.queryLogisticsInfo(orderItem,orderRelationshipList);
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
        status.setStatusCode(OrderStatus.OutChuStorage.getCode());
        status.setOrderStatus(OrderStatus.OutChuStorage.getStatusName());
        //订单状态
        order.setStatus(OrderStatus.OutChuStorage.getCode());
        order.setStatusName(OrderStatus.OutChuStorage.getStatusName());
        // 修改订单表状态信息
        orderHeaderInfoService.updateById(order);
        // 添加状态信息记录
        orderStatusFlowService.add(status);
        return outPlanLogisticDao.setPublish(orderItem);
    }

    @Override
    public OutPlanLogisticEntity findByOrderItem(String orderItem) {
        return outPlanLogisticDao.selectOne(new QueryWrapper<OutPlanLogisticEntity>().eq("ORDER_ITEM",orderItem));
    }
}
