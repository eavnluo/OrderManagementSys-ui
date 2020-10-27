package com.platform.modules.wms.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Created by Tp-wangwh on 2019-9-3.
 */
@ApiModel("订单商品")
@Data
public class OrderGood {
    /**
     * 商品编码
     */
    String batchNo;
    /**
     *
     */
    String custom1;
    /**
     *
     */
    String custom2;
    /**
     *
     */
    String custom3;
    /**
     * 商品code
     */
    String goodsCode;
    /***
     * 商品名称
     */
    String goodsName;
    /**
     *
     */
    String outerGoodsCode;
    /**
     * 单位
     */
    String  packUnit;
    /**
     * 价格
     */
    Double  price;

    String remark;
    /**
     * 商品编码
     */
    String sku;
    /**
     * 单位
     */
    String unit;
    /**
     *
     */
    int volume;
    /**
     *
     */
    int weight;
}
