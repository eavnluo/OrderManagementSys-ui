package com.platform.modules.wms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 出库物流信息表
 *
 * @author Created by Tp-wangwh on 2019-9-4.
 */
@Data
@TableName(value ="store_out_logistic")
public class OutPlanLogisticEntity {

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
     * 承运单位
     */
    private String carrierCompany;
    /**
     * 运送费
     */
    private String carrierFreightFee;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 运单编号
     */
    private String carrierId;
    /**
     * 承运人
     */
    private String carrierMan;
    /**
     * 承运人手机号
     */
    private String carrierMobile;
    /**
     * 承运商名称
     */
    private String carrierName;
    /**
     * 收货人
     */
    private String consignee;
    /**
     * 收货人ID
     */
    private String consigneeId;
    /**
     * 交货地址
     */
    private String deliveryAddress;
    /**
     * 交货城市
     */
    private String deliveryCity;
    /**
     * 交货城市编码
     */
    private String deliveryCityCode;
    /**
     * 交货公司
     */
    private String deliveryCompany;
    /**
     * 交货公司编码
     */
    private String deliveryCompanyCode;
    /**
     * 交货地区
     */
    private String deliveryDistrict;
    /**
     * 交货地区编码
     */
    private String deliveryDistrictCode;
    /**
     * 交货手机号
     */
    private String deliveryMobile;
    /**
     * 交货方名称
     */
    private String deliveryName;
    /**
     * 交货方ID
     */
    private String deliveryPartyId;
    /**
     * 交货省
     */
    private String deliveryProvince;
    /**
     * 交货省编号
     */
    private String deliveryProvinceCode;
    /**
     * 交货街道
     */
    private String deliveryTown;
    /**
     * 交货街道编号
     */
    private String deliveryTownCode;
    /**
     * 收款
     */
    private String paymentCollection;
    /**
     * 收款方式
     */
    private String paymentMethod;
    /**
     * 收据编号
     */
    private String receiptNum;
    /**
     * 收据类型
     */
    private String receiptType;
    /**
     * 接收地址
     */
    private String receiveAddress;
    /**
     * 接收城市
     */
    private String receiveCity;
    /**
     * 接收城市代码
     */
    private String receiveCityCode;
    /**
     * 接收公司
     */
    private String receiveCompany;
    /**
     * 接收地区
     */
    private String receiveDistrict;
    /**
     * 接收地区代码
     */
    private String receiveDistrictCode;
    /**
     * 接收手机号码
     */
    private String receiveMobile;
    /**
     * 接收省
     */
    private String receiveProvince;
    /**
     * 接收省代码
     */
    private String receiveProvinceCode;
    /**
     * 接收街道
     */
    private String receiveTown;
    /**
     * 接收街道代码
     */
    private String receiveTownCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 运输附加费
     */
    private String transportExtraFee;
    /**
     * 运输类型
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
