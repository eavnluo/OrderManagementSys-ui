/*
 * 项目名称:platform-plus
 * 类名称:StatementInfoEntity.java
 * 包名称:com.platform.modules.statement.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-30 14:33:17        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.bill.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 结算单实体
 *
 * @author Mr.panbb
 * @date 2020-03-30 14:33:17
 */
@Data
@TableName("BILL_INFO")
public class BillInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 账单号
     */
    private String billNo;
    /**
     * 发票号
     */
    private String invoiceNo;
    /**
     * 工作单
     */
    private String orderItem;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 客户编号
     */
    private String customerCode;
    /**
     * 供应商编号
     */
    private String supplyCode;
    /**
     * 订单创建时间
     */
    private Date orderTime;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 增值税
     */
    private BigDecimal vatRate;
    /**
     * 税前合计
     */
    private BigDecimal beforeTax;
    /**
     * 税后合计
     */
    private BigDecimal afterTax;
    /**
     * 已确认费用
     */
    private BigDecimal confirmedMoney;
    /**
     * 未确认费用
     */
    private BigDecimal unconfirmedMoney;
    /**
     * 0:正常，1：删除
     */
    private String status;
    /**
     * 账单类型: 01：客户账单, 02：卡车供应商, 03：仓储供应商
     */
    private String type;
    /**
     * 是否确认，0：未确认，1：已确认
     */
    private String isConfirm;
    /**
     * 备注
     */
    private String remark;
    /**
     * 应付账单关联应收账单
     */
    private String parentId;
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
     * 费用清单
     */
    @TableField(exist = false)
    private List<BillItemEntity> itemList;
}
