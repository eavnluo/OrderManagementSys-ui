/*
 * 项目名称:platform-plus
 * 类名称:OrderReceiveNoticeController.java
 * 包名称:com.platform.modules.order.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:31        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.order.entity.OrderReceiveNoticeEntity;
import com.platform.modules.order.service.OrderReceiveNoticeService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单消息接收表 Controller
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:31
 */
@RestController
@RequestMapping("order/receivenotice")
public class OrderReceiveNoticeController extends AbstractController {
    @Autowired
    private OrderReceiveNoticeService orderReceiveNoticeService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("order:receivenotice:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<OrderReceiveNoticeEntity> list = orderReceiveNoticeService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询订单消息接收表 
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("order:receivenotice:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = orderReceiveNoticeService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param delFlag 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{delFlag}")
    @RequiresPermissions("order:receivenotice:info")
    public RestResponse info(@PathVariable("delFlag") String delFlag) {
        OrderReceiveNoticeEntity orderReceiveNotice = orderReceiveNoticeService.getById(delFlag);

        return RestResponse.success().put("receivenotice", orderReceiveNotice);
    }

    /**
     * 新增订单消息接收表 
     *
     * @param orderReceiveNotice orderReceiveNotice
     * @return RestResponse
     */
    @SysLog("新增订单消息接收表 ")
    @RequestMapping("/save")
    @RequiresPermissions("order:receivenotice:save")
    public RestResponse save(@RequestBody OrderReceiveNoticeEntity orderReceiveNotice) {

        orderReceiveNoticeService.add(orderReceiveNotice);

        return RestResponse.success();
    }

    /**
     * 修改订单消息接收表 
     *
     * @param orderReceiveNotice orderReceiveNotice
     * @return RestResponse
     */
    @SysLog("修改订单消息接收表 ")
    @RequestMapping("/update")
    @RequiresPermissions("order:receivenotice:update")
    public RestResponse update(@RequestBody OrderReceiveNoticeEntity orderReceiveNotice) {

        orderReceiveNoticeService.update(orderReceiveNotice);

        return RestResponse.success();
    }

    /**
     * 根据主键删除订单消息接收表 
     *
     * @param delFlags delFlags
     * @return RestResponse
     */
    @SysLog("删除订单消息接收表 ")
    @RequestMapping("/delete")
    @RequiresPermissions("order:receivenotice:delete")
    public RestResponse delete(@RequestBody String[] delFlags) {
        orderReceiveNoticeService.deleteBatch(delFlags);

        return RestResponse.success();
    }
}
