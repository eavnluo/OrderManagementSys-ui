/*
 * 项目名称:platform-plus
 * 类名称:BaseLinkmanInfoController.java
 * 包名称:com.platform.modules.base.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-26 11:30:23        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.base.entity.BaseLinkmanInfoEntity;
import com.platform.modules.base.service.BaseLinkmanInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zlm
 * @date 2019-09-26 11:30:23
 */
@RestController
@RequestMapping("base/linkmaninfo")
public class BaseLinkmanInfoController extends AbstractController {
    @Autowired
    private BaseLinkmanInfoService baseLinkmanInfoService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("base:linkmaninfo:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<BaseLinkmanInfoEntity> list = baseLinkmanInfoService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("base:linkmaninfo:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = baseLinkmanInfoService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:linkmaninfo:info")
    public RestResponse info(@PathVariable("id") String id) {
        BaseLinkmanInfoEntity baseLinkmanInfo = baseLinkmanInfoService.getById(id);

        return RestResponse.success().put("linkmaninfo", baseLinkmanInfo);
    }

    /**
     * 新增
     *
     * @param baseLinkmanInfo baseLinkmanInfo
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("base:linkmaninfo:save")
    public RestResponse save(@RequestBody BaseLinkmanInfoEntity baseLinkmanInfo) {

        baseLinkmanInfoService.add(baseLinkmanInfo);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param baseLinkmanInfo baseLinkmanInfo
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("base:linkmaninfo:update")
    public RestResponse update(@RequestBody BaseLinkmanInfoEntity baseLinkmanInfo) {

        baseLinkmanInfoService.update(baseLinkmanInfo);

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
    //@RequiresPermissions("base:linkmaninfo:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        baseLinkmanInfoService.deleteBatch(ids);

        return RestResponse.success();
    }
}
