/*
 * 项目名称:platform-plus
 * 类名称:BaseCityCodeEntity.java
 * 包名称:com.platform.modules.base.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 11:04:39        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 实体
 *
 * @author zlm
 * @date 2020-03-18 11:04:39
 */
@Data
@TableName("BASE_CITY_CODE")
public class BaseCityCodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	@TableId
	private String id;
	/**
	 * 全称
	 */
	@Excel(name = "全称", orderNum = "1")
	private String fullName;
	/**
	 * 城市三字代码
	 */
	@Excel(name = "代码", orderNum = "2")
	private String code;
	/**
	 * 省份
	 */
	@Excel(name = "省份", orderNum = "3")
	private String privince;
}
