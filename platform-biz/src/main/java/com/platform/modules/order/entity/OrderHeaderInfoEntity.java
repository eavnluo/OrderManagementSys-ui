/*
 * 项目名称:platform-plus
 * 类名称:OrderHeaderInfoEntity.java
 * 包名称:com.platform.modules.order.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:30        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.common.utils.DateUtils;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单头表 实体
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:30
 */
@Data
@TableName("ORDER_HEADER_INFO")
public class OrderHeaderInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 订单号
     */
    @NotNull(message = "订单号不允许为空")
    @NotEmpty(message = "订单号不允许为空")
    @Excel(name = "订单号", orderNum = "1")
    private String orderNo;
    /**
     * 工作单号
     */
    @Excel(name = "工作单号", orderNum = "2")
    private String orderItem;
    /**
     * 订单日期
     */
    private Date orderDate;
    /**
     * 订单类型
     */
    @NotNull(message = "订单类型不允许为空")
    @NotEmpty(message = "订单类型不允许为空")
    private String orderType;
    /**
     * 仓库编码
     */
    private String storeHouseCode;
    /**
     * 仓库编码
     */
    private String storeHouseName;
    /**
     * 发票单号
     */
    @NotNull(message = "发票单号不允许为空")
    @NotEmpty(message = "发票单号不允许为空")
    private String invoiceNo;
    /**
     * 买方编码
     */
    private String buyerCode;
    /**
     * 买方名称
     */
    private String buyerName;
    /**
     * 卖方编码
     */
    private String sellerCode;
    /**
     * 卖方名称
     */
    private String sellerName;
    /**
     * 起点，发货地点
     */
    @NotNull(message = "始发地不允许为空")
    @NotEmpty(message = "始发地不允许为空")
    private String startPoint;
    /**
     * 终点，送达地点
     */
    @NotNull(message = "目的地不允许为空")
    @NotEmpty(message = "目的地不允许为空")
    private String endPoint;
    /**
     * 供应商CODE
     */
    private String supplyCode;
    /**
     * 供应商名称
     */
    private String supplyName;
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
     * 产品list
     */
    @NotNull(message = "商品列表不允许为空")
    @NotEmpty(message = "商品列表不允许为空")
    @TableField(exist = false)
    private List<OrderDetailGoodsInfoEntity> goods;

    /**
     *
     */
    private Integer infoNum;

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
     * 仓库返回的出入库单号
     */
    private String dynamic;
    /**
     * 卡车供应商ID
     */
    @NotNull(message = "卡车供应商不允许为空")
    @NotEmpty(message = "卡车供应商不允许为空")
    private String carSupplyId;
    /**
     * 仓储供应商ID
     */
    @NotNull(message = "仓储供应商不允许为空")
    @NotEmpty(message = "仓储供应商不允许为空")
    private String storageSupplyId;
    /**
     * 件数
     */
    @NotNull(message = "件数供应商不允许为空")
    @NotEmpty(message = "件数供应商不允许为空")
    private String pieceNumber;
    /**
     * 重量
     */
    private String weight;
    /**
     * 发货人
     */
    @NotNull(message = "发货人不允许为空")
    @NotEmpty(message = "发货人不允许为空")
    private String sendGoodsPeople;
    /**
     * 发货人ID
     */
    private String sendGoodsPeopleId;
    /**
     * 收货人
     */
    @NotNull(message = "收货人不允许为空")
    @NotEmpty(message = "收货人不允许为空")
    private String receiveGoodsPeople;
    /**
     * 收货人ID
     */
    private String receiveGoodsPeopleId;
    /**
     * 客户id
     */
    private String customerId;
    /**
     * 卡车供应商完善订单信息状态，0-未完善，1-已完善
     */
    private String carSupplyStatus;
    /**
     * 仓储供应商完善订单信息状态，0-未完善，1-已完善
     */
    private String storageSupplyStatus;

    /**
     * 仓储供应商代码
     */
    @TableField(exist = false)
    private String storageSupplyCode;
    /**
     * 卡车供应商代码
     */
    @TableField(exist = false)
    private String carSupplyCode;
    /**
     * 客户代码
     */
    @TableField(exist = false)
    private String customerCode;

    /**
     * 出入库单号
     */
    private String outInOddNum;
    /**
     * 预计提货时间
     */
    @JsonFormat(pattern = DateUtils.DATE_PATTERN)
    @DateTimeFormat(pattern = DateUtils.DATE_PATTERN)
    private Date expectReceiveDate;
    /**
     * 发货地址
     */
    private String shippingAddress;
    /**
     * 提货地址
     */
    private String receivingAddress;

}
