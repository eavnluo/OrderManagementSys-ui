/*
 * 项目名称:platform-plus
 * 类名称:SysMobileUserEntity.java
 * 包名称:com.platform.modules.sys.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-16 16:32:44        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 手持用户实体
 *
 * @author 凯晟admin
 * @date 2019-09-16 16:32:44
 */
@Data
@TableName("SYS_MOBILE_USER")
public class SysMobileUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户类型
     */
    private String userType;


    /**
     * 删除标识
     */
    @TableLogic
    private String delFlag;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedTime;
}
