/*
 * 项目名称:platform-plus
 * 类名称:StoreInventoryInfoEntity.java
 * 包名称:com.platform.modules.store.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 14:14:39        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存管理 实体
 *
 * @author jk
 * @date 2019-09-05 14:14:39
 */
@Data
@TableName("STORE_INVENTORY_INFO")
public class StoreInventoryInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 商品ID
     */
    private String itemId;
    /**
     * 商品编码
     */
    private String itemCode;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 库存数量
     */
    private Double itemQuantity;
    /**
     * 货主编号
     */
    private String ownerCode;
    /**
     * 货主姓名
     */
    private String ownerName;
    /**
     * 仓库编码
     */
    private String storeCode;
    /**
     * 仓库名称
     */
    private String storeName;
    /**
     * 货位编码
     */
    private String spaceCode;
    /**
     * 存放位置
     */
    private String spaceAddress;
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
     * 库位
     */
    private String storeHouse;


}
