/*
 * 项目名称:platform-plus
 * 类名称:OrderCarRelationEntity.java
 * 包名称:com.platform.modules.order.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 13:04:44        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author zlm
 * @date 2020-03-24 13:04:44
 */
@Data
@TableName("ORDER_CAR_RELATION")
public class OrderCarRelationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private String id;
    /**
     * 工作单号
     */
    private String orderItem;
    /**
     * 运单号
     */
    private String wayBillNo;
    /**
     * 签收单号
     */
    private String signNumber;
    /**
     * 车辆ID
     */
    private String carId;
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
    @TableLogic
    private String delFlag;
}
