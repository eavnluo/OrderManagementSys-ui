/*
 * 项目名称:platform-plus
 * 类名称:PriceInfoEntity.java
 * 包名称:com.platform.modules.price.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 13:23:09        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.price.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 价格信息实体
 *
 * @author Mr.panbb
 * @date 2020-03-24 13:23:09
 */
@Data
@TableName("PRICE_INFO")
public class PriceInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 报价单号
     */
    private String priceNo;
    /**
     * 公司代码
     */
    private String companyCode;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 报价人
     */
    private String bidder;
    /**
     * 报价时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date quoteTime;
    /**
     * 生效时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectTime;
    /**
     * 到期时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireTime;
    /**
     * 始发地
     */
    private String startArea;
    /**
     * 目的地
     */
    private String endArea;
    /**
     * 报价类型：0：客户报价，1：供应商报价
     */
    private String type;
    /**
     * 结算周期
     */
    private String settlementCycle;
    /**
     * 客户代表
     */
    private String customerRepresentative;
    /**
     * 审批人
     */
    private String approver;
    /**
     * 0:正常，1：删除
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
    /**
     * 价格清单
     */
    @TableField(exist = false)
    private List<PriceDetailEntity> priceDetailList;
    /**
     * 附件列表
     */
    @TableField(exist = false)
    private List<SysFileAttachmentEntity> fileList;
}
