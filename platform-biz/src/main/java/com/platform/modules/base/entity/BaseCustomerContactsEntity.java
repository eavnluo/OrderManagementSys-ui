/*
 * 项目名称:platform-plus
 * 类名称:BaseCustomerContactsEntity.java
 * 包名称:com.platform.modules.base.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-04-14 16:12:52        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author zlm
 * @date 2020-04-14 16:12:52
 */
@Data
@TableName("BASE_CUSTOMER_CONTACTS")
public class BaseCustomerContactsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 公司
     */
    private String company;
    /**
     * 地址
     */
    private String address;
    /**
     * 产业类型
     */
    private String industryType;
    /**
     * 国家
     */
    private String country;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    /**
     * 类型
     */
    private String type;
    /**
     * 注册用户ID
     */
    private String registerUserId;
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
    /**
     * 删除标识
     */
    @TableLogic
    private String delFlag;
}
