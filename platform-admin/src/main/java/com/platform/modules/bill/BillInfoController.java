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
package com.platform.modules.bill;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.EasyPoiUtils;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.bill.entity.BillInfoEntity;
import com.platform.modules.bill.entity.BillItemEntity;
import com.platform.modules.bill.service.BillInfoService;
import com.platform.modules.bill.service.BillItemService;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import com.platform.modules.sys.service.SysFileAttachmentService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 结算单Controller
 *
 * @author Mr.panbb
 * @date 2020-03-30 14:33:17
 */
@RestController
@RequestMapping("bill/info")
public class BillInfoController extends AbstractController {
    @Autowired
    private BillInfoService billInfoService;
    @Autowired
    private BillItemService billItemService;
    @Autowired
    private OrderHeaderInfoService orderHeaderInfoService;
    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;
    @Autowired
    private SysFileAttachmentService sysFileAttachmentService;

    /**
     * 导出报表时的标题
     */
    private static final String FILE_TITLE = "title";

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<BillInfoEntity> list = billInfoService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询结算单
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = billInfoService.queryPage(params);

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
        // 1、账单信息
        BillInfoEntity billInfo = billInfoService.getById(id);

        // 2、订单信息
        OrderHeaderInfoEntity order = new OrderHeaderInfoEntity();
        // 3、附件列表
        List<SysFileAttachmentEntity> fileList = new ArrayList<>();
        if (billInfo != null) {
            order = orderHeaderInfoService.getByOrderItem(billInfo.getOrderItem());
            fileList = sysFileAttachmentService.getListByBelongTo(billInfo.getBillNo());
        }

        // 4、费用项列表
        Map<String, Object> params = new HashMap<>(1);
        params.put("infoId", id);
        List<BillItemEntity> itemList = billItemService.queryAll(params);

        // 5、客户信息
        BaseCustomerInfoEntity customer = new BaseCustomerInfoEntity();
        if (StringUtils.isNotBlank(order.getCustomerId())) {
            customer = baseCustomerInfoService.findByRegistryUserId(order.getCustomerId());
        }

        return RestResponse.success()
                .put("billInfo", billInfo)
                .put("order", order)
                .put("itemList", itemList)
                .put("customer", customer)
                .put("fileList", fileList);
    }

    /**
     * 新增结算单
     *
     * @param billInfoEntity statementInfo
     * @return RestResponse
     */
    @SysLog("新增结算单")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody BillInfoEntity billInfoEntity) {
        billInfoEntity.setCreateBy(getUserId());
        billInfoEntity.setCreateTime(new Date());
        billInfoService.add(billInfoEntity);

        return RestResponse.success();
    }

    /**
     * 修改结算单
     *
     * @param statementInfo statementInfo
     * @return RestResponse
     */
    @SysLog("修改结算单")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody BillInfoEntity statementInfo) {

        billInfoService.update(statementInfo);

        return RestResponse.success();
    }

    /**
     * 根据主键删除结算单
     *
     * @param id id
     * @return RestResponse
     */
    @SysLog("删除结算单")
    @RequestMapping("/delete")
    public RestResponse delete(String id) {
        billInfoService.deleteById(id, getUserId());

        return RestResponse.success();
    }

    /**
     * 根据主键删除结算单
     *
     * @param orderItem 工作单号
     * @return RestResponse
     */
    @SysLog("确认结算单")
    @RequestMapping("/doConfirm")
    public RestResponse doConfirm(String orderItem) {
        billInfoService.doConfirm(orderItem, getUserId());
        return RestResponse.success();
    }

    /**
     * 按月份汇总
     *
     * @return RestResponse
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
     * @return RestResponse
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
     * @return RestResponse
     */
    @GetMapping("/getListByOrderNo")
    public RestResponse getListByOrderNo(@RequestParam Map<String, Object> params) {
        List<BillInfoEntity> list = billInfoService.getListByOrderNo(params);
        return RestResponse.success().put("list", list);
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

    /**
     * 根据工作单号查询账单信息
     *
     * @param params 请求参数
     * @return RestResponse
     */
    @GetMapping("/getByOrderItem")
    public RestResponse getByOrderItem(@RequestParam Map<String, Object> params) {
        BillInfoEntity info = billInfoService.getByOrderItem(params);
        return RestResponse.success().put("bill", info);
    }

}
