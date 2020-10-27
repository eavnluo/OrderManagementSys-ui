/*
 * 项目名称:platform-plus
 * 类名称:PriceItemController.java
 * 包名称:com.platform.modules.price.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 09:48:39        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.price;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.price.entity.PriceItemEntity;
import com.platform.modules.price.service.PriceItemService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 价格项，基础数据Controller
 *
 * @author Mr.panbb
 * @date 2020-03-24 09:48:39
 */
@RestController
@RequestMapping("price/item")
public class PriceItemController extends AbstractController {
    @Autowired
    private PriceItemService priceItemService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("price:item:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<PriceItemEntity> list = priceItemService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询价格项，基础数据
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("price:item:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = priceItemService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("price:item:info")
    public RestResponse info(@PathVariable("id") String id) {
        PriceItemEntity priceItem = priceItemService.getById(id);

        return RestResponse.success().put("item", priceItem);
    }

    /**
     * 新增价格项，基础数据
     *
     * @param priceItem priceItem
     * @return RestResponse
     */
    @SysLog("新增价格项，基础数据")
    @RequestMapping("/save")
    @RequiresPermissions("price:item:save")
    public RestResponse save(@RequestBody PriceItemEntity priceItem) {
        priceItem.setCreateBy(getUserId());
        priceItem.setCreateTime(new Date());
        priceItemService.add(priceItem);

        return RestResponse.success();
    }

    /**
     * 修改价格项，基础数据
     *
     * @param priceItem priceItem
     * @return RestResponse
     */
    @SysLog("修改价格项，基础数据")
    @RequestMapping("/update")
    @RequiresPermissions("price:item:update")
    public RestResponse update(@RequestBody PriceItemEntity priceItem) {
        priceItem.setUpdateBy(getUserId());
        priceItem.setUpdateTime(new Date());
        priceItemService.update(priceItem);

        return RestResponse.success();
    }

    /**
     * 根据主键删除价格项，基础数据
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除价格项，基础数据")
    @RequestMapping("/delete")
    @RequiresPermissions("price:item:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        priceItemService.deleteBatch(ids);

        return RestResponse.success();
    }
}
