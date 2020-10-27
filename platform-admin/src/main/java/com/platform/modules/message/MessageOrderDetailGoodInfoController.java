/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderDetailGoodInfoController.java
 * 包名称:com.platform.modules.message.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 14:53:26        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.message.entity.MessageOrderDetailGoodInfoEntity;
import com.platform.modules.message.service.MessageOrderDetailGoodInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单详情表  Controller
 *
 * @author 凯晟admin
 * @date 2019-09-10 14:53:26
 */
@RestController
@RequestMapping("message/orderdetailgoodinfo")
public class MessageOrderDetailGoodInfoController extends AbstractController {
    @Autowired
    private MessageOrderDetailGoodInfoService messageOrderDetailGoodInfoService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("message:orderdetailgoodinfo:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<MessageOrderDetailGoodInfoEntity> list = messageOrderDetailGoodInfoService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询订单详情表  
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("message:orderdetailgoodinfo:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = messageOrderDetailGoodInfoService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param delFlag 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{delFlag}")
    @RequiresPermissions("message:orderdetailgoodinfo:info")
    public RestResponse info(@PathVariable("delFlag") String delFlag) {
        MessageOrderDetailGoodInfoEntity messageOrderDetailGoodInfo = messageOrderDetailGoodInfoService.getById(delFlag);

        return RestResponse.success().put("orderdetailgoodinfo", messageOrderDetailGoodInfo);
    }

    /**
     * 新增订单详情表  
     *
     * @param messageOrderDetailGoodInfo messageOrderDetailGoodInfo
     * @return RestResponse
     */
    @SysLog("新增订单详情表  ")
    @RequestMapping("/save")
    @RequiresPermissions("message:orderdetailgoodinfo:save")
    public RestResponse save(@RequestBody MessageOrderDetailGoodInfoEntity messageOrderDetailGoodInfo) {

        messageOrderDetailGoodInfoService.add(messageOrderDetailGoodInfo);

        return RestResponse.success();
    }

    /**
     * 修改订单详情表  
     *
     * @param messageOrderDetailGoodInfo messageOrderDetailGoodInfo
     * @return RestResponse
     */
    @SysLog("修改订单详情表  ")
    @RequestMapping("/update")
    @RequiresPermissions("message:orderdetailgoodinfo:update")
    public RestResponse update(@RequestBody MessageOrderDetailGoodInfoEntity messageOrderDetailGoodInfo) {

        messageOrderDetailGoodInfoService.update(messageOrderDetailGoodInfo);

        return RestResponse.success();
    }

    /**
     * 根据主键删除订单详情表  
     *
     * @param delFlags delFlags
     * @return RestResponse
     */
    @SysLog("删除订单详情表  ")
    @RequestMapping("/delete")
    @RequiresPermissions("message:orderdetailgoodinfo:delete")
    public RestResponse delete(@RequestBody String[] delFlags) {
        messageOrderDetailGoodInfoService.deleteBatch(delFlags);

        return RestResponse.success();
    }
}
