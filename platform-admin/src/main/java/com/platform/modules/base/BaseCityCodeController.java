/*
 * 项目名称:platform-plus
 * 类名称:BaseCityCodeController.java
 * 包名称:com.platform.modules.base.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 11:04:39        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseCityCodeEntity;
import com.platform.modules.base.service.BaseCityCodeService;
import com.platform.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zlm
 * @date 2020-03-18 11:04:39
 */
@RestController
@RequestMapping("base/citycode")
public class BaseCityCodeController extends AbstractController {
	@Autowired
	private BaseCityCodeService baseCityCodeService;

	/**
	 * 查看所有列表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@RequestMapping("/queryAll")
	public RestResponse queryAll(@RequestParam Map<String, Object> params) {
		List<BaseCityCodeEntity> list = baseCityCodeService.queryAll(params);

		return RestResponse.success().put("list", list);
	}

	/**
	 * 分页查询
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@GetMapping("/list")
	public RestResponse list(@RequestParam Map<String, Object> params) {
		Page page = baseCityCodeService.queryPage(params);
		return RestResponse.success().put("page", page);
	}

	/**
	 * 根据主键查询详情
	 *
	 * @param fullName 主键
	 * @return RestResponse
	 */
	@RequestMapping("/info/{fullName}")
	public RestResponse info(@PathVariable("fullName") String fullName) {
		BaseCityCodeEntity baseCityCode = baseCityCodeService.getById(fullName);

		return RestResponse.success().put("citycode", baseCityCode);
	}

	/**
	 * 新增
	 *
	 * @param baseCityCode baseCityCode
	 * @return RestResponse
	 */
	@SysLog("新增")
	@RequestMapping("/save")
	public RestResponse save(@RequestBody BaseCityCodeEntity baseCityCode) {

		baseCityCodeService.add(baseCityCode);

		return RestResponse.success();
	}

	/**
	 * 修改
	 *
	 * @param baseCityCode baseCityCode
	 * @return RestResponse
	 */
	@SysLog("修改")
	@RequestMapping("/update")
	public RestResponse update(@RequestBody BaseCityCodeEntity baseCityCode) {

		baseCityCodeService.update(baseCityCode);

		return RestResponse.success();
	}

	/**
	 * 根据主键删除
	 *
	 * @param fullNames fullNames
	 * @return RestResponse
	 */
	@SysLog("删除")
	@RequestMapping("/delete")
	public RestResponse delete(@RequestBody String[] fullNames) {
		baseCityCodeService.deleteBatch(fullNames);

		return RestResponse.success();
	}
}
