/*
 * 项目名称:platform-plus
 * 类名称:OrderRelationshipEntity.java
 * 包名称:com.platform.modules.order.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-11-05 10:34:40        凯晟admin     初版做成
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
 * 实体
 *
 * @author 凯晟admin
 * @date 2019-11-05 10:34:40
 */
@Data
@TableName("ORDER_RELATIONSHIP")
public class OrderRelationshipEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public OrderRelationshipEntity(String orderItem, String wayBillNo) {
		this.orderItem = orderItem;
		this.wayBillNo = wayBillNo;
	}

	public OrderRelationshipEntity() {

	}

	/**
	 *
	 */
	@TableId
	private String id;
	/**
	 * 工作单号
	 */
	private String orderItem;
	/**
	 * 运输单号
	 */
	private String wayBillNo;
	/**
	 * 创建人
	 */
	private String createdBy;
	/**
	 * 创建时间
	 */
	private Date createdTime;
	/**
	 * 创建人
	 */
	private String updatedBy;
	/**
	 * 创建时间
	 */
	private Date updatedTime;
}
