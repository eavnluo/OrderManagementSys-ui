/*
 * 项目名称:platform-plus
 * 类名称:OrderDetailGoodInfoEntity.java
 * 包名称:com.platform.modules.order.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:30        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.modules.wms.entity.InPlanGoodsEntity;
import com.platform.modules.wms.entity.OutGoodsEntity;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单详情表 实体
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:30
 */
@Data
@TableName("ORDER_DETAIL_GOODS_INFO")
public class OrderDetailGoodsInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 主键ID
     */
    @TableId
    private String rowId;
    /**
     * 订单号内订单号
     */
    private String orderItem;
    /**
     * 商品自增编号
     */
    private String lineNum;
    /**
     * 品名
     */
    private String goodsName;
    /**
     * 商品编码
     */
    private String goodsCode;
    /**
     * 型号
     */
    private String goodsModel;
    /**
     * 条码号
     */
    private String barCode;
    /**
     * 数量
     */
    private Double goodsNum;
    /**
     * 单价
     */
    private Double price;
    /**
     * 币种
     */
    private String currencyType;
    /**
     * 总额
     */
    private Double amount;
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 规格
     */
    private String specification;
    /**
     * 品牌
     */
    private String brand;
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
    /**
     * 生产日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date productionDate;
	/**
	 * 库位
	 */
	private String storageLocation;


    /**
     * 转为入库商品信息
     * @return
     */
    public InPlanGoodsEntity toInPlanGoods() {
        InPlanGoodsEntity inPlanGoodsEntity=new InPlanGoodsEntity();
        //TODO 将商品信息转为 入库的商品信息
        inPlanGoodsEntity.setGoodsCode(this.goodsCode);
        inPlanGoodsEntity.setGoodsName(this.goodsName);
        inPlanGoodsEntity.setInboundQty(this.getGoodsNum());
        inPlanGoodsEntity.setPrice(this.price);
        return inPlanGoodsEntity;
    }

    /**
     * 转为出库商品信息
     * @return
     */
    public OutGoodsEntity toOutGoods() {
        OutGoodsEntity outGoodsEntity = new OutGoodsEntity();
        outGoodsEntity.setGoodsCode(this.goodsCode);
        outGoodsEntity.setGoodsName(this.goodsName);
        outGoodsEntity.setOutboundQty(this.goodsNum);
        outGoodsEntity.setPrice(this.price);
        return outGoodsEntity;

    }
}
