/*
 * 项目名称:platform-plus
 * 类名称:CooperationGuideController.java
 * 包名称:com.platform.modules.cooperation.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-19 08:42:55        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.cooperation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.cooperation.entity.CooperationGuideEntity;
import com.platform.modules.cooperation.service.CooperationGuideService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 合作指南Controller
 *
 * @author Mr.panbb
 * @date 2020-03-19 08:42:55
 */
@RestController
@RequestMapping("cooperation/guide")
public class CooperationGuideController extends AbstractController {
    @Autowired
    private CooperationGuideService cooperationGuideService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("cooperation:guide:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<CooperationGuideEntity> list = cooperationGuideService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询合作指南
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("cooperation:guide:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = cooperationGuideService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cooperation:guide:info")
    public RestResponse info(@PathVariable("id") String id) {
        CooperationGuideEntity cooperationGuide = cooperationGuideService.getById(id);
        return RestResponse.success().put("guide", cooperationGuide);
    }

    /**
     * 新增合作指南
     *
     * @param cooperationGuide cooperationGuide
     * @return RestResponse
     */
    @SysLog("新增合作指南")
    @RequestMapping("/save")
    @RequiresPermissions("cooperation:guide:save")
    public RestResponse save(@RequestBody CooperationGuideEntity cooperationGuide) {
        cooperationGuide.setCreateBy(getUserId());
        cooperationGuide.setCreateTime(new Date());
        cooperationGuideService.add(cooperationGuide);
        return RestResponse.success();
    }

    /**
     * 修改合作指南
     *
     * @param cooperationGuide cooperationGuide
     * @return RestResponse
     */
    @SysLog("修改合作指南")
    @RequestMapping("/update")
    @RequiresPermissions("cooperation:guide:update")
    public RestResponse update(@RequestBody CooperationGuideEntity cooperationGuide) {
        cooperationGuide.setUpdateBy(getUserId());
        cooperationGuide.setUpdateTime(new Date());
        cooperationGuideService.update(cooperationGuide);
        return RestResponse.success();
    }

    /**
     * 根据主键删除合作指南
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除合作指南")
    @RequestMapping("/delete")
    @RequiresPermissions("cooperation:guide:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        cooperationGuideService.deleteBatch(ids);

        return RestResponse.success();
    }
}
