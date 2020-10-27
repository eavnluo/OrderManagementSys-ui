/*
 * 项目名称:platform-plus
 * 类名称:StatementInfoController.java
 * 包名称:com.platform.modules.statement.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-30 14:33:17        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.web;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.EasyPoiUtils;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.bill.entity.BillInfoEntity;
import com.platform.modules.bill.entity.BillItemEntity;
import com.platform.modules.bill.service.BillInfoService;
import com.platform.modules.bill.service.BillItemService;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.register.entity.RegisterUserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结算管理接口
 *
 * @author zlm
 * @date 2020-04-02 10:30:17
 */

@Slf4j
@RestController
@RequestMapping("web/bill/info")
@Api(tags = "BillInfoController|结算管理接口")
public class BillInfoController extends ApiBaseController {
    @Autowired
    private BillInfoService billInfoService;
    @Autowired
    private OrderHeaderInfoService orderHeaderInfoService;
    @Autowired
    private BillItemService billItemService;


    /**
     * 导出报表时的标题
     */
    private static final String FILE_TITLE = "title";

    /**
     * 按月份汇总
     *
     * @return
     */
    @GetMapping("/getListGroupMonth")
    public RestResponse getListGroupMonth(@RequestParam Map<String, Object> params) {
        Page page = billInfoService.getListGroupMonth(params);
        return RestResponse.success().put("page", page);
    }

    /**
     * 获取指定年月信息列表
     *
     * @param params 查询条件
     * @return
     */
    @GetMapping("/getListByMonth")
    public RestResponse getListByMonth(@RequestParam Map<String, Object> params) {
        Page page = billInfoService.getListByMonth(params);
        return RestResponse.success().put("page", page);
    }

    /**
     * 获取指定订单号信息列表
     *
     * @param params 查询条件
     * @return
     */
    @GetMapping("/getListByOrderNo")
    public RestResponse getListByOrderNo(@RequestParam Map<String, Object> params) {
        List<BillInfoEntity> list = billInfoService.getListByOrderNo(params);
        return RestResponse.success().put("list", list);
    }

    /**
     * 获取指定账单信息
     *
     * @param params 请求参数
     * @return
     */
    @GetMapping("/getInfoByOrderItem")
    public RestResponse getInfoByOrderItem(@RequestParam Map<String, Object> params) {
        // 获取账单头部
        Map<String, Object> orderInfo = orderHeaderInfoService.findByOrderItem(params.get("orderItem").toString());
        // 获取账单信息
        BillInfoEntity statementInfo = billInfoService.getByOrderItem(params);
        // 获取费用信息
        Map<String, Object> attr = new HashMap<>();
        attr.put("infoId", statementInfo.getId());
        List<BillItemEntity> statementItem = billItemService.queryAll(attr);

        JSONObject data = new JSONObject();
        data.put("orderInfo", orderInfo);
        data.put("statementInfo", statementInfo);
        data.put("statementItem", statementItem);

        return RestResponse.success().put("data", data);
    }


    /**
     * 确认账单明细
     *
     * @param params billNo 账单编号, itemIds 明细ID拼接字符串， 例如：xxx,xxx,xxx
     * @return RestResponse
     */
    @PostMapping(value = "/confirmItem")
    @ApiOperation(value = "确认账单明细", notes = "确认账单明细")
    public RestResponse confirmItem(@LoginUser RegisterUserEntity user,
                                    @RequestBody Map<String, String> params) {
        // 备注
        String remark = params.get("remark");
        if (StringUtils.isBlank(remark)) {
            remark = "";
        }
        billInfoService.confirmItem(params.get("billNo"), params.get("itemIds"), user.getId(), remark);
        return RestResponse.success();
    }

    /**
     * 导出账单
     *
     * @param params
     */
    @GetMapping("/export")
    public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        String title = "账单信息";
        if (!StringUtils.isEmpty(params.get(FILE_TITLE))) {
            title = params.get(FILE_TITLE).toString();
        }
        List<Map<String, Object>> list = billInfoService.findListByGroupMonth(params);
        Map<String, Object> maps = Maps.newHashMap();
        maps.put("list", list);
        TemplateExportParams exportParams = new TemplateExportParams("static/export_bill_receivable.xls");
        exportParams.setSheetName(title);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, maps);
        EasyPoiUtils.downLoadExcel(title +"-"+ DateUtils.format(new Date(), "yyyyMMdd") + "-"
                + ".xls", response, workbook);
    }

    /**
     * 导出指定月的账单
     * @param params
     * @param response
     */
    @GetMapping("/exportByMonth")
    public void exportByMonth(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        String title = "单月账单信息";
        if (!StringUtils.isEmpty(params.get(FILE_TITLE))) {
            title = params.get(FILE_TITLE).toString();
        }
        // 账单月份
        String yearMonth = "";
        if (!StringUtils.isEmpty(params.get("yearMonth"))) {
            yearMonth = params.get("yearMonth").toString();
        }
        List<BillInfoEntity> list = billInfoService.findListByGroupOrder(params);
        Map<String, Object> maps = Maps.newHashMap();
        maps.put("list", list);
        TemplateExportParams exportParams = new TemplateExportParams("static/export_bill_order.xls");
        exportParams.setSheetName(title);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, maps);
        EasyPoiUtils.downLoadExcel(title +"-"+ yearMonth +"-" + ".xls", response, workbook);
    }

    /**
     * 导出指订单下的账单
     * @param params
     * @param response
     */
    @GetMapping("/exportByOrder")
    public void exportByOrder(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        String title = "单个订单账单信息";
        if (!StringUtils.isEmpty(params.get(FILE_TITLE))) {
            title = params.get(FILE_TITLE).toString();
        }
        // 订单编号
        String orderNo = "";
        if (!StringUtils.isEmpty(params.get("orderNo"))) {
            orderNo = params.get("orderNo").toString();
        }
        List<BillInfoEntity> list = billInfoService.getListByOrderNo(params);
        Map<String, Object> maps = Maps.newHashMap();
        maps.put("list", list);
        TemplateExportParams exportParams = new TemplateExportParams("static/export_bill_item.xls");
        exportParams.setSheetName(title);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, maps);
        EasyPoiUtils.downLoadExcel(title +"-"+ orderNo +"-" + ".xls", response, workbook);
    }

}
