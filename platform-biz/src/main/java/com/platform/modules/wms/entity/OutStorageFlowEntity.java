package com.platform.modules.wms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 出库流水计划信息
 * @author Created by Tp-wangwh on 2019-9-4.
 */
@Data
@TableName(value ="store_out_flow")
public class OutStorageFlowEntity  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 上游业务单号
     */
    @TableId
    private String bizCode;
    /**
     * 业务来源方
     */
    private String bizSource;
    /**
     * 承运商ID，服务商ID
     */
    private String carrierId;
    /**
     * 承运商，服务商
     */
    private String carrierName;
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
     * 客户订单编号
     */
    private String customerOrderNo;
    /**
     * 物流服务类型，配送, 自提
     */
    private String logisticsType;
    /**
     * 经办人
     */
    private String operatorName;
    /**
     * 制单人
     */
    private String orderMaker;
    /**
     * 出库类型，上游业务订单类型
     */
    private String outboundTypeCode;
    /**
     * 货主ID
     */
    private String ownerId;
    /**
     * 货主名称
     */
    private String ownerName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 接口版本
     */
    private String version;
    /**
     * 仓库code
     */
    private String warehouseCode;
    /**
     * 第三方ID
     */
    private String thirdPartyId;
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
     * wms的订单号
     */
    private String wmsOrderNo;

}
