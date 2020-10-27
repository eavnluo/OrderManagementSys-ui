/*
 * 项目名称:platform-plus
 * 类名称:BaseCarInfoController.java
 * 包名称:com.platform.modules.base.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-29 09:32:01        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.base.entity.BaseCarInfoEntity;
import com.platform.modules.base.service.BaseCarInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zlm
 * @date 2019-09-29 09:32:01
 */
@RestController
@RequestMapping("base/carinfo")
public class BaseCarInfoController extends AbstractController {
    @Autowired
    private BaseCarInfoService baseCarInfoService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<BaseCarInfoEntity> list = baseCarInfoService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 获取分页列表
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/findPage")
    public RestResponse findPage(@RequestParam Map<String, Object> params) {
        Page page = baseCarInfoService.findPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = baseCarInfoService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:carinfo:info")
    public RestResponse info(@PathVariable("id") String id) {
        BaseCarInfoEntity baseCarInfo = baseCarInfoService.getById(id);

        return RestResponse.success().put("carinfo", baseCarInfo);
    }

    /**
     * 新增
     *
     * @param baseCarInfo baseCarInfo
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("base:carinfo:save")
    public RestResponse save(@RequestBody BaseCarInfoEntity baseCarInfo) {

        baseCarInfoService.add(baseCarInfo);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param baseCarInfo baseCarInfo
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("base:carinfo:update")
    public RestResponse update(@RequestBody BaseCarInfoEntity baseCarInfo) {

        baseCarInfoService.update(baseCarInfo);

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
    //@RequiresPermissions("base:carinfo:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        baseCarInfoService.deleteBatch(ids);

        return RestResponse.success();
    }
}
