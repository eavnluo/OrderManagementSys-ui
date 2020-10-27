/*
 * 项目名称:platform-plus
 * 类名称:OrderInterfaceLogController.java
 * 包名称:com.platform.modules.order.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-10-23 09:23:54        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.order.entity.OrderInterfaceLogEntity;
import com.platform.modules.order.service.OrderInterfaceLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 凯晟admin
 * @date 2019-10-23 09:23:54
 */
@RestController
@RequestMapping("order/interfacelog")
public class OrderInterfaceLogController extends AbstractController {
    @Autowired
    private OrderInterfaceLogService orderInterfaceLogService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("order:interfacelog:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<OrderInterfaceLogEntity> list = orderInterfaceLogService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("order:interfacelog:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = orderInterfaceLogService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("order:interfacelog:info")
    public RestResponse info(@PathVariable("id") String id) {
        OrderInterfaceLogEntity orderInterfaceLog = orderInterfaceLogService.getById(id);

        return RestResponse.success().put("interfacelog", orderInterfaceLog);
    }

    /**
     * 新增
     *
     * @param orderInterfaceLog orderInterfaceLog
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("order:interfacelog:save")
    public RestResponse save(@RequestBody OrderInterfaceLogEntity orderInterfaceLog) {

        orderInterfaceLogService.add(orderInterfaceLog);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param orderInterfaceLog orderInterfaceLog
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("order:interfacelog:update")
    public RestResponse update(@RequestBody OrderInterfaceLogEntity orderInterfaceLog) {

        orderInterfaceLogService.update(orderInterfaceLog);

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
    @RequiresPermissions("order:interfacelog:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        orderInterfaceLogService.deleteBatch(ids);

        return RestResponse.success();
    }
}
