/*
 * 项目名称:platform-plus
 * 类名称:BaseStoreInfoEntity.java
 * 包名称:com.platform.modules.base.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-03 15:38:50        stg     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓库基础信息表 实体
 *
 * @author stg
 * @date 2019-09-03 15:38:50
 */
@Data
@TableName("BASE_STORE_INFO")
public class BaseStoreInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 是否删除
     */
    @TableLogic
    private String delFlag;
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
     * 主键ID
     */
    @TableId
    private String id;
    /**
     * 仓库编码
     */
    private String storeCode;
    /**
     * 仓库名称
     */
    private String sotreName;
    /**
     * 来源公司代码
     */
    private String source;
    /**
     * 仓库地址
     */
    private String sotreAddress;
}
