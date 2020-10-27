/*
 * 项目名称:platform-plus
 * 类名称:StoreInventoryGoodsService.java
 * 包名称:com.platform.modules.store.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-31 10:34:30        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.wms.entity.StoreInventoryGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zlm
 * @date 2020-03-31 10:34:30
 */
public interface StoreInventoryGoodsService extends IService<StoreInventoryGoodsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<StoreInventoryGoodsEntity> queryAll(Map<String, Object> params);

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
     * @param storeInventoryGoods 
     * @return 新增结果
     */
    boolean add(StoreInventoryGoodsEntity storeInventoryGoods);

    /**
     * 根据主键更新
     *
     * @param storeInventoryGoods 
     * @return 更新结果
     */
    boolean update(StoreInventoryGoodsEntity storeInventoryGoods);

    /**
     * 根据主键删除
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

    /**
     * 根据工作单号获取列表
     * @param orderItem
     */
    List<StoreInventoryGoodsEntity> listByOrderItem(String orderItem);

	Page<Map<String, Object>> getOutInRecords(Map<String,Object> params, String goodsId, List<String> orderItems);
}
