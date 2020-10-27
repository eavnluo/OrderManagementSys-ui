/*
 * 项目名称:platform-plus
 * 类名称:OutStorageFlowController.java
 * 包名称:com.platform.modules.wms.out.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 16:19:19        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.wms.entity.OutStorageFlowEntity;
import com.platform.modules.wms.service.OutStorageFlowService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 出库流水计划信息 Controller
 *
 * @author stg
 * @date 2019-09-04 16:19:19
 */
@RestController
@RequestMapping("out/storageflow")
public class OutStorageFlowController extends AbstractController {
    @Autowired
    private OutStorageFlowService outStorageFlowService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("out:storageflow:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<OutStorageFlowEntity> list = outStorageFlowService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询出库流水计划信息 
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("out:storageflow:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = outStorageFlowService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param bizCode 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{bizCode}")
    @RequiresPermissions("out:storageflow:info")
    public RestResponse info(@PathVariable("bizCode") String bizCode) {
        OutStorageFlowEntity outStorageFlow = outStorageFlowService.getById(bizCode);

        return RestResponse.success().put("storageflow", outStorageFlow);
    }

    /**
     * 新增出库流水计划信息 
     *
     * @param outStorageFlow outStorageFlow
     * @return RestResponse
     */
    @SysLog("新增出库流水计划信息 ")
    @RequestMapping("/save")
    @RequiresPermissions("out:storageflow:save")
    public RestResponse save(@RequestBody OutStorageFlowEntity outStorageFlow) {

        Date now = new Date();
        //封装创建时间和修改时间
        outStorageFlow.setCreatedTime(now);
        outStorageFlow.setUpdatedTime(now);
        //获取当前用户
        SysUserEntity currentUser = getUser();
        outStorageFlow.setCreatedBy(currentUser.getUserId());
        outStorageFlow.setUpdatedBy(currentUser.getUserId());
        outStorageFlowService.add(outStorageFlow);

        return RestResponse.success();
    }

    /**
     * 修改出库流水计划信息 
     *
     * @param outStorageFlow outStorageFlow
     * @return RestResponse
     */
    @SysLog("修改出库流水计划信息 ")
    @RequestMapping("/update")
    @RequiresPermissions("out:storageflow:update")
    public RestResponse update(@RequestBody OutStorageFlowEntity outStorageFlow) {

        outStorageFlowService.update(outStorageFlow);

        return RestResponse.success();
    }

    /**
     * 根据主键删除出库流水计划信息 
     *
     * @param bizCodes bizCodes
     * @return RestResponse
     */
    @SysLog("删除出库流水计划信息 ")
    @RequestMapping("/delete")
    @RequiresPermissions("out:storageflow:delete")
    public RestResponse delete(@RequestBody String[] bizCodes) {
        outStorageFlowService.deleteBatch(bizCodes);

        return RestResponse.success();
    }
}
