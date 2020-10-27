/*
 * 项目名称:platform-plus
 * 类名称:OrderDetailGoodInfoController.java
 * 包名称:com.platform.modules.order.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:30        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import com.platform.modules.order.service.OrderDetailGoodsInfoService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单详情表 Controller
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:30
 */
@RestController
@RequestMapping("order/detailgoodinfo")
public class OrderDetailGoodsInfoController extends AbstractController {
    @Autowired
    private OrderDetailGoodsInfoService orderDetailGoodInfoService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("order:detailgoodinfo:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<OrderDetailGoodsInfoEntity> list = orderDetailGoodInfoService.queryAll(params);

        return RestResponse.success().put("list", list);
    }


    @RequestMapping("/listByOrderItem")
    public RestResponse listByOrderItem(@RequestParam("orderItem") String orderItem ) {
        List<OrderDetailGoodsInfoEntity> list = orderDetailGoodInfoService.listByOrderItem( orderItem);
        return RestResponse.success().put("list", list);
    }
    /**
     * 分页查询订单详情表 
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("order:detailgoodinfo:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = orderDetailGoodInfoService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param delFlag 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{delFlag}")
    @RequiresPermissions("order:detailgoodinfo:info")
    public RestResponse info(@PathVariable("delFlag") String delFlag) {
        OrderDetailGoodsInfoEntity orderDetailGoodInfo = orderDetailGoodInfoService.getById(delFlag);

        return RestResponse.success().put("detailgoodinfo", orderDetailGoodInfo);
    }

    /**
     * 新增订单详情表 
     *
     * @param orderDetailGoodInfo orderDetailGoodInfo
     * @return RestResponse
     */
    @SysLog("新增订单详情表 ")
    @RequestMapping("/save")
    @RequiresPermissions("order:detailgoodinfo:save")
    public RestResponse save(@RequestBody OrderDetailGoodsInfoEntity orderDetailGoodInfo) {

        orderDetailGoodInfoService.add(orderDetailGoodInfo);

        return RestResponse.success();
    }

    /**
     * 修改订单详情表 
     *
     * @param orderDetailGoodInfo orderDetailGoodInfo
     * @return RestResponse
     */
    @SysLog("修改订单详情表 ")
    @RequestMapping("/update")
    @RequiresPermissions("order:detailgoodinfo:update")
    public RestResponse update(@RequestBody OrderDetailGoodsInfoEntity orderDetailGoodInfo) {

        orderDetailGoodInfoService.update(orderDetailGoodInfo);

        return RestResponse.success();
    }

    /**
     * 根据主键删除订单详情表 
     *
     * @param delFlags delFlags
     * @return RestResponse
     */
    @SysLog("删除订单详情表 ")
    @RequestMapping("/delete")
    @RequiresPermissions("order:detailgoodinfo:delete")
    public RestResponse delete(@RequestBody String[] delFlags) {
        orderDetailGoodInfoService.deleteBatch(delFlags);

        return RestResponse.success();
    }

    /**
     * 根据内部订单编号获取商品数量
     * @param orderItem
     * @return
     */
    @RequestMapping("/queryGoodCount")
    public RestResponse queryGoodCount(@RequestParam String orderItem){
        String goodsNums = orderDetailGoodInfoService.queryGoodCount(orderItem);
        return RestResponse.success().put("goodsNum",goodsNums);
    }

}
