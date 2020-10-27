/*
 * 项目名称:platform-plus
 * 类名称:RequestingResearchService.java
 * 包名称:com.platform.modules.requesting.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 14:01:57        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.requesting.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.requesting.entity.RequestingResearchEntity;

import java.util.List;
import java.util.Map;

/**
 * 需求调研Service接口
 *
 * @author Mr.panbb
 * @date 2020-03-18 14:01:57
 */
public interface RequestingResearchService extends IService<RequestingResearchEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<RequestingResearchEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询需求调研
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增需求调研
     *
     * @param requestingResearch 需求调研
     * @return 新增结果
     */
    boolean add(RequestingResearchEntity requestingResearch);

    /**
     * 根据主键更新需求调研
     *
     * @param requestingResearch 需求调研
     * @return 更新结果
     */
    boolean update(RequestingResearchEntity requestingResearch);

    /**
     * 根据主键删除需求调研
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
