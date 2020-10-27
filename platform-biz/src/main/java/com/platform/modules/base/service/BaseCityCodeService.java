/*
 * 项目名称:platform-plus
 * 类名称:BaseCityCodeService.java
 * 包名称:com.platform.modules.base.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 11:04:39        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.base.entity.BaseCityCodeEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zlm
 * @date 2020-03-18 11:04:39
 */
public interface BaseCityCodeService extends IService<BaseCityCodeEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseCityCodeEntity> queryAll(Map<String, Object> params);

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
     * @param baseCityCode 
     * @return 新增结果
     */
    boolean add(BaseCityCodeEntity baseCityCode);

    /**
     * 根据主键更新
     *
     * @param baseCityCode 
     * @return 更新结果
     */
    boolean update(BaseCityCodeEntity baseCityCode);

    /**
     * 根据主键删除
     *
     * @param fullName fullName
     * @return 删除结果
     */
    boolean delete(String fullName);

    /**
     * 根据主键批量删除
     *
     * @param fullNames fullNames
     * @return 删除结果
     */
    boolean deleteBatch(String[] fullNames);

    /**
     * 根据城市名称查询代码
     * @param cityName 城市名称
     * @return
     */
    BaseCityCodeEntity findByName(String cityName);
}
