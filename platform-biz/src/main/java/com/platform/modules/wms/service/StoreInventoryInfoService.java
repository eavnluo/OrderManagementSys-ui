/*
 * 项目名称:platform-plus
 * 类名称:StoreInventoryInfoService.java
 * 包名称:com.platform.modules.store.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 14:14:39        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.order.entity.OrderGoodUniqueCodeScanEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.wms.entity.StoreInventoryInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 库存管理 Service接口
 *
 * @author jk
 * @date 2019-09-05 14:14:39
 */
public interface StoreInventoryInfoService extends IService<StoreInventoryInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<StoreInventoryInfoEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询库存管理
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增库存管理
     *
     * @param storeInventoryInfo 库存管理
     * @return 新增结果
     */
    boolean add(StoreInventoryInfoEntity storeInventoryInfo);

    /**
     * 根据主键更新库存管理
     *
     * @param storeInventoryInfo 库存管理
     * @return 更新结果
     */
    boolean update(StoreInventoryInfoEntity storeInventoryInfo);

    /**
     * 根据主键删除库存管理
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
     * 获取月度库存变化
     *
     * @param stock
     * @return Object
     */
    JSONObject findMonthStock(StoreInventoryInfoEntity stock);

    /**
     * 刷新库存
     *
     * @param orderItem 工作单号
     * @return
     */
    RestResponse fresh(String orderItem);

    /**
     * 刷新库存
     *
     * @param orderInfo    订单信息
     * @param supplyinfo   仓储供应商信息
     * @param customerInfo 客户信息
     * @return
     */
    RestResponse fresh(OrderHeaderInfoEntity orderInfo, BaseSupplyInfoEntity supplyinfo, BaseCustomerInfoEntity customerInfo);

    /**
     * 确认库存量是否满足出库的要去
     *
     * @param orderItem 工作单号
     * @return Object
     */
    boolean confirmNum(String orderItem);

    /**
     * 查找指定货主指定商品的信息
     *
     * @param params
     * @return
     */
    List<OrderGoodUniqueCodeScanEntity> findGoodUniqueList(Map<String, Object> params);

    /**
     * 分页查询库存
     *
     * @param params
     * @return
     */
    Page queryStorePage(Map<String, Object> params);

    /**
     * 查询库存数据
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> findStoreList(Map<String, Object> params);

    /**
     * 分页查询入库信息
     *
     * @param params
     * @return
     */
    Page queryInStorePage(Map<String, Object> params);

    /**
     * 查询入库信息
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> findInStoreList(Map<String, Object> params);

    /**
     * 分页查询出库信息
     *
     * @param params
     * @return
     */
    Page queryOutStorePage(Map<String, Object> params);

    /**
     * 查询出库信息
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> findOutStoreList(Map<String, Object> params);

    /**
     * 获取出入库记录
     *
     * @param params 请求参数
     * @return 出入库记录
     */
    Page<Map<String, Object>> getOutInRecords(Map<String, Object> params);

    /**
     * 根据仓储供应商编号、商品ID、客户编号获取库存信息
     *
     * @param storeCode 仓库编号
     * @param itemId    商品编号
     * @param ownerCode 货主编号
     * @return
     */
    List<StoreInventoryInfoEntity> getGoodsInfo(String storeCode, String itemId, String ownerCode);

    /**
     * 统计库存信息
     * @param params
     * @return
     */
    List<StoreInventoryInfoEntity> statisticsStore(Map<String, Object> params);
}
