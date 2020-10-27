/*
 * 项目名称:platform-plus
 * 类名称:BaseAreaController.java
 * 包名称:com.platform.modules.base.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-30 09:33:03        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseAreaEntity;
import com.platform.modules.base.service.BaseAreaService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 凯晟admin
 * @date 2019-09-30 09:33:03
 */
@RestController
@RequestMapping("base/area")
public class BaseAreaController extends AbstractController {
	@Autowired
	private BaseAreaService baseAreaService;

	/**
	 * 查看所有列表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@RequestMapping("/queryAll")
	@RequiresPermissions("base:area:list")
	public RestResponse queryAll(@RequestParam Map<String, Object> params) {
		List<BaseAreaEntity> list = baseAreaService.queryAll(params);

		return RestResponse.success().put("list", list);
	}

	/**
	 * 分页查询
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@GetMapping("/list")
	@RequiresPermissions("base:area:list")
	public RestResponse list(@RequestParam Map<String, Object> params) {
		Page page = baseAreaService.queryPage(params);

		return RestResponse.success().put("page", page);
	}

	/**
	 * 根据主键查询详情
	 *
	 * @param id 主键
	 * @return RestResponse
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("base:area:info")
	public RestResponse info(@PathVariable("id") String id) {
		BaseAreaEntity baseArea = baseAreaService.getById(id);

		return RestResponse.success().put("area", baseArea);
	}

	/**
	 * 新增
	 *
	 * @param baseArea baseArea
	 * @return RestResponse
	 */
	@SysLog("新增")
	@RequestMapping("/save")
	@RequiresPermissions("base:area:save")
	public RestResponse save(@RequestBody BaseAreaEntity baseArea) {

		baseAreaService.add(baseArea);

		return RestResponse.success();
	}

	/**
	 * 修改
	 *
	 * @param baseArea baseArea
	 * @return RestResponse
	 */
	@SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("base:area:update")
	public RestResponse update(@RequestBody BaseAreaEntity baseArea) {

		baseAreaService.update(baseArea);

		return RestResponse.success();
	}

	/**
	 * 根据主键删除
	 *
	 * @param ids ids
	 * @return RestResponse
	 */
	@SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("base:area:delete")
	public RestResponse delete(@RequestBody String[] ids) {
		baseAreaService.deleteBatch(ids);

		return RestResponse.success();
	}

	/**
	 * 获取列表
	 * @return
	 */
	@RequestMapping("/getListByParentId")
	public RestResponse getListByParentId(String parentId) {
		List<BaseAreaEntity> list = baseAreaService.getListByParentId(parentId);
		return RestResponse.success().put("list", list);
	}
}
