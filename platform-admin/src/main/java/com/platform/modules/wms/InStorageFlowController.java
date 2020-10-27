/*
 * 项目名称:platform-plus
 * 类名称:InStorageFlowController.java
 * 包名称:com.platform.modules.in.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 14:28:34        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.wms.entity.InStorageFlowEntity;
import com.platform.modules.wms.service.InStorageFlowService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 入库订单数据 Controller
 *
 * @author jk
 * @date 2019-09-04 14:28:34
 */
@RestController
@RequestMapping("in/storageflow")
public class InStorageFlowController extends AbstractController {
    @Autowired
    private InStorageFlowService inStorageFlowService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("in:storageflow:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<InStorageFlowEntity> list = inStorageFlowService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询入库订单数据
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("in:storageflow:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = inStorageFlowService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param bizCode 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{bizCode}")
    @RequiresPermissions("in:storageflow:info")
    public RestResponse info(@PathVariable("bizCode") String bizCode) {
        InStorageFlowEntity inStorageFlow = inStorageFlowService.getById(bizCode);

        return RestResponse.success().put("storageflow", inStorageFlow);
    }

    /**
     * 新增入库订单数据
     *
     * @param inStorageFlow inStorageFlow
     * @return RestResponse
     */
    @SysLog("新增入库订单数据 ")
    @RequestMapping("/save")
    @RequiresPermissions("in:storageflow:save")
    public RestResponse save(@RequestBody InStorageFlowEntity inStorageFlow) {
        //获取当前用户
        SysUserEntity currentUser = getUser();
        inStorageFlow.setCreatedBy(currentUser.getUserId());
        inStorageFlow.setUpdatedBy(currentUser.getUserId());
        inStorageFlowService.add(inStorageFlow);

        return RestResponse.success();
    }

    /**
     * 修改入库订单数据
     *
     * @param inStorageFlow inStorageFlow
     * @return RestResponse
     */
    @SysLog("修改入库订单数据 ")
    @RequestMapping("/update")
    @RequiresPermissions("in:storageflow:update")
    public RestResponse update(@RequestBody InStorageFlowEntity inStorageFlow) {
        inStorageFlow.setUpdatedBy(getUser().getUserId());
        inStorageFlowService.update(inStorageFlow);

        return RestResponse.success();
    }

    /**
     * 根据主键删除入库订单数据
     *
     * @param bizCodes bizCodes
     * @return RestResponse
     */
    @SysLog("删除入库订单数据 ")
    @RequestMapping("/delete")
    @RequiresPermissions("in:storageflow:delete")
    public RestResponse delete(@RequestBody String[] bizCodes) {
        inStorageFlowService.deleteBatch(bizCodes);

        return RestResponse.success();
    }
}
