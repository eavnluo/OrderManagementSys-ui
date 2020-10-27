/*
 * 项目名称:platform-plus
 * 类名称:SysConfigEntity.java
 * 包名称:com.platform.modules.sys.entity
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    tp      初版完成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 系统配置信息
 *
 * @author tp
 */
@Data
@TableName("SYS_CONFIG")
public class SysConfigEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String id;
    @NotBlank(message = "参数名不能为空")
    private String paramKey;
    @NotBlank(message = "参数值不能为空")
    private String paramValue;
    private String remark;
}
