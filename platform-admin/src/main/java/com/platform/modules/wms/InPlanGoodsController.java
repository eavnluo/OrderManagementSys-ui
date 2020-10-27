/*
 * 项目名称:platform-plus
 * 类名称:InPlanGoodsController.java
 * 包名称:com.platform.modules.in.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 11:00:36        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.wms.entity.InPlanGoodsEntity;
import com.platform.modules.wms.service.InPlanGoodsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 商品集合信息 Controller
 *
 * @author jk
 * @date 2019-09-05 11:00:36
 */
@RestController
@RequestMapping("in/plangoods")
public class InPlanGoodsController extends AbstractController {
    @Autowired
    private InPlanGoodsService inPlanGoodsService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("in:plangoods:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<InPlanGoodsEntity> list = inPlanGoodsService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询商品集合信息
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("in:plangoods:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = inPlanGoodsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param outerGoodsCode 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{outerGoodsCode}")
    @RequiresPermissions("in:plangoods:info")
    public RestResponse info(@PathVariable("outerGoodsCode") String outerGoodsCode) {
        InPlanGoodsEntity inPlanGoods = inPlanGoodsService.getById(outerGoodsCode);

        return RestResponse.success().put("plangoods", inPlanGoods);
    }

    /**
     * 新增商品集合信息
     *
     * @param inPlanGoods inPlanGoods
     * @return RestResponse
     */
    @SysLog("新增商品集合信息 ")
    @RequestMapping("/save")
    @RequiresPermissions("in:plangoods:save")
    public RestResponse save(@RequestBody InPlanGoodsEntity inPlanGoods) {
        SysUserEntity currentUser = getUser();
        inPlanGoods.setCreatedBy(currentUser.getUserId());
        inPlanGoods.setUpdatedBy(currentUser.getUserId());
        inPlanGoodsService.add(inPlanGoods);

        return RestResponse.success();
    }

    /**
     * 通过商品编号查询商品规格
     *
     * @param goodsCode
     * @return
     */
    @RequestMapping("/queryPackUnit")
    public RestResponse queryPackUnit(@RequestBody String goodsCode){
        String packUnit = inPlanGoodsService.queryPackUnit(goodsCode);
        return RestResponse.success().put("packUnit",packUnit);
    }

    /**
     * 修改商品集合信息
     *
     * @param inPlanGoods inPlanGoods
     * @return RestResponse
     */
    @SysLog("修改商品集合信息 ")
    @RequestMapping("/update")
    @RequiresPermissions("in:plangoods:update")
    public RestResponse update(@RequestBody InPlanGoodsEntity inPlanGoods) {
        inPlanGoods.setUpdatedBy(getUser().getUserId());
        inPlanGoodsService.update(inPlanGoods);

        return RestResponse.success();
    }

    /**
     * 根据主键删除商品集合信息
     *
     * @param outerGoodsCodes outerGoodsCodes
     * @return RestResponse
     */
    @SysLog("删除商品集合信息 ")
    @RequestMapping("/delete")
    @RequiresPermissions("in:plangoods:delete")
    public RestResponse delete(@RequestBody String[] outerGoodsCodes) {
        inPlanGoodsService.deleteBatch(outerGoodsCodes);

        return RestResponse.success();
    }
}
