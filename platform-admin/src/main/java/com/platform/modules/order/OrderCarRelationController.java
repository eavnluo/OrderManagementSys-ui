/*
 * 项目名称:platform-plus
 * 类名称:OrderCarRelationController.java
 * 包名称:com.platform.modules.order.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 13:04:44        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.order.entity.OrderCarRelationEntity;
import com.platform.modules.order.service.OrderCarRelationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zlm
 * @date 2020-03-24 13:04:44
 */
@RestController
@RequestMapping("order/carrelation")
public class OrderCarRelationController extends AbstractController {
    @Autowired
    private OrderCarRelationService orderCarRelationService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("order:carrelation:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<OrderCarRelationEntity> list = orderCarRelationService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("order:carrelation:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = orderCarRelationService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("order:carrelation:info")
    public RestResponse info(@PathVariable("id") String id) {
        OrderCarRelationEntity orderCarRelation = orderCarRelationService.getById(id);

        return RestResponse.success().put("carrelation", orderCarRelation);
    }

    /**
     * 新增
     *
     * @param orderCarRelation orderCarRelation
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("order:carrelation:save")
    public RestResponse save(@RequestBody OrderCarRelationEntity orderCarRelation) {

        orderCarRelationService.add(orderCarRelation);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param orderCarRelation orderCarRelation
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("order:carrelation:update")
    public RestResponse update(@RequestBody OrderCarRelationEntity orderCarRelation) {

        orderCarRelationService.update(orderCarRelation);

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
    @RequiresPermissions("order:carrelation:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        orderCarRelationService.deleteBatch(ids);

        return RestResponse.success();
    }
}
