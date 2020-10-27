/*
 * 项目名称:platform-plus
 * 类名称:FerriesReportService.java
 * 包名称:com.platform.modules.ferries.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-06 11:15:10        JK     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.ferries.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.ferries.entity.FerriesReportEntity;

import java.util.List;
import java.util.Map;

/**
 * 船运报表Service接口
 *
 * @author JK
 * @date 2019-09-06 11:15:10
 */
public interface FerriesReportService extends IService<FerriesReportEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<FerriesReportEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询船运报表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增船运报表
     *
     * @param ferriesReport 船运报表
     * @return 新增结果
     */
    boolean add(FerriesReportEntity ferriesReport);

    /**
     * 根据主键更新船运报表
     *
     * @param ferriesReport 船运报表
     * @return 更新结果
     */
    boolean update(FerriesReportEntity ferriesReport);

    /**
     * 根据主键删除船运报表
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
     * 获取本年图表监控数据
     * @return Object
     */
    JSONObject findChartData();

    /**
     * 查询最新的运输信息
     * @return Object
     */
    JSONObject getNewInfo();
}
