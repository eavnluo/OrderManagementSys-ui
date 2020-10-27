/*
 * 项目名称:platform-plus
 * 类名称:OrderTrackInfoController.java
 * 包名称:com.platform.modules.order.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-11-05 08:52:24        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.order.entity.OrderTrackInfoEntity;
import com.platform.modules.order.service.OrderTrackInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zlm
 * @date 2019-11-05 08:52:24
 */
@RestController
@RequestMapping("order/trackinfo")
public class OrderTrackInfoController extends AbstractController {
    @Autowired
    private OrderTrackInfoService orderTrackInfoService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("order:trackinfo:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<OrderTrackInfoEntity> list = orderTrackInfoService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("order:trackinfo:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = orderTrackInfoService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("order:trackinfo:info")
    public RestResponse info(@PathVariable("id") String id) {
        OrderTrackInfoEntity orderTrackInfo = orderTrackInfoService.getById(id);

        return RestResponse.success().put("trackinfo", orderTrackInfo);
    }

    /**
     * 新增
     *
     * @param orderTrackInfo orderTrackInfo
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("order:trackinfo:save")
    public RestResponse save(@RequestBody OrderTrackInfoEntity orderTrackInfo) {

        orderTrackInfoService.add(orderTrackInfo);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param orderTrackInfo orderTrackInfo
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("order:trackinfo:update")
    public RestResponse update(@RequestBody OrderTrackInfoEntity orderTrackInfo) {

        orderTrackInfoService.update(orderTrackInfo);

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
    @RequiresPermissions("order:trackinfo:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        orderTrackInfoService.deleteBatch(ids);

        return RestResponse.success();
    }
}
