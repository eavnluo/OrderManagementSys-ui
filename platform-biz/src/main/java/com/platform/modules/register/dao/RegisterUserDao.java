/*
 * 项目名称:platform-plus
 * 类名称:RegisterUserDao.java
 * 包名称:com.platform.modules.register.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-17 08:52:12        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.register.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.register.entity.RegisterUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author Mr.panbb
 * @date 2020-03-17 08:52:12
 */
@Mapper
public interface RegisterUserDao extends BaseMapper<RegisterUserEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<RegisterUserEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<RegisterUserEntity> selectRegisterUserPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 更新信息状态
     * @param list 主键列表
     * @param updateTime 更新时间
     * @param status 状态值
     * @return 更新结果
     */
    boolean updateStatusByIds(@Param("list") List<String> list, @Param("updateTime") Date updateTime, @Param("status") String status);

    /**
     * 根据手机号获取用户
     * @param phoneNumber 手机号
     * @param status 状态
     * @return 返回用户信息
     */
    RegisterUserEntity getByPhone(@Param("phoneNumber") String phoneNumber, @Param("status") String status);

    /**
     * 修改密码
     * @param phoneNumber 手机号
     * @param password 新密码
     * @param updateTime 更新时间
     */
    void editPassword(@Param("phoneNumber") String phoneNumber,
                      @Param("password") String password,
                      @Param("updateTime") Date updateTime);

	/**
	 * 修改认证状态
	 * @param id id
	 * @param authenticationStatus 状态代码
	 * @return
	 */
	Integer updateAuthenticationStatus(@Param("id") String id, @Param("authenticationStatus") String authenticationStatus);
}
