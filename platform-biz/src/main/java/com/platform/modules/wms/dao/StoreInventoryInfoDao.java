/*
 * 项目名称:platform-plus
 * 类名称:StoreInventoryInfoDao.java
 * 包名称:com.platform.modules.store.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 14:14:39        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.modules.wms.entity.StoreInventoryInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 库存管理 Dao
 *
 * @author jk
 * @date 2019-09-05 14:14:39
 */
@Mapper
public interface StoreInventoryInfoDao extends BaseMapper<StoreInventoryInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<StoreInventoryInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<StoreInventoryInfoEntity> selectStoreInventoryInfoPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 获取月度库存变化
     *
     * @param stock 查询条件
     * @return Object
     */
    List<Map<String, Object>> findMonthStock(@Param("stock") StoreInventoryInfoEntity stock);

    /**
     * 根据仓库编号、商品编号获取库存信息
     *
     * @param storeCode 仓库编号
     * @param itemCode  商品编号
     * @param ownerCode 货主编号
     * @return
     */
    List<StoreInventoryInfoEntity> getInfo(@Param("storeCode") String storeCode, @Param("itemCode") String itemCode,
                                           @Param("ownerCode") String ownerCode);

    /**
     * 根据仓储供应商编号、商品ID、客户编号获取库存信息
     *
     * @param storeCode 仓库编号
     * @param itemId  商品编号
     * @param ownerCode 货主编号
     * @return
     */
    List<StoreInventoryInfoEntity> getGoodsInfo(@Param("storeCode") String storeCode, @Param("itemId") String itemId,
                                           @Param("ownerCode") String ownerCode);

    /**
     * 分页查询库存信息
     * @param page
     * @param params
     * @return
     */
    List<Map<String, Object>> findStoreList(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 查询入库信息
     * @param page
     * @param params
     * @return
     */
    List<Map<String, Object>> findInStoreList(Page<Map<String, Object>> page, @Param("params") Map<String, Object> params);

    /**
     * 查询出库信息
     * @param page
     * @param params
     * @return
     */
    List<Map<String, Object>> findOutStoreList(Page<Map<String, Object>> page, @Param("params") Map<String, Object> params);

    List<StoreInventoryInfoEntity> statisticsStore(Map<String, Object> params);
}
