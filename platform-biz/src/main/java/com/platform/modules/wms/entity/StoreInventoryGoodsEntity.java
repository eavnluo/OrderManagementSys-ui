/*
 * 项目名称:platform-plus
 * 类名称:StoreInventoryGoodsEntity.java
 * 包名称:com.platform.modules.store.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-31 10:34:30        zlm     初版做成
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
 * 实体
 *
 * @author zlm
 * @date 2020-03-31 10:34:30
 */
@Data
@TableName("STORE_INVENTORY_GOODS")
public class StoreInventoryGoodsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    private String id;
    /**
     * 工作单号
     */
    private String orderItem;
    /**
     * 出库or入库
     */
    private String orderType;
    /**
     * 订单商品ID
     */
    private String orderGoodsId;
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 品名
     */
    private String goodsName;
    /**
     * 型号
     */
    private String goodsModel;
    /**
     * 规格
     */
    private String specification;
    /**
     * 包装
     */
    private String goodsPacking;
    /**
     * 材质
     */
    private String goodsMaterial;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 批次
     */
    private String goodsBatch;
    /**
     * 生产日期
     */
    private Date productionDate;
    /**
     * 数量
     */
    private Double goodsNum;
    /**
     * 单价
     */
    private Double price;
    /**
     * 出入库单号
     */
    private String outInOddNum;
    /**
     * 库位
     */
    private String storageLocation;
    /**
     * 仓库编码
     */
    private String storeCode;
	/**
	 * 客户代码
	 */
	private String customerCode;
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
    private String delFlag;
}
