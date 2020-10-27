/*
 * 项目名称:platform-plus
 * 类名称:BaseIndustryTypeService.java
 * 包名称:com.platform.modules.base.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 10:01:36        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.base.entity.BaseIndustryTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zlm
 * @date 2020-03-18 10:01:36
 */
public interface BaseIndustryTypeService extends IService<BaseIndustryTypeEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseIndustryTypeEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增
     *
     * @param baseIndustryType 
     * @return 新增结果
     */
    boolean add(BaseIndustryTypeEntity baseIndustryType);

    /**
     * 根据主键更新
     *
     * @param baseIndustryType 
     * @return 更新结果
     */
    boolean update(BaseIndustryTypeEntity baseIndustryType);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(Integer id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(Integer[] ids);
}
