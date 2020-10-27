/*
 * 项目名称:platform-plus
 * 类名称:CooperationGuideService.java
 * 包名称:com.platform.modules.cooperation.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-19 08:42:55        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.cooperation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.cooperation.entity.CooperationGuideEntity;

import java.util.List;
import java.util.Map;

/**
 * 合作指南Service接口
 *
 * @author Mr.panbb
 * @date 2020-03-19 08:42:55
 */
public interface CooperationGuideService extends IService<CooperationGuideEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<CooperationGuideEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询合作指南
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增合作指南
     *
     * @param cooperationGuide 合作指南
     * @return 新增结果
     */
    boolean add(CooperationGuideEntity cooperationGuide);

    /**
     * 根据主键更新合作指南
     *
     * @param cooperationGuide 合作指南
     * @return 更新结果
     */
    boolean update(CooperationGuideEntity cooperationGuide);

    /**
     * 根据主键删除合作指南
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
}
