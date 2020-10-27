package com.platform.modules.chart.controller;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.utils.RestResponse;
import com.platform.modules.web.ApiBaseController;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.ferries.service.FerriesReportService;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.wms.entity.StoreInventoryInfoEntity;
import com.platform.modules.wms.service.StoreInventoryInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 监控数据接口
 * @author Mr.panbb
 * @date 2019-09-06
 */
@Api(tags = "ChartController|监控数据接口")
@RestController
@RequestMapping(value = "/chart")
public class ChartController extends ApiBaseController {

    @Autowired
    private OrderHeaderInfoService orderHeaderInfoService;
    @Autowired
    private FerriesReportService ferriesReportService;
    @Autowired
    private StoreInventoryInfoService storeInventoryInfoService;
    @Autowired
    private BaseSupplyInfoService baseSupplyInfoService;

    /**
     * 获取数字数据
     * @return RestResponse
     */
    @ApiOperation(value = "获取数字数据")
    @RequestMapping(value = "/numberData")
    public RestResponse numberData() {
        JSONObject data = new JSONObject();
        OrderHeaderInfoEntity orderInfo = new OrderHeaderInfoEntity();
        // 订单总数量 - 全部
        int allOrder = orderHeaderInfoService.findNumberForChart(orderInfo, false);
        data.put("allOrder", allOrder);
        // 订单总数量 - 昨天
        int allOrderY = orderHeaderInfoService.findNumberForChart(orderInfo, true);
        data.put("allOrderY", allOrderY);

        // 在途订单数量 - 全部
        int onWayOrder = orderHeaderInfoService.findNumberOnWay(orderInfo, false);
        data.put("onWayOrder", onWayOrder);
        // 在途订单数量 - 昨天
        int onWayOrderY = orderHeaderInfoService.findNumberOnWay(orderInfo, true);
        data.put("onWayOrderY", onWayOrderY);

        // 入库订单数量 - 全部
        orderInfo.setOrderType("R");
        int inOrder = orderHeaderInfoService.findNumberForChart(orderInfo, false);
        data.put("inOrder", inOrder);
        // 入库订单数量 - 昨天
        int inOrderY = orderHeaderInfoService.findNumberForChart(orderInfo, true);
        data.put("inOrderY", inOrderY);

        // 出库订单数量 - 全部
        orderInfo.setOrderType("I");
        int outOrder = orderHeaderInfoService.findNumberForChart(orderInfo, false);
        data.put("outOrder", outOrder);
        // 出库订单数量 - 昨天
        int outOrderY = orderHeaderInfoService.findNumberForChart(orderInfo, true);
        data.put("outOrderY", outOrderY);

        // 仓库数量
        int storeNum = baseSupplyInfoService.findNumberForChart(false);
        data.put("storeNum", storeNum);
        // 仓库数量 - 昨天
        int storeNumY = baseSupplyInfoService.findNumberForChart(true);
        data.put("storeNumY", storeNumY);

        return RestResponse.success().put("data", data);
    }

    /**
     * 月度日订单（月度日订单总数量变化、月度日订单入库数量变化、月度日订单出库数量变化）
     * @param orderInfo 查询条件
     * @return RestResponse
     */
    @ApiOperation(value = "月度日订单")
    @RequestMapping(value = "/dayOrderData")
    public RestResponse orderData(OrderHeaderInfoEntity orderInfo) {
        JSONObject data = orderHeaderInfoService.findChartData(orderInfo);
        return RestResponse.success().put("data", data);
    }

    /**
     * 月度订单环比总数量变化
     * @param orderInfo 查询条件
     * @return RestResponse
     */
    @ApiOperation(value = "月度订单环比总数量变化")
    @RequestMapping(value = "/monthOrderHb")
    public RestResponse monthOrderHb(OrderHeaderInfoEntity orderInfo) {
        JSONObject data = orderHeaderInfoService.findChartDataHb(orderInfo);
        return RestResponse.success().put("data", data);
    }

    /**
     * 周箱量统计
     * @return
     */
    @ApiOperation(value = "周箱量统计")
    @RequestMapping(value = "/weekBox")
    public RestResponse weekBox() {
        JSONObject data = ferriesReportService.findChartData();

        return RestResponse.success().put("data", data);
    }

    /**
     * 月度库存总量变化
     * @return Object
     */
    @ApiOperation(value = "月度库存总量变化")
    @RequestMapping(value = "/monthStock")
    public RestResponse monthStock() {
        JSONObject data = storeInventoryInfoService.findMonthStock(new StoreInventoryInfoEntity());
        return RestResponse.success().put("data", data);
    }

    /**
     * 获取最新的国际运输信息
     * @return Object
     */
    @ApiOperation(value = "获取最新的国际运输信息")
    @GetMapping(value = "/getItInfo")
    public RestResponse getItInfo() {
        JSONObject data = ferriesReportService.getNewInfo();
        return RestResponse.success().put("data", data);
    }

}
