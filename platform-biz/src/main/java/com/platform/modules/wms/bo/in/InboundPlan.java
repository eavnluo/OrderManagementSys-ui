package com.platform.modules.wms.bo.in;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Created by Tp-wangwh on 2019-9-3.
 */
@ApiModel(value = "入库单信息")
@Data
public class InboundPlan {
    /***
     * 非空:业务编码，上游业务编码，对应仓储的clientNo,外部业务编码长度不能超过64
     */
   @NotNull
   String  bizCode;
    /**
     * 可空:自定义字段1,自定义字段1长度不能超过64位
     */
   String custom1;
    /**
     * 可空:自定义字段1,自定义字段1长度不能超过64位
     */
   String custom2;
    /**
     * 可空:自定义字段1,自定义字段1长度不能超过64位
     */
   String custom3;
    /***
     * 可空:客户订单编号,客户订单编号长度不能超过64位
      */
   String  customerOrderNo;
    /**
     * 可空:预计到达时间
      */
   String expectArriveDate;
    /**
     * 可空:入库类型code
      */
   String inboundTypeCode;

   String  logisticsType;
    /***
     *物流信息
     */
   Object logisticsInfo;
    /**
     *可空:物流单号
     */
   String logisticsNo;
    /***
     * 经办人, 可空，长度不能超过50
     */
   String operatorName;
    /***
     * 制单人, 可空，长度不能超过50
     */
   String orderMaker;
    /**
     * 可空:备注,备注不能超过255个字符
     */
   String remark;
    /**
     * 可空:供货商,供货商字段长度不能超过64位
     */
   String supplierName;
    /**
     * 非空:仓库code,仓库编码长度不能超过255
     */
    @NotNull
   String warehouseCode;
    /**
     * 可空:仓库名称,仓库编码长度不能超过255
     */
   String warehouseName;
    /**
     * 商品集合
     */
   List<Object> planGoods;
    /***
     * ---商品总价，可空
     */
   Integer totalPrice;
    /***
     *
     */
    @NotNull
   String thirdPartyId;

    public InboundPlan(String bizCode, String logisticsType, Object
            logisticsInfo, String warehouseCode, String thirdPartyId ){
        this.bizCode=bizCode;
        this.logisticsType=logisticsType;
        this.logisticsInfo=logisticsInfo;
        this.warehouseCode=warehouseCode;
        this.thirdPartyId=thirdPartyId;
    }


}
