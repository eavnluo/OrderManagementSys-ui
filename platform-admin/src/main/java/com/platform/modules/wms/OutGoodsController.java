/*
 * 项目名称:platform-plus
 * 类名称:OutGoodsController.java
 * 包名称:com.platform.modules.wms.out.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 10:22:41        stg     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.wms.entity.OutGoodsEntity;
import com.platform.modules.wms.service.OutGoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 出库商品列表信息 Controller
 *
 * @author stg
 * @date 2019-09-05 10:22:41
 */
@RestController
@RequestMapping("out/goods")
public class OutGoodsController extends AbstractController {
    @Autowired
    private OutGoodsService outGoodsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("out:goods:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<OutGoodsEntity> list = outGoodsService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询出库商品列表信息 
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("out:goods:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = outGoodsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("out:goods:info")
    public RestResponse info(@PathVariable("id") String id) {
        OutGoodsEntity outGoods = outGoodsService.getById(id);

        return RestResponse.success().put("goods", outGoods);
    }

    /**
     * 新增出库商品列表信息 
     *
     * @param outGoods outGoods
     * @return RestResponse
     */
    @SysLog("新增出库商品列表信息 ")
    @RequestMapping("/save")
    @RequiresPermissions("out:goods:save")
    public RestResponse save(@RequestBody OutGoodsEntity outGoods) {

        Date now = new Date();
        //封装创建时间和修改时间
        outGoods.setCreatedTime(now);
        outGoods.setUpdatedTime(now);
        //获取当前用户
        SysUserEntity currentUser = getUser();
        outGoods.setCreatedBy(currentUser.getUserId());
        outGoods.setUpdatedBy(currentUser.getUserId());

        outGoodsService.add(outGoods);

        return RestResponse.success();
    }

    /**
     * 修改出库商品列表信息 
     *
     * @param outGoods outGoods
     * @return RestResponse
     */
    @SysLog("修改出库商品列表信息 ")
    @RequestMapping("/update")
    @RequiresPermissions("out:goods:update")
    public RestResponse update(@RequestBody OutGoodsEntity outGoods) {

        outGoodsService.update(outGoods);

        return RestResponse.success();
    }

    /**
     * 根据主键删除出库商品列表信息 
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除出库商品列表信息 ")
    @RequestMapping("/delete")
    @RequiresPermissions("out:goods:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        outGoodsService.deleteBatch(ids);

        return RestResponse.success();
    }
}
