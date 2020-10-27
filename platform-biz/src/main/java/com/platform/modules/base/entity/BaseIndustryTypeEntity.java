/*
 * 项目名称:platform-plus
 * 类名称:BaseIndustryTypeEntity.java
 * 包名称:com.platform.modules.base.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 10:01:36        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author zlm
 * @date 2020-03-18 10:01:36
 */
@Data
@TableName("BASE_INDUSTRY_TYPE")
public class BaseIndustryTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Integer id;
    /**
     * 产业类型
     */
    private String industryType;
}
