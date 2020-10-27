/*
 * 项目名称:platform-plus
 * 类名称:StoreOutFlowStatusService.java
 * 包名称:com.platform.modules.store.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-07 16:36:16        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.wms.entity.StoreOutFlowStatusEntity;

import java.util.List;
import java.util.Map;

/**
 *  出库订单状态流水表Service接口
 *
 * @author 凯晟admin
 * @date 2019-09-07 16:36:16
 */
public interface StoreOutFlowStatusService extends IService<StoreOutFlowStatusEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<StoreOutFlowStatusEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询 出库订单状态流水表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增 出库订单状态流水表
     *
     * @param storeOutFlowStatus  出库订单状态流水表
     * @return 新增结果
     */
    boolean add(StoreOutFlowStatusEntity storeOutFlowStatus);

    /**
     * 根据主键更新 出库订单状态流水表
     *
     * @param storeOutFlowStatus  出库订单状态流水表
     * @return 更新结果
     */
    boolean update(StoreOutFlowStatusEntity storeOutFlowStatus);

    /**
     * 根据主键删除 出库订单状态流水表
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
}
