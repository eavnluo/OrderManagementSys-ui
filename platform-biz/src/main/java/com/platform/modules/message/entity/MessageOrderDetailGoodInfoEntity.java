/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderDetailGoodInfoEntity.java
 * 包名称:com.platform.modules.message.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 14:53:26        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单详情表  实体
 *
 * @author 凯晟admin
 * @date 2019-09-10 14:53:26
 */
@Data
@TableName("MESSAGE_ORDER_DETAIL_GOOD_INFO")
public class MessageOrderDetailGoodInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 删除标识
     */
    @TableId
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
    private String rowId;
    /**
     * 品名
     */
    private String goodName;
    /**
     * 商品编码
     */
    private String goodCode;
    /**
     * 型号
     */
    private String goodSpec;
    /**
     * 条码号
     */
    private String barCode;
    /**
     * 数量
     */
    private String goodsNum;
    /**
     * 单价
     */
    private String goodPrice;
    /**
     * 币种
     */
    private String currencyTpye;
    /**
     * 总额
     */
    private String amount;
}
