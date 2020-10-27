/*
 * 项目名称:platform-plus
 * 类名称:StatementItemEntity.java
 * 包名称:com.platform.modules.statement.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-30 14:33:17        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.bill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算项实体
 *
 * @author Mr.panbb
 * @date 2020-03-30 14:33:17
 */
@Data
@TableName("Bill_ITEM")
@NoArgsConstructor
public class BillItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 费用名称
     */
    private String name;
    /**
     * 费用代码
     */
    private String code;
    /**
     * 费用类型
     */
    private String type;
    /**
     * 单位
     */
    private String unit;
    /**
     * 金额
     */
    private BigDecimal price;
    /**
     * 增值税税率
     */
    private String vatRate;
    /**
     * 合计
     */
    private BigDecimal totalPrice;
    /**
     * 客户编号
     */
    private String supplyCode;
    /**
     * 成本
     */
    private BigDecimal costPrice;
    /**
     * 利润
     */
    private BigDecimal profit;
    /**
     * 始发地
     */
    private String startArea;
    /**
     * 目的地
     */
    private String endArea;
    /**
     * 是否是运输费用项，0：否，1：是
     */
    private String isTransport;
    /**
     * 0:正常，1：删除
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
    /**
     * 是否确认
     */
    private String isConfirm;
    /**
     * 结算单ID
     */
    private String infoId;

    public BillItemEntity(BillItemEntity entity) {
        this.code = entity.getCode();
        this.name = entity.getName();
        this.type = entity.getType();
        this.unit = entity.getUnit();
        this.vatRate = entity.getVatRate();
        this.price = entity.getCostPrice();
        this.supplyCode = entity.getSupplyCode();
        this.startArea = entity.getStartArea();
        this.endArea = entity.getEndArea();
        this.isTransport = entity.getIsTransport();
    }
}
