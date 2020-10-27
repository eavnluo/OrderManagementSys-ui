/*
 * 项目名称:platform-plus
 * 类名称:OrderGoodUniqueCodeStandardEntity.java
 * 包名称:com.platform.modules.order.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-12 13:22:59        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 商品唯一编码标准表 实体
 *
 * @author jk
 * @date 2019-09-12 13:22:59
 */
@Data
@TableName("ORDER_GOOD_UNIQUE_CODE_STANDARD")
public class OrderGoodUniqueCodeStandardEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 内部单号
     */
    private String orderItem;
    /**
     * 产品名称
     */
    private String goodName;
    /**
     * 产品编码
     */
    private String goodCode;
    /**
     * 唯一标识码
     */
    private String uniqueCode;
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
     * 无参构造
     */
    public OrderGoodUniqueCodeStandardEntity() {
    }

    /**
     * 有参构造（商品唯一编码）
     */
    public OrderGoodUniqueCodeStandardEntity(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderGoodUniqueCodeStandardEntity)) {
            return false;
        }
        OrderGoodUniqueCodeStandardEntity that = (OrderGoodUniqueCodeStandardEntity) o;
        return Objects.equals(uniqueCode, that.uniqueCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueCode);
    }


    /**
     * 添加商品相关属性
     * @return
     */
    public OrderGoodUniqueCodeScanEntity addGoodInfo(OrderGoodUniqueCodeScanEntity scan) {
        scan.setGoodName(this.goodName);
        scan.setGoodCode(this.goodCode);
        return scan;
    }
}
