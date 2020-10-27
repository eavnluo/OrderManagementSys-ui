package com.platform.modules.wms.bo;

import io.swagger.annotations.ApiModel;

/**
 * @author Created by Tp-wangwh on 2019-9-3.
 */
@ApiModel(value ="物流信息" )
public class LogisticsInfo {
    /***
     * 到达时间
     */
    String arriveDate;
    /***
     * 承运时间
     * */
    String carrierCompany;
    /**
     *
     */
    String carrierFreightFee;
    /**
     *
     */
    String carrierId;
    /**
     * 承运人
     */
    String carrierMan;
    /***
     * 承运人电话
     */
    String carrierMobile;
    /**
     * 收货人
     */
    String consignee;
    /***
     * 收货人ID
     */
    String consigneeId;
    /***
     * 收货地址
     */
    String deliveryAddress;
    /***
     * 收货城市
     */
    String deliveryCity;
    /**
     * 收货code
     */
    String deliveryCityCode;
    /***
     *
     */
    String deliveryCompany;
    /**
     *
     */
    String deliveryCompanyCode;
    /**
     * 运输地区
     */
    String deliveryDistrict;
    /**
     * 运输code
     */
    String deliveryDistrictCode;
    /***
     * 运输电话
     */
    String deliveryMobile;
    /**
     *
     */
    String deliveryName;
    /***
     *
     */
    String deliveryPartyId;
    /**
     * 承运省
     */
    String deliveryProvince;
    /**
     * 省编码
     */
    String deliveryProvinceCode;

    String deliveryTown;
    /**
     *
     */
    String deliveryTownCode;
    /**
     * 预计到达时间
     */
    String expectArriveDate;
    /**
     *
     */
    String expectPickDate;
    /**
     *
     */
    String logisticsInfoNo;
    /**
     * 运单号
     */
    String logisticsOrderNo;
    /**
     * 运费
     */
    int  paymentCollection;
    /**
     * 支付方式
     */
    String paymentMethod;
    /**
     *
     */
    String plateNumber;
    /**
     *
     */
    String receiptNum;
    /**
     *
     */
    String receiptType;
    /**
     * 接收地址
     */
    String receiveAddress;
    /**
     *
     */
    String receiveCity;
    /**
     *
     */
    String receiveCityCode;
    /**
     *
     */
    String receiveCompany;
    /**
     *
     */
    String receiveDistrict;
    /**
     *
     */
    String receiveDistrictCode;
    /**
     *
     */
    String receiveMobile;
    /**
     *
     */
    String receiveProvince;
    /**
     *
     */
    String receiveProvinceCode;
    /**
     *
     */
    String receiveTown;
    /**
     *
     */
    String receiveTownCode;
    /**
     *
     */
    String remark;
    /**
     * 运输方式
     */
    String transportTypeValue;
}
