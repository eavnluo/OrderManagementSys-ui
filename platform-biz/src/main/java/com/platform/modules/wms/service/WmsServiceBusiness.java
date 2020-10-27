package com.platform.modules.wms.service;

import com.google.common.collect.Lists;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.wms.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Created by Tp-wangwh on 2019-9-4.
 */
@Service
@Transactional(readOnly = true)
public class WmsServiceBusiness {


    @Autowired
    private InStorageFlowService inStorageFlowService;

    @Autowired
    private InPlanLogisticService inPlanLogisticService;

    @Autowired
    private InPlanGoodsService inPlanGoodsService;

    @Autowired
    private OutStorageFlowService outStorageFlowService;

    @Autowired
    private OutPlanLogisticService outPlanLogisticService;

    @Autowired
    private OutGoodsService outGoodsService;

    /***
     * 入库操作
     * @param orderInfo
     * @param goodLists
     */
    @Transactional(readOnly = false)
    public void  inStoragePlan(OrderHeaderInfoEntity orderInfo, List<OrderDetailGoodsInfoEntity> goodLists){
        //TODO 1.入库操作 插入本地数据接口
        List<InPlanGoodsEntity> inPlanGoodsEntities= Lists.newArrayList();
        goodLists.forEach(orderDetailGoodInfoEntity->{
            InPlanGoodsEntity planGoodsEntity = orderDetailGoodInfoEntity.toInPlanGoods();
            planGoodsEntity.setOrderItem(orderInfo.getOrderItem());
            inPlanGoodsEntities.add(planGoodsEntity);
        });
        inPlanGoodsService.saveBatch(inPlanGoodsEntities);
    }

    @Transactional(readOnly = false)
    public void  outStoragePlan(OrderHeaderInfoEntity orderInfo, List<OrderDetailGoodsInfoEntity> goodLists){
        //TODO 1.出库操作 插入本地数据接口
        //将odInfoEntity goodLists中的字段转为InStorageFlowEntity ,
        List<OutGoodsEntity> outPlanGoodsEntities= Lists.newArrayList();
        goodLists.forEach(orderDetailGoodInfoEntity->{
            OutGoodsEntity outGoodsEntity=orderDetailGoodInfoEntity.toOutGoods();
            outGoodsEntity.setOrderItem(orderInfo.getOrderItem());
            outPlanGoodsEntities.add(outGoodsEntity);
        });
        outGoodsService.saveBatch(outPlanGoodsEntities);
    }

}
