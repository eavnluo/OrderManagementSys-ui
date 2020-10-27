package com.platform.modules.wms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 入库物流信息
 * @author Created by Tp-wangwh on 2019-9-4.
 */
@Data
@TableName(value ="store_in_logistic" )
public class InPlanLogisticEntity{
    private static final long serialVersionUID = 1L;
    @TableId
    private String id;

    /**
     * 工作单号
     */
    private String orderItem;
    /**
     * 运输单号
     */
    private String wayBillNo;
    /**
     * 运单号
     */
    private String carrierId;
    /**
     * 承运单位
     */
    private String carrierCompany;
    /**
     * 承运商名称
     */
    private String carrierName;
    /**
     * 承运人
     */
    private String carrierMan;
    /**
     * 承运人手机号
     */
    private String carrierMobile;
    /**
     * 运费
     */
    private Integer carrierFreightFee;
    /**
     * 送达时间
     */
    private Date arriveDate;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 收货人id
     */
    private String consigneeId;
    /**
     * 配货地址
     */
    private String deliveryAddress;
    /**
     * 配货地址—省级
     */
    private String deliveryProvince;
    /**
     * 配货地址—省级编码
     */
    private String deliveryProvinceCode;
    /**
     * 配货城市
     */
    private String deliveryCity;
    /**
     * 配货城市编码
     */
    private String deliveryCityCode;
    /**
     * 配货地址—区县
     */
    private String deliveryDistrict;
    /**
     * 配货地址—区县编码
     */
    private String deliveryDistrictCode;
    /**
     * 配货地址—乡镇
     */
    private String deliveryTown;
    /**
     * 配货地址—乡镇编码
     */
    private String deliveryTownCode;
    /**
     * 配货公司名称
     */
    private String deliveryCompany;
    /**
     * 配货公司编码
     */
    private String deliveryCompanyCode;
    /**
     * 配货员id
     */
    private String deliveryPartyId;
    /**
     * 配货员姓名
     */
    private String deliveryName;
    /**
     * 配货员电话
     */
    private String deliveryMobile;
    /**
     * 预计到达时间
     */
    private Date expectArriveDate;
    /**
     * 选择送达时间
     */
    private Date expectPickDate;
    /**
     * 配送费
     */
    private Integer paymentCollection;
    /**
     * 支付方式
     */
    private String paymentMethod;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 收货编号
     */
    private String receiptNum;
    /**
     * 收货方式
     */
    private String receiptType;
    /**
     * 收货公司
     */
    private String receiveCompany;
    /**
     * 收货人手机号
     */
    private String receiveMobile;
    /**
     * 收货地址
     */
    private String receiveAddress;
    /**
     * 收货地址-省
     */
    private String receiveProvince;
    /**
     * 收货地址-省编码
     */
    private String receiveProvinceCode;
    /**
     * 收货地址-城市
     */
    private String receiveCity;
    /**
     * 收货地址-城市编码
     */
    private String receiveCityCode;
    /**
     * 收货地址-区县
     */
    private String receiveDistrict;
    /**
     * 收货地址-区县编码
     */
    private String receiveDistrictCode;
    /**
     * 收货地址-乡镇
     */
    private String receiveTown;
    /**
     * 收货地址-乡镇编码
     */
    private String receiveTownCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 配送方式
     */
    private String transportTypeValue;
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
     * 下发标识
     */
    private String publishFlag;

}
