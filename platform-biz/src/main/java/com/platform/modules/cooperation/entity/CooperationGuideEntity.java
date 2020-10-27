/*
 * 项目名称:platform-plus
 * 类名称:CooperationGuideEntity.java
 * 包名称:com.platform.modules.cooperation.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-19 08:42:55        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.cooperation.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合作指南实体
 *
 * @author Mr.panbb
 * @date 2020-03-19 08:42:55
 */
@Data
@TableName("COOPERATION_GUIDE")
public class CooperationGuideEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 类型
     */
    private String type;
    /**
     * 面向对象
     */
    private String objectOriented;
    /**
     * 内容
     */
    private String content;
    /**
     * 状态，0:正常，1：删除
     */
    private String status;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
}
