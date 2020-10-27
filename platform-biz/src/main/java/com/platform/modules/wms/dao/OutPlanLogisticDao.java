/*
 * 项目名称:platform-plus
 * 类名称:OutPlanLogisticDao.java
 * 包名称:com.platform.modules.out.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 09:31:42        stg     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.order.entity.OrderRelationshipEntity;
import com.platform.modules.wms.entity.OutPlanLogisticEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 出库物流信息表 Dao
 *
 * @author stg
 * @date 2019-09-05 09:31:42
 */
@Mapper
public interface OutPlanLogisticDao extends BaseMapper<OutPlanLogisticEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OutPlanLogisticEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<OutPlanLogisticEntity> selectOutPlanLogisticPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 根据运单编号查询运单信息
     *
     * @param wayBillNo 运单编号
     * @return list
     */
    List<OutPlanLogisticEntity> findByWayBillNo(@Param("wayBillNo") String wayBillNo);

    /**
     * 根据条件物流信息
     * @param orderItem
     * @return
     */
    List<OutPlanLogisticEntity> queryLogisticsInfo(@Param("orderItem") String orderItem, @Param("orderRelationshipList") List<OrderRelationshipEntity> orderRelationshipList);

    /**
     * 下发成功
     *
     * @param orderItem
     */
    Integer setPublish(String orderItem);
}

