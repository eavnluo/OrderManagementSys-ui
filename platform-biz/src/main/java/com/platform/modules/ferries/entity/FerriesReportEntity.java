/*
 * 项目名称:platform-plus
 * 类名称:FerriesReportEntity.java
 * 包名称:com.platform.modules.ferries.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-06 11:15:10        JK     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.ferries.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 船运报表实体
 *
 * @author JK
 * @date 2019-09-06 11:15:10
 */
@Data
@TableName("FERRIES_REPORT")
public class FerriesReportEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 填写日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fillingDate;
    /**
     * 本周箱量
     */
    private Integer weekCartonNumber;
    /**
     * 本月箱量
     */
    private Integer monthCartonNumber;
    /**
     * 本年箱量
     */
    private Integer yearCartonNumber;
    /**
     * 中欧
     */
    private Integer zhongOu;
    /**
     * 中俄
     */
    private Integer zhongE;
    /**
     * 中亚
     */
    private Integer zhongYa;
    /**
     * 国内
     */
    private Integer inland;
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
    @TableLogic
    private String delFlag;
}
