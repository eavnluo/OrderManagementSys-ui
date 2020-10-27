/*
 * 项目名称:platform-plus
 * 类名称:FerriesReportController.java
 * 包名称:com.platform.modules.ferries.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-06 11:15:10        JK     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.ferries;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.ferries.entity.FerriesReportEntity;
import com.platform.modules.ferries.service.FerriesReportService;
import com.platform.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 船运报表Controller
 *
 * @author JK
 * @date 2019-09-06 11:15:10
 */
@RestController
@RequestMapping("ferries/report")
public class FerriesReportController extends AbstractController {
    @Autowired
    private FerriesReportService ferriesReportService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("ferries:report:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<FerriesReportEntity> list = ferriesReportService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询船运报表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("ferries:report:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = ferriesReportService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("ferries:report:info")
    public RestResponse info(@PathVariable("id") String id) {
        FerriesReportEntity ferriesReport = ferriesReportService.getById(id);

        return RestResponse.success().put("report", ferriesReport);
    }

    /**
     * 新增船运报表
     *
     * @param ferriesReport ferriesReport
     * @return RestResponse
     */
    @SysLog("新增船运报表")
    @RequestMapping("/save")
    @RequiresPermissions("ferries:report:save")
    public RestResponse save(@RequestBody FerriesReportEntity ferriesReport) {
        SysUserEntity currentUser = getUser();
        ferriesReport.setCreatedBy(currentUser.getUserId());
        ferriesReport.setUpdatedBy(currentUser.getUserId());
        ferriesReportService.add(ferriesReport);

        return RestResponse.success();
    }

    /**
     * 修改船运报表
     *
     * @param ferriesReport ferriesReport
     * @return RestResponse
     */
    @SysLog("修改船运报表")
    @RequestMapping("/update")
    @RequiresPermissions("ferries:report:update")
    public RestResponse update(@RequestBody FerriesReportEntity ferriesReport) {
        ferriesReport.setUpdatedBy(getUser().getUserId());
        ferriesReportService.update(ferriesReport);

        return RestResponse.success();
    }

    /**
     * 根据主键删除船运报表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除船运报表")
    @RequestMapping("/delete")
    @RequiresPermissions("ferries:report:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        ferriesReportService.deleteBatch(ids);

        return RestResponse.success();
    }
}
