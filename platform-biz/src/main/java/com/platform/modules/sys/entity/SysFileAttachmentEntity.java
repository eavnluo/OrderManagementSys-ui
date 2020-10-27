/*
 * 项目名称:platform-plus
 * 类名称:SysFileAttachmentEntity.java
 * 包名称:com.platform.modules.sys.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-25 14:44:58        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author zlm
 * @date 2020-03-25 14:44:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("SYS_FILE_ATTACHMENT")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysFileAttachmentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private String id;
    /**
     * 归属
     */
    private String belongTo;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件类型
     */
    private String fileSuffix;
    /**
     * 文件大小
     */
    private Double fileSize;
    /**
     * 文件路径
     */
    private String fileUrl;
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

    @TableField(exist = false)
    private String fileId;
    /**
     * 图片描述
     */
    private String imgDescribe;
    /**
     * 合作伙伴网站地址
     */
    private String linkAddress;

    public SysFileAttachmentEntity(String belongTo, String fileName, String fileSuffix, Double fileSize, String fileUrl) {
        this.belongTo = belongTo;
        this.fileName = fileName;
        this.fileSuffix = fileSuffix;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
    }
}
