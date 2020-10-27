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
@TableName(value ="store_in_plan_goods" )
public class InPlanGoodsEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 出库商品编号
     */
    @TableId
    private String outerGoodsCode;
    /**
     * 商品编码
     */
    private String goodsCode;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 入库包装数量
     */
    private Integer inboundPackCount;
    /**
     * 入库数量
     */
    private Double inboundQty;
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
     * 批号
     */
    private String batchNo;
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
    /**
     *工作订单
     */
    private String orderItem;
}
