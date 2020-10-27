/*
 * 项目名称:platform-plus
 * 类名称:OrderGoodUniqueCodeScanEntity.java
 * 包名称:com.platform.modules.order.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-12 13:22:59        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品唯一编码扫描表 实体
 *
 * @author jk
 * @date 2019-09-12 13:22:59
 */
@Data
@TableName("ORDER_GOOD_UNIQUE_CODE_SCAN")
public class OrderGoodUniqueCodeScanEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 内部单号
     */
    private String orderItem;
    /**
     * 操作类型
     */
    private String operateType;
    /**
     * 产品名称
     */
    private String goodName;
    /**
     * 产品编码
     */
    private String goodCode;
    /**
     * 唯一标识码
     */
    private String uniqueCode;
    /**
     * 操作人姓名
     */
    private String operatorName;
    /**
     * 操作人编码
     */
    private String operatorCode;
    /**
     * 操作时间
     */
    private Date operatorDate;
    /**
     * 操作结果
     */
    private String operateResult;

    /**
     * 库位
     */
    private String storeHouse;
    /**
     * 库区
     */
    private String storeArea;
    /**
     * 删除标识
     */
    private String delFlag;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间（入库时间）
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间（出库时间）
     */
    private Date updatedTime;

    /**
     * 仓库编码
     */
    private String storeCode;

    /**
     * 是否入库:0-否；1-是
     */
    private String isInStore;

    /**
     * 扫描结果
     */
    public static final String OPERATE_RESULT_SUCCESS = "SUCCESS";
    public static final String OPERATE_RESULT_FAILURE = "FAILURE";
}
