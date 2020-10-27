/*
 * 项目名称:platform-plus
 * 类名称:BaseCustomerInfoEntity.java
 * 包名称:com.platform.modules.base.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-20 15:00:22        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户信息表实体
 *
 * @author jk
 * @date 2019-09-20 15:00:22
 */
@Data
@TableName("BASE_CUSTOMER_INFO")
public class BaseCustomerInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 编号
     */
    private String customerCode;
    /**
     * 名称
     */
    private String customerName;
    /**
     * 地址
     */
    private String customerAddress;
    /**
     * 产业类型
     */
    private String industryType;
    /**
     * 联系人
     */
    private String linkName;
    /**
     * 联系电话
     */
    private String linkPhone;
    /**
     * 省市区
     */
    private String region;
    /**
     * 国家
     */
    private String country;
    /**
     * 省
     */
    private String privince;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 备注1
     */
    @TableField(fill = FieldFill.UPDATE)
    private String remark1;
    /**
     * 备注2
     */
    @TableField(fill = FieldFill.UPDATE)
    private String remark2;
    /**
     * 备注3
     */
    @TableField(fill = FieldFill.UPDATE)
    private String remark3;
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
     * 删除标识
     */
    @TableLogic
    private String delFlag;

    /**
     * 类型 C-采购商；F-分销商；G-工厂
     */
    private String type;
    /**
     *
     */
    private Integer infoNum;
    /**
     * 公司法人
     */
    private String legalPerson;
    /**
     * 税务登记证号码
     */
    private String taxRegistrationNo;
    /**
     * 邮件地址
     */
    private String emailAddress;
    /**
     * 主要产品
     */
    private String mainProduct;
    /**
     * 主营业务
     */
    private String mainBusiness;
    /**
     * 注册用户ID
     */
    private String registerUserId;
}
