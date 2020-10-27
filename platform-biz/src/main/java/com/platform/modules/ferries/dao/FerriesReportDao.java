/*
 * 项目名称:platform-plus
 * 类名称:FerriesReportDao.java
 * 包名称:com.platform.modules.ferries.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-06 11:15:10        JK     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.ferries.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.ferries.entity.FerriesReportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 船运报表Dao
 *
 * @author JK
 * @date 2019-09-06 11:15:10
 */
@Mapper
public interface FerriesReportDao extends BaseMapper<FerriesReportEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<FerriesReportEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<FerriesReportEntity> selectFerriesReportPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 查询图表展示数据列表
     * @return list
     */
    List<FerriesReportEntity> findListForChart();

    /**
     * 获取最新的国际运输信息
     * @return
     */
    FerriesReportEntity getNewInfo();
}
