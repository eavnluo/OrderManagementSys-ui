/*
 * 项目名称:platform-plus
 * 类名称:BaseCarInfoEntity.java
 * 包名称:com.platform.modules.base.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-29 09:32:01        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author zlm
 * @date 2019-09-29 09:32:01
 */
@Data
@TableName("BASE_CAR_INFO")
public class BaseCarInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 归属，供应商ID
     */
    private String belongTo;
    /**
     * 车牌
     */
    private String plateNumber;
    /**
     * 型号
     */
    private String type;
    /**
     * 司机
     */
    private String driver;
    /**
     * 司机联系方式
     */
    private String driverLink;
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
    private String delFlag;
    /**
     * 供应商名称
     */
    @TableField(exist = false)
    private String supplyName;
    /**
     * 供应商编号
     */
    @TableField(exist = false)
    private String supplyCode;
}
