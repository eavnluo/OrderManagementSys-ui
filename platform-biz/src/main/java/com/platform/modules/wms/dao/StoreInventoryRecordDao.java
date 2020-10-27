/*
 * 项目名称:platform-plus
 * 类名称:StoreInventoryRecordDao.java
 * 包名称:com.platform.modules.store.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-23 11:52:28        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.wms.entity.StoreInventoryRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author Mr.panbb
 * @date 2019-09-23 11:52:28
 */
@Mapper
public interface StoreInventoryRecordDao extends BaseMapper<StoreInventoryRecordEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<StoreInventoryRecordEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<StoreInventoryRecordEntity> selectStoreInventoryRecordPage(IPage page, @Param("params") Map<String, Object> params);
}
