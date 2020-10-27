/*
 * 项目名称:platform-plus
 * 类名称:StoreInFlowStatusDao.java
 * 包名称:com.platform.modules.store.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-07 16:36:16        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.wms.entity.StoreInFlowStatusEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 入库订单状态 Dao
 *
 * @author 凯晟admin
 * @date 2019-09-07 16:36:16
 */
@Mapper
public interface StoreInFlowStatusDao extends BaseMapper<StoreInFlowStatusEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<StoreInFlowStatusEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<StoreInFlowStatusEntity> selectStoreInFlowStatusPage(IPage page, @Param("params") Map<String, Object> params);
}
