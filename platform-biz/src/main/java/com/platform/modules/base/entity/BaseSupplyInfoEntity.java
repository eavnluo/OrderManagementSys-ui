/*
 * 项目名称:platform-plus
 * 类名称:BaseSupplyInfoEntity.java
 * 包名称:com.platform.modules.base.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-03 17:21:59        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 供应商信息管理 实体
 *
 * @author jk
 * @date 2019-09-03 17:21:59
 */
@Data
@TableName("BASE_SUPPLY_INFO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseSupplyInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 编号
     */
    private String supplyCode;
    /**
     * 名称
     */
    private String supplyName;
    /**
     * 地址
     */
    private String supplyAddress;
    /**
     * 类型
     */
    private String type;
    /**
     * 邮编
     */
    private String postcode;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 联系人电话
     */
    private String linkPhone;
    /**
     * 联系人
     */
    private String linkName;
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
     * 主要联系人集合
     */
    @TableField(exist = false)
    private List<BaseLinkmanInfoEntity> linkmanList;

    /**
     * 车辆信息集合
     */
    @TableField(exist = false)
    private List<BaseCarInfoEntity> carList;

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
     * 主营业务描述
     */
    private String mainBusinessDesc;
    /**
     * 注册用户ID
     */
    private String registerUserId;

}
