/*
 * 项目名称:platform-plus
 * 类名称:StoreInFlowStatusService.java
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
import com.platform.modules.wms.entity.StoreInFlowStatusEntity;

import java.util.List;
import java.util.Map;

/**
 * 入库订单状态 Service接口
 *
 * @author 凯晟admin
 * @date 2019-09-07 16:36:16
 */
public interface StoreInFlowStatusService extends IService<StoreInFlowStatusEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<StoreInFlowStatusEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询入库订单状态 
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增入库订单状态 
     *
     * @param storeInFlowStatus 入库订单状态 
     * @return 新增结果
     */
    boolean add(StoreInFlowStatusEntity storeInFlowStatus);

    /**
     * 根据主键更新入库订单状态 
     *
     * @param storeInFlowStatus 入库订单状态 
     * @return 更新结果
     */
    boolean update(StoreInFlowStatusEntity storeInFlowStatus);

    /**
     * 根据主键删除入库订单状态 
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
