/*
 * 项目名称:platform-plus
 * 类名称:InPlanLogisticService.java
 * 包名称:com.platform.modules.in.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 16:47:54        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.wms.entity.InPlanLogisticEntity;

import java.util.List;
import java.util.Map;

/**
 * 入库物流信息 Service接口
 *
 * @author jk
 * @date 2019-09-04 16:47:54
 */
public interface InPlanLogisticService extends IService<InPlanLogisticEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<InPlanLogisticEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询入库物流信息
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增入库物流信息
     *
     * @param inPlanLogistic 入库物流信息
     * @return 新增结果
     */
    boolean add(InPlanLogisticEntity inPlanLogistic);

    /**
     * 根据主键更新入库物流信息
     *
     * @param inPlanLogistic 入库物流信息
     * @return 更新结果
     */
    boolean update(InPlanLogisticEntity inPlanLogistic);

    /**
     * 根据主键删除入库物流信息
     *
     * @param logisticsInfoNo logisticsInfoNo
     * @return 删除结果
     */
    boolean delete(String logisticsInfoNo);

    /**
     * 根据主键批量删除
     *
     * @param logisticsInfoNos logisticsInfoNos
     * @return 删除结果
     */
    boolean deleteBatch(String[] logisticsInfoNos);

    /***
     * 根据用户获取对应的手持数据
     * @param params
     * @param mobile 手持用户的手机号码
     * @return
     */
    Page queryPageByTelePhone(Map<String, String> params, String mobile);

    /**
     * 根据运单编号查询运单信息
     * @param wayBillNo 运单编号
     * @return list
     */
    List<InPlanLogisticEntity> findByWayBillNo(String wayBillNo);

    /**
     * 根据条件物流信息
     * @param orderItem
     * @return
     */
    List<InPlanLogisticEntity> queryLogisticsInfo(String orderItem);

    /**
     * 下发成功
     *
     * @param orderItem
     */
    Integer setPublish(String orderItem,String userId);
}
