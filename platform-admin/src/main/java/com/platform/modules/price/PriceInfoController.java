/*
 * 项目名称:platform-plus
 * 类名称:PriceInfoController.java
 * 包名称:com.platform.modules.price.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 13:23:09        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.price;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.price.entity.PriceDetailEntity;
import com.platform.modules.price.entity.PriceInfoEntity;
import com.platform.modules.price.service.PriceDetailService;
import com.platform.modules.price.service.PriceInfoService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import com.platform.modules.sys.service.SysFileAttachmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 价格信息Controller
 *
 * @author Mr.panbb
 * @date 2020-03-24 13:23:09
 */
@RestController
@RequestMapping("price/info")
public class PriceInfoController extends AbstractController {
    @Autowired
    private PriceInfoService priceInfoService;
    @Autowired
    private PriceDetailService priceDetailService;
    @Autowired
    private SysFileAttachmentService sysFileAttachmentService;
    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("price:info:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<PriceInfoEntity> list = priceInfoService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询价格信息
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("price:info:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = priceInfoService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("price:info:info")
    public RestResponse info(@PathVariable("id") String id) {
        // 报价单
        PriceInfoEntity priceInfo = priceInfoService.getById(id);
        // 报价明细
        Map<String, Object> map = new HashMap<>(2);
        map.put("priceInfoId", id);
        List<PriceDetailEntity> priceDetailList = priceDetailService.queryAll(map);
        priceInfo.setPriceDetailList(priceDetailList);
        // 附件列表
        map.put("belongTo", id);
        List<SysFileAttachmentEntity> fileList = sysFileAttachmentService.queryAll(map);
        priceInfo.setFileList(fileList);
        return RestResponse.success().put("info", priceInfo);
    }

    /**
     * 新增价格信息
     *
     * @param priceInfo priceInfo
     * @return RestResponse
     */
    @SysLog("新增价格信息")
    @RequestMapping("/save")
    @RequiresPermissions("price:info:save")
    public RestResponse save(@RequestBody PriceInfoEntity priceInfo) {
        priceInfo.setCreateBy(getUserId());
        priceInfo.setCreateTime(new Date());
        priceInfoService.add(priceInfo);
        return RestResponse.success().put("id", priceInfo.getId());
    }

    /**
     * 修改价格信息
     *
     * @param priceInfo priceInfo
     * @return RestResponse
     */
    @SysLog("修改价格信息")
    @RequestMapping("/update")
    @RequiresPermissions("price:info:update")
    public RestResponse update(@RequestBody PriceInfoEntity priceInfo) {
        priceInfo.setUpdateBy(getUserId());
        priceInfo.setUpdateTime(new Date());
        priceInfoService.update(priceInfo);

        return RestResponse.success();
    }

    /**
     * 更新报价单状态
     *
     * @param id     主键
     * @param status 状态
     * @return RestResponse
     */
    @SysLog("更新报价单状态")
    @RequestMapping("/updateStatus")
    @RequiresPermissions("price:info:invalid")
    public RestResponse updateStatus(@RequestParam("id") String id, @RequestParam("status") String status) {
        priceInfoService.updateStatus(id, getUserId(), new Date(), status);

        return RestResponse.success();
    }

    /**
     * 创建报价单编号
     * @return RestResponse
     */
    @SysLog("创建报价单编号")
    @RequestMapping("/createPriceNo")
    public RestResponse createPriceNo() {
        String priceNo = priceInfoService.createPriceNo();
        return RestResponse.success().put("priceNo", priceNo);
    }

    /**
     * 根据费用项，查询相关报价供应商列表
     * @param params 查询参数
     * @return RestResponse
     */
    @SysLog("根据费用项，查询相关报价供应商列表")
    @RequestMapping("/findSupplyList")
    public RestResponse findSupplyList(@RequestParam Map<String, Object> params) {
        List<Map<String, Object>> list = priceInfoService.findSupplyList(params);
        return RestResponse.success().put("list", list);
    }

}
