/*
 * 项目名称:platform-plus
 * 类名称:StoreInventoryRecordService.java
 * 包名称:com.platform.modules.store.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-23 11:52:28        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.wms.entity.StoreInventoryRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author Mr.panbb
 * @date 2019-09-23 11:52:28
 */
public interface StoreInventoryRecordService extends IService<StoreInventoryRecordEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<StoreInventoryRecordEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增
     *
     * @param storeInventoryRecord 
     * @return 新增结果
     */
    boolean add(StoreInventoryRecordEntity storeInventoryRecord);

    /**
     * 根据主键更新
     *
     * @param storeInventoryRecord 
     * @return 更新结果
     */
    boolean update(StoreInventoryRecordEntity storeInventoryRecord);

    /**
     * 根据主键删除
     *
     * @param oldValue oldValue
     * @return 删除结果
     */
    boolean delete(Double oldValue);

    /**
     * 根据主键批量删除
     *
     * @param oldValues oldValues
     * @return 删除结果
     */
    boolean deleteBatch(Double[] oldValues);
}
