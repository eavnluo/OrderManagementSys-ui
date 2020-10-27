/*
 * 项目名称:platform-plus
 * 类名称:OrderRelationshipController.java
 * 包名称:com.platform.modules.order.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-11-05 10:34:40        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.order.entity.OrderRelationshipEntity;
import com.platform.modules.order.service.OrderRelationshipService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 凯晟admin
 * @date 2019-11-05 10:34:40
 */
@RestController
@RequestMapping("order/relationship")
public class OrderRelationshipController extends AbstractController {
    @Autowired
    private OrderRelationshipService orderRelationshipService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("order:relationship:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<OrderRelationshipEntity> list = orderRelationshipService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("order:relationship:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = orderRelationshipService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("order:relationship:info")
    public RestResponse info(@PathVariable("id") String id) {
        OrderRelationshipEntity orderRelationship = orderRelationshipService.getById(id);

        return RestResponse.success().put("relationship", orderRelationship);
    }

    /**
     * 新增
     *
     * @param orderRelationship orderRelationship
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("order:relationship:save")
    public RestResponse save(@RequestBody OrderRelationshipEntity orderRelationship) {

        orderRelationshipService.add(orderRelationship);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param orderRelationship orderRelationship
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("order:relationship:update")
    public RestResponse update(@RequestBody OrderRelationshipEntity orderRelationship) {

        orderRelationshipService.update(orderRelationship);

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
    @RequiresPermissions("order:relationship:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        orderRelationshipService.deleteBatch(ids);

        return RestResponse.success();
    }
}
