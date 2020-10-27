/*
 * 项目名称:platform-plus
 * 类名称:OutStorageFlowDao.java
 * 包名称:com.platform.modules.wms.out.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 16:19:19        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.wms.entity.OutStorageFlowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 出库流水计划信息 Dao
 *
 * @author stg
 * @date 2019-09-04 16:19:19
 */
@Mapper
public interface OutStorageFlowDao extends BaseMapper<OutStorageFlowEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OutStorageFlowEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<OutStorageFlowEntity> selectOutStorageFlowPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     *
     * @param customerOrderNo
     * @param data wms 订单号
     * @param statusCode
     * @param statusName
     */
    void updatByOrderNo(@Param("customerOrderNo") String customerOrderNo,@Param("data")String data,@Param("statusCode") String statusCode,@Param("statusName") String statusName);
}
