/*
 * 项目名称:platform-plus
 * 类名称:RequestingResearchController.java
 * 包名称:com.platform.modules.requesting.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 14:01:57        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.requesting;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.register.service.RegisterUserService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.requesting.entity.RequestingResearchEntity;
import com.platform.modules.requesting.service.RequestingResearchService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 需求调研Controller
 *
 * @author Mr.panbb
 * @date 2020-03-18 14:01:57
 */
@RestController
@RequestMapping("requesting/research")
public class RequestingResearchController extends AbstractController {
    @Autowired
    private RequestingResearchService requestingResearchService;
    @Autowired
    private RegisterUserService registerUserService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("requesting:research:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<RequestingResearchEntity> list = requestingResearchService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询需求调研
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("requesting:research:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = requestingResearchService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("requesting:research:info")
    public RestResponse info(@PathVariable("id") String id) {
        RequestingResearchEntity requestingResearch = requestingResearchService.getById(id);

        return RestResponse.success().put("research", requestingResearch);
    }

    /**
     * 新增需求调研
     *
     * @param requestingResearch requestingResearch
     * @return RestResponse
     */
    @SysLog("新增需求调研")
    @RequestMapping("/save")
    @RequiresPermissions("requesting:research:save")
    public RestResponse save(@RequestBody RequestingResearchEntity requestingResearch) {
        requestingResearch.setCreateBy(getUserId());
        // 保存需求调研信息
        requestingResearchService.add(requestingResearch);
        // 转化成注册信息
        registerUserService.addFromRequesting(requestingResearch);
        return RestResponse.success();
    }

    /**
     * 修改需求调研
     *
     * @param requestingResearch requestingResearch
     * @return RestResponse
     */
    @SysLog("修改需求调研")
    @RequestMapping("/update")
    @RequiresPermissions("requesting:research:update")
    public RestResponse update(@RequestBody RequestingResearchEntity requestingResearch) {
        requestingResearch.setUpdateBy(getUserId());
        requestingResearchService.update(requestingResearch);

        return RestResponse.success();
    }

    /**
     * 根据主键删除需求调研
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除需求调研")
    @RequestMapping("/delete")
    @RequiresPermissions("requesting:research:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        requestingResearchService.deleteBatch(ids);

        return RestResponse.success();
    }
}
