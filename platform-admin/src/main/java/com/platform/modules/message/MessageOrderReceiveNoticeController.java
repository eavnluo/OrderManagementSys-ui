/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderReceiveNoticeController.java
 * 包名称:com.platform.modules.message.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 15:25:47        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.message.entity.MessageOrderReceiveNoticeEntity;
import com.platform.modules.message.service.MessageOrderReceiveNoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单消息接收表  Controller
 *
 * @author jk
 * @date 2019-09-10 15:25:47
 */
@RestController
@RequestMapping("message/orderreceivenotice")
public class MessageOrderReceiveNoticeController extends AbstractController {
    @Autowired
    private MessageOrderReceiveNoticeService messageOrderReceiveNoticeService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("message:orderreceivenotice:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<MessageOrderReceiveNoticeEntity> list = messageOrderReceiveNoticeService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询订单消息接收表  
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("message:orderreceivenotice:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = messageOrderReceiveNoticeService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param delFlag 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{delFlag}")
    @RequiresPermissions("message:orderreceivenotice:info")
    public RestResponse info(@PathVariable("delFlag") String delFlag) {
        MessageOrderReceiveNoticeEntity messageOrderReceiveNotice = messageOrderReceiveNoticeService.getById(delFlag);

        return RestResponse.success().put("orderreceivenotice", messageOrderReceiveNotice);
    }

    /**
     * 新增订单消息接收表  
     *
     * @param messageOrderReceiveNotice messageOrderReceiveNotice
     * @return RestResponse
     */
    @SysLog("新增订单消息接收表  ")
    @RequestMapping("/save")
    @RequiresPermissions("message:orderreceivenotice:save")
    public RestResponse save(@RequestBody MessageOrderReceiveNoticeEntity messageOrderReceiveNotice) {

        messageOrderReceiveNoticeService.add(messageOrderReceiveNotice);

        return RestResponse.success();
    }

    /**
     * 修改订单消息接收表  
     *
     * @param messageOrderReceiveNotice messageOrderReceiveNotice
     * @return RestResponse
     */
    @SysLog("修改订单消息接收表  ")
    @RequestMapping("/update")
    @RequiresPermissions("message:orderreceivenotice:update")
    public RestResponse update(@RequestBody MessageOrderReceiveNoticeEntity messageOrderReceiveNotice) {

        messageOrderReceiveNoticeService.update(messageOrderReceiveNotice);

        return RestResponse.success();
    }

    /**
     * 根据主键删除订单消息接收表  
     *
     * @param delFlags delFlags
     * @return RestResponse
     */
    @SysLog("删除订单消息接收表  ")
    @RequestMapping("/delete")
    @RequiresPermissions("message:orderreceivenotice:delete")
    public RestResponse delete(@RequestBody String[] delFlags) {
        messageOrderReceiveNoticeService.deleteBatch(delFlags);

        return RestResponse.success();
    }
}
