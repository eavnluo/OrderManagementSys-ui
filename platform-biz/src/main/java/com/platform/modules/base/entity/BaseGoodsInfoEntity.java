/*
 * 项目名称:platform-plus
 * 类名称:BaseGoodsInfoEntity.java
 * 包名称:com.platform.modules.base.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-02 17:29:38        jk     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品基础信息表 实体
 *
 * @author jk
 * @date 2019-09-02 17:29:38
 */
@Data
@TableName("BASE_GOODS_INFO")
public class BaseGoodsInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 商品编号
     */
    private String goodsCode;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品类别编码
     */
    private String categoryCode;
    /**
     * 商品类别名称
     */
    private String categoryName;
    /**
     * 品牌
     */
    private String brand;
    /**
     * 规格
     */
    private String specification;
    /**
     * 条形码
     */
    private String barCode;
    /**
     * 单位
     */
    private String unit;
    /**
     * 产地
     */
    private String origin;
    /**
     * 单价(RMB)
     */
    private BigDecimal price;
    /**
     * 长（CM)

     */
    private Integer goodsLong;
    /**
     * 宽(CM)

     */
    private Integer goodsWidth;
    /**
     * 高(CM)

     */
    private Integer goodsHigh;
    /**
     * 重量(KG)

     */
    private Integer goodsWeight;
    /**
     * 颜色
     */
    private String color;
    /**
     * 备注1
     */
    @TableField(fill = FieldFill.UPDATE)
    private String remarks1;
    /**
     * 备注2
     */
    @TableField(fill = FieldFill.UPDATE)
    private String remarks2;
    /**
     * 备注3
     */
    @TableField(fill = FieldFill.UPDATE)
    private String remarks3;
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
     * 是否删除
     */
    @TableLogic
    private String delFlag;
    /**
     * 生产日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productionDate;
    /**
     * 周转时间
     */
    private String cycleTime;
    /**
     *
     */
    private Integer infoNum;
    /**
     * 型号
     */
    private String goodsModel;
    /**
     * 包装
     */
    private String goodsPacking;
    /**
     * 材质
     */
    private String goodsMaterial;
    /**
     * 批次
     */
    private String goodsBatch;
}
