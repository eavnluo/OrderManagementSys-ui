/*
 * 项目名称:platform-plus
 * 类名称:OrderStatusFlowFileEntity.java
 * 包名称:com.platform.modules.order.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-07 11:23:38        zlm     初版做成
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
 * @author zlm
 * @date 2020-03-07 11:23:38
 */
@Data
@TableName("ORDER_STATUS_FLOW_FILE")
public class OrderStatusFlowFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 订单状态ID
	 */
	private String orderStatusFlowId;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件路径
	 */
	private String fileUrl;
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

	public OrderStatusFlowFileEntity(String orderStatusFlowId, String fileUrl, String userId) {
		this.orderStatusFlowId = orderStatusFlowId;
		this.fileUrl = fileUrl;
		this.createdBy = userId;
		this.updatedBy = userId;
		this.createdTime = new Date();
		this.updatedTime = new Date();
		this.delFlag = "0";
	}
}
