package com.platform.modules.wms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author Created by Tp-wangwh on 2019-9-4.
 */
@Data
@TableName(value = "store_out_plan_goods")
public class OutGoodsEntity{
    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 货物代码
     */
    private String goodsCode;
    /**
     * 货物名称
     */
    private String goodsName;
    /**
     * 出库包数
     */
    private Integer outboundPackCount;
    /**
     * 出库数量
     */
    private Double outboundQty;
    /**
     * 出库货物代码
     */
    private String outerGoodsCode;
    /**
     * 规格
     */
    private String packUnit;
    /**
     * 价格
     */
    private Double price;
    /**
     * 库存单位
     */
    private String sku;
    /**
     * 单位
     */
    private String unit;
    /**
     * 体积
     */
    private String volume;
    /**
     * 重量
     */
    private String weight;
    /**
     * 总价
     */
    private Double totalPrice;
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
     * 工作单号
     */
    private String orderItem;
    /**
     * 自定义字段1
     */
    private String custom1;
    /**
     * 自定义字段2
     */
    private String custom2;
    /**
     * 自定义字段3
     */
    private String custom3;
}
