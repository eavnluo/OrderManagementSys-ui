/*
 * 项目名称:platform-plus
 * 类名称:InStorageFlowDao.java
 * 包名称:com.platform.modules.in.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 14:28:34        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.wms.entity.InStorageFlowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 入库订单数据 Dao
 *
 * @author jk
 * @date 2019-09-04 14:28:34
 */
@Mapper
public interface InStorageFlowDao extends BaseMapper<InStorageFlowEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<InStorageFlowEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<InStorageFlowEntity> selectInStorageFlowPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     *
     * @param customerOrderNo
     * @param statusCode
     * @param statusName
     */
    void updatByOrderNo(@Param("customerOrderNo") String customerOrderNo,@Param("data")String data,@Param("statusCode") String statusCode,@Param("statusName") String statusName);
}
