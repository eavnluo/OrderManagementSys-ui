/*
 * 项目名称:platform-plus
 * 类名称:OrderReceiveNoticeEntity.java
 * 包名称:com.platform.modules.order.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:31        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单消息接收表 实体
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:31
 */
@Data
@TableName("ORDER_RECEIVE_NOTICE")
public class OrderReceiveNoticeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 删除标识
     */
   @TableLogic
   @TableField(value ="DEL_FLAG", fill = FieldFill.INSERT)
   private String delFlag;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    @TableField(value="created_time", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    @TableField(value="updated_time", fill = FieldFill.UPDATE)
    private Date updatedDate;
    /**
     * 合作伙伴
     */
    private String partner;
    /**
     * 签名方式
     */
    private String signType;
    /**
     * 签名内容
     */
    private String signContent;
    /**
     * 通知类型
     */
    private String notifyType;
    /**
     * 通知时间
     */
    private Date notifyDate;
    /**
     * 内容
     */
    private String content;
    /**
     * 主键
     */
    @TableId
    private String notifyId;
    /**
     * 处理状态
     */
    private String dealFlag;
    /**
     * 处理描述
     */
    private String dealDesc;
    /**
     * 处理次数
     */
    private Integer dealCnt;

  public OrderReceiveNoticeEntity(String  content) {
    this.content =content;
    this.notifyDate=new Date();
  }
}
