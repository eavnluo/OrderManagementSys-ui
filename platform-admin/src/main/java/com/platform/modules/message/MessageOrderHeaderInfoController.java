/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderHeaderInfoController.java
 * 包名称:com.platform.modules.message.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 15:13:17        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.message.entity.MessageOrderHeaderInfoEntity;
import com.platform.modules.message.service.MessageOrderHeaderInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单头表  Controller
 *
 * @author jk
 * @date 2019-09-10 15:13:17
 */
@RestController
@RequestMapping("message/orderheaderinfo")
public class MessageOrderHeaderInfoController extends AbstractController {
    @Autowired
    private MessageOrderHeaderInfoService messageOrderHeaderInfoService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("message:orderheaderinfo:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<MessageOrderHeaderInfoEntity> list = messageOrderHeaderInfoService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询订单头表  
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("message:orderheaderinfo:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = messageOrderHeaderInfoService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("message:orderheaderinfo:info")
    public RestResponse info(@PathVariable("id") String id) {
        MessageOrderHeaderInfoEntity messageOrderHeaderInfo = messageOrderHeaderInfoService.getById(id);

        return RestResponse.success().put("orderheaderinfo", messageOrderHeaderInfo);
    }

    /**
     * 新增订单头表  
     *
     * @param messageOrderHeaderInfo messageOrderHeaderInfo
     * @return RestResponse
     */
    @SysLog("新增订单头表  ")
    @RequestMapping("/save")
    @RequiresPermissions("message:orderheaderinfo:save")
    public RestResponse save(@RequestBody MessageOrderHeaderInfoEntity messageOrderHeaderInfo) {

        messageOrderHeaderInfoService.add(messageOrderHeaderInfo);

        return RestResponse.success();
    }

    /**
     * 修改订单头表  
     *
     * @param messageOrderHeaderInfo messageOrderHeaderInfo
     * @return RestResponse
     */
    @SysLog("修改订单头表  ")
    @RequestMapping("/update")
    @RequiresPermissions("message:orderheaderinfo:update")
    public RestResponse update(@RequestBody MessageOrderHeaderInfoEntity messageOrderHeaderInfo) {

        messageOrderHeaderInfoService.update(messageOrderHeaderInfo);

        return RestResponse.success();
    }

    /**
     * 根据主键删除订单头表  
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除订单头表  ")
    @RequestMapping("/delete")
    @RequiresPermissions("message:orderheaderinfo:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        messageOrderHeaderInfoService.deleteBatch(ids);

        return RestResponse.success();
    }
}
