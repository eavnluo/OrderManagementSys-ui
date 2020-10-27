/*
 * 项目名称:platform-plus
 * 类名称:BaseAreaEntity.java
 * 包名称:com.platform.modules.base.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-30 09:33:03        凯晟admin     初版做成
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
 * @author 凯晟admin
 * @date 2019-09-30 09:33:03
 */
@Data
@TableName("BASE_AREA")
public class BaseAreaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    private String id;
    /**
     * 
     */
    private String name;
    /**
     * 
     */
    private String parentId;
    /**
     * 
     */
    private String shortName;
    /**
     * 
     */
    private Integer levelType;
    /**
     * 
     */
    private String cityCode;
    /**
     * 
     */
    private String zipCode;
    /**
     * 
     */
    private String mergerName;
    /**
     * 
     */
    private String ing;
    /**
     * 
     */
    private String lat;
    /**
     * 
     */
    private String pinYin;
}
