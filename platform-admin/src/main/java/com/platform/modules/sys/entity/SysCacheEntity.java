/*
 * 项目名称:platform-plus
 * 类名称:SysCacheEntity.java
 * 包名称:com.platform.modules.sys.entity
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2019/1/28 17:12    tp      初版完成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.sys.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * redis缓存信息
 *
 * @author tp
 */
@Data
public class SysCacheEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    private String cacheKey;
    /**
     * value
     */
    private String value;
    /**
     * 剩余过期时间，单位秒
     */
    private Long seconds;
}
