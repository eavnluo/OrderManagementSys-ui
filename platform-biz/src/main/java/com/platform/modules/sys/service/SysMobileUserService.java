/*
 * 项目名称:platform-plus
 * 类名称:SysMobileUserService.java
 * 包名称:com.platform.modules.sys.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-16 16:32:44        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.sys.entity.SysMobileUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 手持用户Service接口
 *
 * @author 凯晟admin
 * @date 2019-09-16 16:32:44
 */
public interface SysMobileUserService extends IService<SysMobileUserEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysMobileUserEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询手持用户
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增手持用户
     *
     * @param sysMobileUser 手持用户
     * @return 新增结果
     */
    boolean add(SysMobileUserEntity sysMobileUser);

    /**
     * 根据主键更新手持用户
     *
     * @param sysMobileUser 手持用户
     * @return 更新结果
     */
    boolean update(SysMobileUserEntity sysMobileUser);

    /**
     * 根据主键删除手持用户
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

    SysMobileUserEntity loginByMobile(String mobile, String password);
}
