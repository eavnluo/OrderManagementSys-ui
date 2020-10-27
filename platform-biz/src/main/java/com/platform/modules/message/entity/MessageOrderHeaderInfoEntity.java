/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderHeaderInfoEntity.java
 * 包名称:com.platform.modules.message.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 15:13:17        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单头表  实体
 *
 * @author jk
 * @date 2019-09-10 15:13:17
 */
@Data
@TableName("MESSAGE_ORDER_HEADER_INFO")
public class MessageOrderHeaderInfoEntity implements Serializable {
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
     * 主键
     */
    @TableId
    private String id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 订单日期
     */
    private Date orderDate;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 仓库编码
     */
    private String storeHouseCode;
    /**
     * 商品编码
     */
    private String goodCode;
    /**
     * 商品名称
     */
    private String goodName;
    /**
     * 商品数量
     */
    private String goodCount;
    /**
     * 货主名称
     */
    private String ownerName;
    /**
     * 货主编码
     */
    private String ownerCode;
    /**
     * 供应商CODE
     */
    private String supplyCode;
    /**
     * 供应商名称
     */
    private String supplyName;
    /**
     * 来源
     */
    private String source;
    /**
     * 订单级别
     */
    private Integer oderLevel;
    /**
     * 收货联系人
     */
    private String receiver;
    /**
     * 收货联系人电话
     */
    private String receiveTel;
    /**
     * 收货联系人手机
     */
    private String receivePhone;
    /**
     * 收省市区
     */
    private String receiveRegion;
    /**
     * 收货人市
     */
    private String receiveCity;
    /**
     * 收货人详细地址
     */
    private String receiveAddress;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 订单更新时间
     */
    private Date statusUpdte;
    /**
     * 订单完成时间
     */
    private Date complateDate;
    /**
     * 订单状态
     */
    private String statusName;

    /**
     * 订单查询时间(开始)
     */
    @TableField(exist = false)
    private Date dateFrom;
    /**
     * 订单查询时间段(结束)
     */
    @TableField(exist = false)
    private Date dateTo;

}
