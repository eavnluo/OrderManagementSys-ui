/*
 * 项目名称:platform-plus
 * 类名称:BaseStatusCodeService.java
 * 包名称:com.platform.modules.base.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-09 08:24:39        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.base.entity.BaseStatusCodeEntity;
import com.platform.modules.base.entity.BaseStatusGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 状态维护表 Service接口
 *
 * @author 凯晟admin
 * @date 2019-09-09 08:24:39
 */
public interface BaseStatusCodeService extends IService<BaseStatusCodeEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseStatusCodeEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询状态维护表 
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增状态维护表 
     *
     * @param baseStatusCode 状态维护表 
     * @return 新增结果
     */
    boolean add(BaseStatusCodeEntity baseStatusCode);

    /**
     * 根据主键更新状态维护表 
     *
     * @param baseStatusCode 状态维护表 
     * @return 更新结果
     */
    boolean update(BaseStatusCodeEntity baseStatusCode);

    /**
     * 根据主键删除状态维护表 
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
     * 获取状态分组列表
     * @return
     */
    List<BaseStatusGroupEntity> getStatusGroup();
}
