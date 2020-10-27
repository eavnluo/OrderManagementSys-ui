/*
 * 项目名称:platform-plus
 * 类名称:WebsiteNewsEntity.java
 * 包名称:com.platform.modules.website.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-04-10 15:19:02        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.website.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author zlm
 * @date 2020-04-10 15:19:02
 */
@Data
@TableName("WEBSITE_NEWS")
public class WebsiteNewsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private String id;
    /**
     * 分类
     */
    private String newsCategory;
    /**
     * 标题
     */
    private String newsTitle;
    /**
     * 简介
     */
    private String newsDesc;
    /**
     * 内容
     */
    private String newsContent;
    /**
     * 发布时间
     */
    private Date publishTime;
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
    /**
     * 删除标识
     */
    @TableLogic
    private String delFlag;
    /**
     * 图片
     */
    @TableField(exist = false)
    private String newsImage;
    /**
     * 图片路径
     */
    @TableField(exist = false)
    private String fileUrl;
}
