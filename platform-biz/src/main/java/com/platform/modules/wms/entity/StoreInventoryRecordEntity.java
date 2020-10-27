/*
 * 项目名称:platform-plus
 * 类名称:StoreInventoryRecordEntity.java
 * 包名称:com.platform.modules.store.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-23 11:52:28        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存变更记录实体
 *
 * @author Mr.panbb
 * @date 2019-09-23 11:52:28
 */
@Data
@TableName("STORE_INVENTORY_RECORD")
public class StoreInventoryRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 原始值
     */
    @TableId
    private Double oldValue;
    /**
     * 变更值
     */
    private Double varValue;
    /**
     * 最新值
     */
    private Double newValue;
    /**
     * 关联订单
     */
    private String orderItem;
    /**
     * 关联库存ID
     */
    private String stockId;
    /**
     * 变更时间
     */
    private Date updateTime;

    public StoreInventoryRecordEntity(){}
    public StoreInventoryRecordEntity(Double oldValue, Double varValue, Double newValue, String orderItem, String stockId) {
        this.oldValue = oldValue;
        this.varValue = varValue;
        this.newValue = newValue;
        this.orderItem = orderItem;
        this.stockId = stockId;
        this.updateTime = new Date();
    }
}
