/*
 * 项目名称:platform-plus
 * 类名称:PriceDetailEntity.java
 * 包名称:com.platform.modules.price.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 13:23:09        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.price.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 报价单包含的报价项清单实体
 *
 * @author Mr.panbb
 * @date 2020-03-24 13:23:09
 */
@Data
@TableName("PRICE_DETAIL")
public class PriceDetailEntity implements Serializable {
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
    private Double price;
    /**
     * 增值税税率
     */
    private String vatRate;
    /**
     * 成本
     */
    private Double costPrice;
    /**
     * 合计
     */
    private Double totalPrice;
    /**
     * 供应商编号
     */
    private String supplyCode;
    /**
     * 报价单ID
     */
    private String priceInfoId;
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
}
