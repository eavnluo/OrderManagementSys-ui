/*
 * 项目名称:platform-plus
 * 类名称:BaseStatusCodeEntity.java
 * 包名称:com.platform.modules.base.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-09 08:24:39        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 状态维护表 实体
 *
 * @author 凯晟admin
 * @date 2019-09-09 08:24:39
 */
@Data
@TableName("BASE_STATUS_CODE")
public class BaseStatusCodeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 删除标识
     */
    private String delFlag;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedDate;
    /**
     * 
     */
    @TableId
    private String id;
    /**
     * 状态类型
     */
    private String statusCode;
    /**
     * 状态名称
     */
    private String statusName;
    /**
     * 状态(R入库 I 出库)
     */
    private String statusType;
    /**
     * java的bean对象
     */
    private String beanName;
    /**
     * java类的地址
     */
    private String classPath;
    /***
     * 排序字段
     */
    private int sort;

    /**
     * 状态图标
     */
    private String icon;
}
