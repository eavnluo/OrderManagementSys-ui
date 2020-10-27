/*
 * 项目名称:platform-plus
 * 类名称:BaseAreaService.java
 * 包名称:com.platform.modules.base.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-30 09:33:03        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.base.entity.BaseAreaEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 凯晟admin
 * @date 2019-09-30 09:33:03
 */
public interface BaseAreaService extends IService<BaseAreaEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseAreaEntity> queryAll(Map<String, Object> params);

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
     * @param baseArea 
     * @return 新增结果
     */
    boolean add(BaseAreaEntity baseArea);

    /**
     * 根据主键更新
     *
     * @param baseArea 
     * @return 更新结果
     */
    boolean update(BaseAreaEntity baseArea);

    /**
     * 根据主键删除
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
     * 通过名称查询省市编号
     * @param name
     * @return
     */
    String queryCode(String name);

    /**
     * 根据父ID获取列表
     * @param parentId
     * @return
     */
    List<BaseAreaEntity> getListByParentId(String parentId);

    /**
     * 根据ID获取城市名称，不包含"市"
     * @param id id
     * @return
     */
    String getCityName(String id);

}
