/*
 * 项目名称:platform-plus
 * 类名称:StatementItemController.java
 * 包名称:com.platform.modules.statement.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-30 14:33:17        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.bill;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.bill.entity.BillItemEntity;
import com.platform.modules.bill.service.BillItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 结算项Controller
 *
 * @author Mr.panbb
 * @date 2020-03-30 14:33:17
 */
@RestController
@RequestMapping("bill/item")
public class BillItemController extends AbstractController {
    @Autowired
    private BillItemService billItemService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<BillItemEntity> list = billItemService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询结算项
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = billItemService.queryPage(params);

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
        BillItemEntity statementItem = billItemService.getById(id);

        return RestResponse.success().put("item", statementItem);
    }

    /**
     * 新增结算项
     *
     * @param statementItem statementItem
     * @return RestResponse
     */
    @SysLog("新增结算项")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody BillItemEntity statementItem) {

        billItemService.add(statementItem);

        return RestResponse.success();
    }

    /**
     * 修改结算项
     *
     * @param statementItem statementItem
     * @return RestResponse
     */
    @SysLog("修改结算项")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody BillItemEntity statementItem) {

        billItemService.update(statementItem);

        return RestResponse.success();
    }

    /**
     * 根据主键删除结算项
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除结算项")
    @RequestMapping("/delete")
    public RestResponse delete(@RequestBody String[] ids) {
        billItemService.deleteBatch(ids);

        return RestResponse.success();
    }
}
