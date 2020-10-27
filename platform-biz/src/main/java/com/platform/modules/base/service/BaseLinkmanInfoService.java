/*
 * 项目名称:platform-plus
 * 类名称:BaseLinkmanInfoService.java
 * 包名称:com.platform.modules.base.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-26 11:30:23        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.base.entity.BaseLinkmanInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zlm
 * @date 2019-09-26 11:30:23
 */
public interface BaseLinkmanInfoService extends IService<BaseLinkmanInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseLinkmanInfoEntity> queryAll(Map<String, Object> params);

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
     * @param baseLinkmanInfo 
     * @return 新增结果
     */
    boolean add(BaseLinkmanInfoEntity baseLinkmanInfo);

    /**
     * 根据主键更新
     *
     * @param baseLinkmanInfo 
     * @return 更新结果
     */
    boolean update(BaseLinkmanInfoEntity baseLinkmanInfo);

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
     * 根据归属修改对应数据的删除标识
     * @param ids 归属ids
     * @return 受影响的行数
     */
    boolean updateDelFlagByBelongTo(String[] ids);

    /**
     * 根据归属查找对应的联系人列表
     * @param belongTo
     * @return
     */
    List<BaseLinkmanInfoEntity> findByBelongTo(String belongTo);
}
