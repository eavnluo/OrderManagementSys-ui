/*
 * 项目名称:platform-plus
 * 类名称:RegisterUserEntity.java
 * 包名称:com.platform.modules.register.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-17 08:52:12        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.register.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author Mr.panbb
 * @date 2020-03-17 08:52:12
 */
@Data
@TableName("REGISTER_USER")
public class RegisterUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 用户类型，0：客户，1：供应商
     */
    private String type;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 密码加密密钥
     */
    private String salt;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户状态，-1：删除，0：注册，1：生效
     */
    private String status;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 认证状态
     */
    private String authenticationStatus;

    /**
     * 短信验证码
     */
    @TableField(exist = false)
    private String code;
    /**
     * 短信类型
     */
    @TableField(exist = false)
    private String codeType;
}
