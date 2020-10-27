/*
 * 项目名称:platform-plus
 * 类名称:SysMobileUserController.java
 * 包名称:com.platform.modules.sys.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-16 16:32:44        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysMobileUserEntity;
import com.platform.modules.sys.service.SysMobileUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 手持用户Controller
 *
 * @author 凯晟admin
 * @date 2019-09-16 16:32:44
 */
@RestController
@RequestMapping("sys/mobileuser")
public class SysMobileUserController extends AbstractController {
    @Autowired
    private SysMobileUserService sysMobileUserService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("sys:mobileuser:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<SysMobileUserEntity> list = sysMobileUserService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询手持用户
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:mobileuser:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = sysMobileUserService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:mobileuser:info")
    public RestResponse info(@PathVariable("id") String id) {
        SysMobileUserEntity sysMobileUser = sysMobileUserService.getById(id);

        return RestResponse.success().put("mobileuser", sysMobileUser);
    }

    /**
     * 新增手持用户
     *
     * @param sysMobileUser sysMobileUser
     * @return RestResponse
     */
    @SysLog("新增手持用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:mobileuser:save")
    public RestResponse save(@RequestBody SysMobileUserEntity sysMobileUser) {
        if(StringUtils.isNotBlank(sysMobileUser.getPassword())){
            sysMobileUser.setPassword(DigestUtils.sha256Hex(sysMobileUser.getPassword()));
        }
        sysMobileUserService.add(sysMobileUser);

        return RestResponse.success();
    }

    /**
     * 修改手持用户
     *
     * @param sysMobileUser sysMobileUser
     * @return RestResponse
     */
    @SysLog("修改手持用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:mobileuser:update")
    public RestResponse update(@RequestBody SysMobileUserEntity sysMobileUser) {
        SysMobileUserEntity old=sysMobileUserService.getById(sysMobileUser.getId());
        if(old!=null){
            if(StringUtils.isNotBlank(sysMobileUser.getPassword())){
                sysMobileUser.setPassword(DigestUtils.sha256Hex(sysMobileUser.getPassword()));
            }else{
                sysMobileUser.setPassword(old.getPassword());
            }
            sysMobileUserService.update(sysMobileUser);
        }

        return RestResponse.success();
    }

    /**
     * 根据主键删除手持用户
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除手持用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:mobileuser:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        sysMobileUserService.deleteBatch(ids);

        return RestResponse.success();
    }
}
