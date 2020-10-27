/*
 * 项目名称:platform-plus
 * 类名称:OutPlanLogisticService.java
 * 包名称:com.platform.modules.out.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 09:31:42        stg     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.wms.entity.OutPlanLogisticEntity;

import java.util.List;
import java.util.Map;

/**
 * 出库物流信息表 Service接口
 *
 * @author stg
 * @date 2019-09-05 09:31:42
 */
public interface OutPlanLogisticService extends IService<OutPlanLogisticEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OutPlanLogisticEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询出库物流信息表 
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增出库物流信息表 
     *
     * @param outPlanLogistic 出库物流信息表 
     * @return 新增结果
     */
    boolean add(OutPlanLogisticEntity outPlanLogistic);

    /**
     * 根据主键更新出库物流信息表 
     *
     * @param outPlanLogistic 出库物流信息表 
     * @return 更新结果
     */
    boolean update(OutPlanLogisticEntity outPlanLogistic);

    /**
     * 根据主键删除出库物流信息表 
     *
     * @param carrierCompany carrierCompany
     * @return 删除结果
     */
    boolean delete(String carrierCompany);

    /**
     * 根据主键批量删除
     *
     * @param carrierCompanys carrierCompanys
     * @return 删除结果
     */
    boolean deleteBatch(String[] carrierCompanys);

    /***
     * 出库的物流单信息
     * @param params
     * @param mobile 手机号码
     * @return
     */
    Page queryPageByTelePhone(Map<String, String> params, String mobile);

    /**
     * 根据运单编号查询运单信息
     * @param wayBillNo 运单编号
     * @return list
     */
    List<OutPlanLogisticEntity> findByWayBillNo(String wayBillNo);

    /**
     * 根据条件物流信息
     * @param orderItem
     * @return
     */
    List<OutPlanLogisticEntity> queryLogisticsInfo(String orderItem);

    /**
     * 下发成功
     *
     * @param orderItem
     */
    Integer setPublish(String orderItem,String userId);

    OutPlanLogisticEntity findByOrderItem(String orderItem);
}
