/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderReceiveNoticeEntity.java
 * 包名称:com.platform.modules.message.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 15:25:47        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单消息接收表  实体
 *
 * @author jk
 * @date 2019-09-10 15:25:47
 */
@Data
@TableName("MESSAGE_ORDER_RECEIVE_NOTICE")
public class MessageOrderReceiveNoticeEntity implements Serializable {
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
}
