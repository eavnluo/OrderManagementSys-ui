package com.platform.modules.order.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @author Created by Tp-wangwh on 2019-9-19.
 */
@Data
public class OrderExportDto {
    /***
     * 序号
     */
    private String id;
    @Excel(name="订单号", orderNum = "1")
    private String orderNo;
    /**
     * 工作单号
     */
    @Excel(name="工作单号", orderNum = "2")
    private String orderItem;
    /***
     * 订单状态
     */
    private String statusName;
    /**
     * 发货城市
     */
    private String supplyCity;

    /***
     *供应商名称
     */
    private String supplyName;

    /**
     * 收货人地址
     */
    private String receiveRegion;

    /**
     * 收货联系人
     */
    private String receiver;
    /***
     * 运输方式
      */
    private String transType;

    /**
     * 运输产品
     */
    private String goodName;
	/**
	 * 始发地
	 */
    private String startPoint;
	/**
	 * 目的地
	 */
	private String endPoint;
	/**
	 * 发货人
	 */
    private String sendGoodsPeople;
	/**
	 * 收货人
	 */
	private String receiveGoodsPeople;

}
