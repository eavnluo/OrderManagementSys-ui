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
package com.platform.modules.web.base;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.LoginUser;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseAreaEntity;
import com.platform.modules.base.entity.BaseCustomerContactsEntity;
import com.platform.modules.base.service.BaseAreaService;
import com.platform.modules.base.service.BaseCustomerContactsService;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.register.service.RegisterUserService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zlm
 * @date 2020-04-14 16:12:52
 */
@RestController
@RequestMapping("web/customercontacts")
@Api(tags = "BaseCustomerContactsController|web客户联系人")
public class BaseCustomerContactsController extends ApiController {
    @Autowired
    private BaseCustomerContactsService baseCustomerContactsService;
    @Autowired
    private RegisterUserService registerUserService;
    @Autowired
    private BaseAreaService baseAreaService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@LoginUser RegisterUserEntity user, @RequestParam Map<String, Object> params) {
        params.put("registerUserId", user.getId());
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
    public RestResponse list(@LoginUser RegisterUserEntity user, @RequestParam Map<String, Object> params) {
        params.put("registerUserId", user.getId());
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
        // 省列表
        List<BaseAreaEntity> provinceList = baseAreaService.getListByParentId("100000");
        // 该省的市列表
        List<BaseAreaEntity> cityList = baseAreaService.getListByParentId(baseCustomerContacts.getProvince());
        // 该市的区列表
        List<BaseAreaEntity> areaList = baseAreaService.getListByParentId(baseCustomerContacts.getCity());

        return RestResponse.success()
                .put("customercontacts", baseCustomerContacts)
                .put("provinceList", provinceList)
                .put("cityList", cityList)
                .put("areaList", areaList);
    }

    /**
     * 新增
     *
     * @param baseCustomerContacts baseCustomerContacts
     * @return RestResponse
     */
    @RequestMapping("/save")
    public RestResponse save(@LoginUser RegisterUserEntity user, @RequestBody BaseCustomerContactsEntity baseCustomerContacts) {
        if (!registerUserService.isAuthentication(user)) {
            throw new BusinessException("企业未认证，请先完善信息");
        }
        // 验证当前用户下该手机号是否唯一
        if (baseCustomerContactsService.getByPhone(baseCustomerContacts.getPhone(), user.getId()) != null) {
            throw new BusinessException("该手机号已存在");
        }
        baseCustomerContacts.setRegisterUserId(user.getId());
        baseCustomerContacts.setCreatedBy(user.getId());
        baseCustomerContacts.setCreatedTime(new Date());
        baseCustomerContactsService.add(baseCustomerContacts);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param baseCustomerContacts baseCustomerContacts
     * @return RestResponse
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:customercontacts:update")
    public RestResponse update(@LoginUser RegisterUserEntity user, @RequestBody BaseCustomerContactsEntity baseCustomerContacts) {
        BaseCustomerContactsEntity findResult = baseCustomerContactsService.getById(baseCustomerContacts.getId());
        if (!findResult.getPhone().equals(baseCustomerContacts.getPhone())) {
            // 修改了手机号,验证手机号在当前用户下是否唯一
            if (baseCustomerContactsService.getByPhone(baseCustomerContacts.getPhone(), user.getId()) != null) {
                throw new BusinessException("该手机号已存在");
            }
        }

        baseCustomerContacts.setUpdatedBy(user.getId());
        baseCustomerContacts.setUpdatedTime(new Date());
        baseCustomerContactsService.update(baseCustomerContacts);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param params ids
     * @return RestResponse
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:customercontacts:delete")
    public RestResponse delete(@RequestBody Map<String, String[]> params) {
        baseCustomerContactsService.deleteBatch(params.get("ids"));

        return RestResponse.success();
    }
}
