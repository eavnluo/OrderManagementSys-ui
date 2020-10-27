/*
 * 项目名称:platform-plus
 * 类名称:StoreInventoryGoodsController.java
 * 包名称:com.platform.modules.store.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-31 10:34:30        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.wms.entity.StoreInventoryGoodsEntity;
import com.platform.modules.wms.service.StoreInventoryGoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zlm
 * @date 2020-03-31 10:34:30
 */
@RestController
@RequestMapping("store/inventorygoods")
public class StoreInventoryGoodsController extends AbstractController {
    @Autowired
    private StoreInventoryGoodsService storeInventoryGoodsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("store:inventorygoods:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<StoreInventoryGoodsEntity> list = storeInventoryGoodsService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("store:inventorygoods:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = storeInventoryGoodsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("store:inventorygoods:info")
    public RestResponse info(@PathVariable("id") String id) {
        StoreInventoryGoodsEntity storeInventoryGoods = storeInventoryGoodsService.getById(id);

        return RestResponse.success().put("inventorygoods", storeInventoryGoods);
    }

    /**
     * 新增
     *
     * @param storeInventoryGoods storeInventoryGoods
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("store:inventorygoods:save")
    public RestResponse save(@RequestBody StoreInventoryGoodsEntity storeInventoryGoods) {

        storeInventoryGoodsService.add(storeInventoryGoods);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param storeInventoryGoods storeInventoryGoods
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("store:inventorygoods:update")
    public RestResponse update(@RequestBody StoreInventoryGoodsEntity storeInventoryGoods) {

        storeInventoryGoodsService.update(storeInventoryGoods);

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
    @RequiresPermissions("store:inventorygoods:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        storeInventoryGoodsService.deleteBatch(ids);

        return RestResponse.success();
    }
}
