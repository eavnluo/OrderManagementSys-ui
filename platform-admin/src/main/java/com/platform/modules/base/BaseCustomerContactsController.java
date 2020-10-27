/*
 * 项目名称:platform-plus
 * 类名称:BaseCustomerContactsController.java
 * 包名称:com.platform.modules.base.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-04-14 16:12:52        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.base.entity.BaseCustomerContactsEntity;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.service.BaseCustomerContactsService;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zlm
 * @date 2020-04-14 16:12:52
 */
@RestController
@RequestMapping("base/customercontacts")
public class BaseCustomerContactsController extends AbstractController {
    @Autowired
    private BaseCustomerContactsService baseCustomerContactsService;
    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<BaseCustomerContactsEntity> list = baseCustomerContactsService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        BaseCustomerInfoEntity customerInfo = baseCustomerInfoService.getById(params.get("customerId").toString());
        if (customerInfo == null || StringUtils.isBlank(customerInfo.getRegisterUserId())) {
            return RestResponse.success().put("page", null);
        }
        String registerUserId = customerInfo.getRegisterUserId();
        params.put("registerUserId", registerUserId);
        Page page = baseCustomerContactsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    public RestResponse info(@PathVariable("id") String id) {
        BaseCustomerContactsEntity baseCustomerContacts = baseCustomerContactsService.getById(id);

        return RestResponse.success().put("customercontacts", baseCustomerContacts);
    }

    /**
     * 新增
     *
     * @param baseCustomerContacts baseCustomerContacts
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody BaseCustomerContactsEntity baseCustomerContacts) {

        baseCustomerContactsService.add(baseCustomerContacts);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param baseCustomerContacts baseCustomerContacts
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody BaseCustomerContactsEntity baseCustomerContacts) {

        baseCustomerContactsService.update(baseCustomerContacts);

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
    public RestResponse delete(@RequestBody String[] ids) {
        baseCustomerContactsService.deleteBatch(ids);

        return RestResponse.success();
    }
}
