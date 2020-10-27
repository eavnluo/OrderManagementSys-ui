/*
 * 项目名称:platform-plus
 * 类名称:OrderStatusFlowEntity.java
 * 包名称:com.platform.modules.order.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:31        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.platform.common.OrderStatus;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单状态流水表 实体
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:31
 */
@Data
@TableName("ORDER_STATUS_FLOW")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderStatusFlowEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 订单状态
     */
    private String orderStatus;
    /**
     * 订单状态CODE
     */
    private String statusCode;
    /**
     * 状态时间
     */
    private Date statusDate;
    /**
     * 备注
     */
    private String remarks;

    @TableField(exist = false)
    List<SysFileAttachmentEntity> fileList;

    public OrderStatusFlowEntity() {
    }
    /***
     *
     * @param orderItem 订单号
     * @param status 状态
     */
    public OrderStatusFlowEntity(String orderItem, OrderStatus status) {
       this(orderItem,status.getStatusName(),status.getCode());
    }
    /***
     *
     * @param orderItem 订单号
     * @param orderStatus 状态
     * @param statusCode code
     */
    public OrderStatusFlowEntity(String orderItem, String orderStatus, String statusCode) {
        this.orderItem = orderItem;
        this.orderStatus = orderStatus;
        this.statusCode = statusCode;
        this.statusDate= new Date();
    }
}
