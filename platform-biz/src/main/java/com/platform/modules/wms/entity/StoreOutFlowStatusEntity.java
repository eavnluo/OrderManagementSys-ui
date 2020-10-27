/*
 * 项目名称:platform-plus
 * 类名称:StoreOutFlowStatusEntity.java
 * 包名称:com.platform.modules.store.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-07 16:36:16        凯晟admin     初版做成
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
 *  出库订单状态流水表实体
 *
 * @author 凯晟admin
 * @date 2019-09-07 16:36:16
 */
@Data
@TableName("STORE_OUT_FLOW_STATUS")
public class StoreOutFlowStatusEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 删除标识
     */
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
     * 
     */
    @TableId
    private String id;
    /**
     * 客户订单编号
     */
    private String customerOrderNo;
    /**
     * 工作单号
     */
    private String orderItem;
    /**
     * 状态
     */
    private String statusCode;
    /**
     * 状态更新时间
     */
    private String statusName;
    /**
     * 状态更新时间
     */
    private String statusUpdate;
}
