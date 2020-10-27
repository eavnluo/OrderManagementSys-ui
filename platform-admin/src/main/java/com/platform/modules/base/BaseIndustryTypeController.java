/*
 * 项目名称:platform-plus
 * 类名称:BaseIndustryTypeController.java
 * 包名称:com.platform.modules.base.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 10:01:36        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.base.entity.BaseIndustryTypeEntity;
import com.platform.modules.base.service.BaseIndustryTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zlm
 * @date 2020-03-18 10:01:36
 */
@RestController
@RequestMapping("base/industrytype")
public class BaseIndustryTypeController extends AbstractController {
    @Autowired
    private BaseIndustryTypeService baseIndustryTypeService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<BaseIndustryTypeEntity> list = baseIndustryTypeService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("base:industrytype:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = baseIndustryTypeService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:industrytype:info")
    public RestResponse info(@PathVariable("id") Integer id) {
        BaseIndustryTypeEntity baseIndustryType = baseIndustryTypeService.getById(id);

        return RestResponse.success().put("industrytype", baseIndustryType);
    }

    /**
     * 新增
     *
     * @param baseIndustryType baseIndustryType
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("base:industrytype:save")
    public RestResponse save(@RequestBody BaseIndustryTypeEntity baseIndustryType) {

        baseIndustryTypeService.add(baseIndustryType);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param baseIndustryType baseIndustryType
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("base:industrytype:update")
    public RestResponse update(@RequestBody BaseIndustryTypeEntity baseIndustryType) {

        baseIndustryTypeService.update(baseIndustryType);

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
    @RequiresPermissions("base:industrytype:delete")
    public RestResponse delete(@RequestBody Integer[] ids) {
        baseIndustryTypeService.deleteBatch(ids);

        return RestResponse.success();
    }
}
