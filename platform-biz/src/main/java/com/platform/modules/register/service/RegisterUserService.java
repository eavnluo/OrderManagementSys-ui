/*
 * 项目名称:platform-plus
 * 类名称:RegisterUserService.java
 * 包名称:com.platform.modules.register.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-17 08:52:12        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.register.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.requesting.entity.RequestingResearchEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author Mr.panbb
 * @date 2020-03-17 08:52:12
 */
public interface RegisterUserService extends IService<RegisterUserEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<RegisterUserEntity> queryAll(Map<String, Object> params);

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
     * @param registerUser 
     * @return 新增结果
     */
    boolean add(RegisterUserEntity registerUser);

    /**
     * 根据主键更新
     *
     * @param registerUser 
     * @return 更新结果
     */
    boolean update(RegisterUserEntity registerUser);

    /**
     * 根据主键删除
     *
     * @param id 用户id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids 用户id
     * @return 删除结果
     */
    boolean deleteBatch(List<String> ids);

    /**
     * 批量审核
     * @param ids 用户id
     * @return 审批结果
     */
    boolean checkBatch(String[] ids);

    /**
     * 禁用用户
     * @param id 用户id
     * @return
     */
    boolean enable(String id);

    /**
     * 用户登录
     * @param phoneNumber
     * @param password
     * @return
     */
    RegisterUserEntity login(String phoneNumber, String password);

    /**
     * 从需求调研信息冲创建注册账户
     * @param requestingResearch
     */
    void addFromRequesting(RequestingResearchEntity requestingResearch);

    /**
     * 修改密码
     * @param phoneNumber 手机号
     * @param password 密码
     */
    void editPassword(String phoneNumber, String password);

    /**
     * 修改认证状态
     * @param id id
     * @param authenticationStatus 认证状态
     * @return
     */
    boolean updateAuthenticationStatus(String id,String authenticationStatus);

    /**
     * 判断是否认证
     * @param entity 登录用户信息
     * @return true 已认证，false 未认证
     */
    boolean isAuthentication(RegisterUserEntity entity);

    /**
     * 校验用户类型是不是卡车供应商类型
     * @param user
     * @return
     */
    BaseSupplyInfoEntity checkoutUserType(RegisterUserEntity user);
}
