/*
 * 项目名称:platform-plus
 * 类名称:RequestingResearchEntity.java
 * 包名称:com.platform.modules.requesting.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 14:20:16        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.requesting.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 需求调研实体
 *
 * @author Mr.panbb
 * @date 2020-03-18 14:20:16
 */
@Data
@TableName("REQUESTING_RESEARCH")
public class RequestingResearchEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 统一社会信用代码
     */
    private String uscc;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系电话
     */
    private String phoneNumber;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 细分领域
     */
    private String segmentation;
    /**
     * 产品范围
     */
    private String productRange;
    /**
     * 融资额度
     */
    private Double financingAmount;
    /**
     * 管理系统
     */
    private String managementSystem;
    /**
     * 其它系统
     */
    private String otherSystem;
    /**
     * 状态，0：正常，1：删除
     */
    private String status;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
}
