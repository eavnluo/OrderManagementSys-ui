package com.platform.modules.wms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 入库订单数据
 * @author Created by Tp-wangwh on 2019-9-4.
 */
@Data
@TableName(value = "store_in_flow_plan")
public class InStorageFlowEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 业务编码 上游业务编码，对应仓储的clientNo
     */
    @TableId
    private String bizCode;
    /**
     * 入库类型code
     */
    private String inboundTypeCode;
    /***
     * 工作单号
     */
    private String orderItem;
    /**
     * 物流单号
     */
    private String logisticsNo;
    /**
     * 客户订单编号
     */
    private String customerOrderNo;
    /**
     * 配送方式
     */
    private String logisticsType;
    /**
     * 预计到达时间
     */
    private Date expectArriveDate;
    /**
     * 仓库code
     */
    private String warehouseCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 供货商
     */
    private String supplierName;
    /**
     * 商品总价
     */
    private Integer totalPrice;
    /**
     * 第三方ID
     */
    private String thirdPartyId;
    /**
     * 经办人
     */
    private String operatorName;
    /**
     * 制单人
     */
    private String orderMaker;
    /**
     * wms的订单号
     */
    private String wmsOrderNo;
    /**
     * 删除标识
     */
    @TableLogic
    private String delFlag;
    /**
     * 状态码
     */
    private String statusCode;
    /**
     * 状态名称
     */
    private String statusName;
    /**
     * 状态更新时间
     */

    private Date updateStatusDate;

    /**
     * 自定义字段1
     */
    private String custom1;
    /**
     * 自定义字段2
     */
    private String custom2;
    /**
     * 自定义字段3
     */
    private String custom3;
    /**
     * 备注
     */
    private String remark;
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



}
