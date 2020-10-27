/*
 * 项目名称:platform-plus
 * 类名称:RegisterUserController.java
 * 包名称:com.platform.modules.register.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-17 08:52:12        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.register;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.TencentSmsUtil;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.register.service.RegisterUserService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller
 *
 * @author Mr.panbb
 * @date 2020-03-17 08:52:12
 */
@RestController
@RequestMapping("register/user")
public class RegisterUserController extends AbstractController {
    @Autowired
    private RegisterUserService registerUserService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("register:user:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<RegisterUserEntity> list = registerUserService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("register:user:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = registerUserService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("register:user:info")
    public RestResponse info(@PathVariable("id") String id) {
        RegisterUserEntity registerUser = registerUserService.getById(id);

        return RestResponse.success().put("user", registerUser);
    }

    /**
     * 新增
     *
     * @param registerUser registerUser
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("register:user:save")
    public RestResponse save(@RequestBody RegisterUserEntity registerUser) {
        registerUserService.add(registerUser);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param registerUser registerUser
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("register:user:update")
    public RestResponse update(@RequestBody RegisterUserEntity registerUser) {
        registerUserService.update(registerUser);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param id id
     * @return RestResponse
     */
    @SysLog("禁用用户")
    @RequestMapping("/delete")
    @RequiresPermissions("register:user:delete")
    public RestResponse delete(String id) {
        registerUserService.delete(id);
        return RestResponse.success();
    }

    /**
     * 禁用用户
     * @param id
     * @return
     */
    @SysLog("启用用户")
    @RequestMapping("/enable")
    @RequiresPermissions("register:user:delete")
    public RestResponse enable(String id) {
        registerUserService.enable(id);
        return RestResponse.success();
    }

    /**
     * 审核用户
     * @param ids
     * @return
     */
    @SysLog("审核")
    @RequestMapping("/check")
    @RequiresPermissions("register:user:check")
    public RestResponse check(@RequestBody String[] ids) {
        registerUserService.checkBatch(ids);
        // 查询所有要发送短信的账户
        Collection<RegisterUserEntity> userList = registerUserService.listByIds(Arrays.asList(ids));
        Iterator<RegisterUserEntity> iterator = userList.iterator();
        // 要发送短信的手机号数组
        while (iterator.hasNext()) {
            RegisterUserEntity user = iterator.next();
            // 审核成功，发送短信通知
            String[] templateParams = {user.getPhoneNumber()};
            String[] phoneNumbers = {"+86" +user.getPhoneNumber()};
            TencentSmsUtil.SendSms(phoneNumbers, TencentSmsUtil.TEMPLATE_ID_03, templateParams);
        }
        return RestResponse.success();
    }

}
