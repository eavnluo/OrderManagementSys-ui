/*
 * 项目名称:platform-plus
 * 类名称:OrderInterfaceLogEntity.java
 * 包名称:com.platform.modules.order.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-10-23 10:34:24        zlm     初版做成
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
 * @date 2019-10-23 10:34:24
 */
@Data
@TableName("ORDER_INTERFACE_LOG")
public class OrderInterfaceLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private String id;
    /**
     * 下发到哪,0-仓库；1-物联网平台
     */
    private String type;
    /**
     * 工作单号
     */
    private String orderItem;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求参数
     */
    private String params;
    /**
     * 响应结果
     */
    private String result;
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
