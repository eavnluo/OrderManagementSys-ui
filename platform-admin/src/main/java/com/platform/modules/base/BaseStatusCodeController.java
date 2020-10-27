package com.platform.modules.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseStatusCodeEntity;
import com.platform.modules.base.entity.BaseStatusGroupEntity;
import com.platform.modules.base.service.BaseStatusCodeService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 状态维护表 Controller
 *
 * @author 凯晟admin
 * @date 2019-09-09 08:24:39
 */
@RestController
@RequestMapping("base/statuscode")
public class BaseStatusCodeController extends AbstractController {
    @Autowired
    private BaseStatusCodeService baseStatusCodeService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<BaseStatusCodeEntity> list = baseStatusCodeService.queryAll(params);
        return RestResponse.success().put("list", list);
    }



    /**
     * 分页查询状态维护表 
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("base:statuscode:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = baseStatusCodeService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:statuscode:info")
    public RestResponse info(@PathVariable("id") String id) {
        BaseStatusCodeEntity baseStatusCode = baseStatusCodeService.getById(id);

        return RestResponse.success().put("statuscode", baseStatusCode);
    }

    /**
     * 新增状态维护表 
     *
     * @param baseStatusCode baseStatusCode
     * @return RestResponse
     */
    @SysLog("新增状态维护表 ")
    @RequestMapping("/save")
    @RequiresPermissions("base:statuscode:save")
    public RestResponse save(@RequestBody BaseStatusCodeEntity baseStatusCode) {

        baseStatusCodeService.add(baseStatusCode);

        return RestResponse.success();
    }

    /**
     * 修改状态维护表 
     *
     * @param baseStatusCode baseStatusCode
     * @return RestResponse
     */
    @SysLog("修改状态维护表 ")
    @RequestMapping("/update")
    @RequiresPermissions("base:statuscode:update")
    public RestResponse update(@RequestBody BaseStatusCodeEntity baseStatusCode) {

        baseStatusCodeService.update(baseStatusCode);

        return RestResponse.success();
    }

    /**
     * 根据主键删除状态维护表 
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除状态维护表 ")
    @RequestMapping("/delete")
    @RequiresPermissions("base:statuscode:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        baseStatusCodeService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 分组状态
     * @return
     */
    @GetMapping("/getStatusGroup")
    public RestResponse getStatusGroup() {
        List<BaseStatusGroupEntity> list = baseStatusCodeService.getStatusGroup();

        return RestResponse.success().put("list", list);
    }
}
