/*
 * 项目名称:platform-plus
 * 类名称:OrderHeaderInfoController.java
 * 包名称:com.platform.modules.order.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:30        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.OrderType;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.*;
import com.platform.modules.base.entity.BaseCustomerContactsEntity;
import com.platform.modules.base.entity.BaseStatusCodeEntity;
import com.platform.modules.base.service.BaseCustomerContactsService;
import com.platform.modules.base.service.BaseStatusCodeService;
import com.platform.modules.order.entity.OrderCarRelationEntity;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import com.platform.modules.order.entity.OrderGoodUniqueCodeScanEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.service.OrderCarRelationService;
import com.platform.modules.order.service.OrderDetailGoodsInfoService;
import com.platform.modules.order.service.OrderGoodUniqueCodeScanService;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.wms.entity.InPlanLogisticEntity;
import com.platform.modules.wms.entity.StoreInventoryGoodsEntity;
import com.platform.modules.wms.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 订单头表 Controller
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:30
 */
@RestController
@RequestMapping("order/headerinfo")
public class OrderHeaderInfoController extends AbstractController {
    @Autowired
    private OrderHeaderInfoService orderHeaderInfoService;
    @Autowired
    private OrderGoodUniqueCodeScanService orderGoodUniqueCodeScanService;
    @Autowired
    private InPlanLogisticService inPlanLogisticService;
    @Autowired
    private BaseStatusCodeService baseStatusCodeService;
    @Autowired
    private StoreInventoryInfoService storeInventoryInfoService;
    @Autowired
    private OrderDetailGoodsInfoService orderDetailGoodsInfoService;
    @Autowired
    private WmsServiceBusiness wMsServiceBusiness;
    @Autowired
    private SyWmsService syWmsService;
    @Autowired
    private OrderCarRelationService orderCarRelationService;
    @Autowired
    private StoreInventoryGoodsService storeInventoryGoodsService;
    @Autowired
    private BaseCustomerContactsService baseCustomerContactsService;

    /**
     * 判断订单号是否存在
     *
     * @param orderNo
     * @return
     */
    @SysLog("校验订单号是否存在")
    @RequestMapping("/isOrderNoExist")
    public RestResponse isOrderNoExist(@RequestParam String orderNo) {
        if (StringUtils.isBlank(orderNo)) {
            return RestResponse.error(-10, "参数错误");
        }
        boolean isExist = orderHeaderInfoService.isOrderNoExist(orderNo);
        //如果存在返回错误代码
        if (isExist) {
            return RestResponse.error(-1, "订单号已存在");
        } else {
            return RestResponse.success();
        }

    }

    @SysLog("新增订单头表 ")
    @RequestMapping("/saveCompleteOrder")
    public RestResponse saveCompleteOrder(@RequestBody OrderHeaderInfoEntity orderHeaderInfo) {
        orderHeaderInfoService.addCompleteOrder(orderHeaderInfo);
        return RestResponse.success();
    }

    /**
     * 确认订单
     *
     * @param orderItem 工作单号
     * @param orderType 订单类型
     * @return Object
     */
    @RequestMapping(value = "/confirmOrder")
    public RestResponse confirmOrder(@RequestParam String orderItem, @RequestParam String orderType) {
        if (StringUtils.isBlank(orderItem) || StringUtils.isBlank(orderType)) {
            return RestResponse.error("参数不能为空");
        }
        RestResponse res = orderHeaderInfoService.confirmOrder(orderItem, orderType, getUserId());
        if ("0".equals(res.get("code").toString())) {
            // 订单确认成功，异步将订单下发给仓库
            new Thread(() -> {
                // 下发订单
                syWmsService.sendOrder(orderItem);
            }).start();
        }
        return res;
    }

    /**
     * 展示订单添加表单页面
     *
     * @return
     */
    @RequestMapping("/completeOrderForm")
    public RestResponse showCompleteOrderForm() {
        return RestResponse.success();
    }


    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<OrderHeaderInfoEntity> list = orderHeaderInfoService.queryAll(params);
        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询订单头表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = orderHeaderInfoService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param orderItem 工作单号
     * @return RestResponse
     */
    @RequestMapping("/info/{orderItem}")
    public RestResponse info(@PathVariable("orderItem") String orderItem) {
        JSONObject data = new JSONObject();
        // 获取订单头信息
        OrderHeaderInfoEntity orderHeaderInfo = orderHeaderInfoService.getByOrderItem(orderItem);
        // 获取订单商品信息列表

        if (orderHeaderInfo.getStorageSupplyStatus().equals(Constant.STR_ONE)) {
            // 已完善仓储信息
            List<StoreInventoryGoodsEntity> goodsList = storeInventoryGoodsService.listByOrderItem(orderItem);
            data.put("goodsList", goodsList);
            if (goodsList.size() > 0) {
                orderHeaderInfo.setOutInOddNum(goodsList.get(0).getOutInOddNum());
            }
        } else {
            // 未完善仓储信息
            List<OrderDetailGoodsInfoEntity> goodsList = orderDetailGoodsInfoService.listByOrderItem(orderItem);
            data.put("goodsList", goodsList);
        }
        // 获取订单车辆信息
        List<OrderCarRelationEntity> carList = orderCarRelationService.listByOrderItem(orderItem);
        // 获取发货人收货人信息
        List<BaseCustomerContactsEntity> contactsList = baseCustomerContactsService.getOrderContactsList(orderHeaderInfo);
        data.put("orderHeaderInfo", orderHeaderInfo);
        data.put("carList", carList);
        data.put("contactsList", contactsList);
        return RestResponse.success().put("data", data);
    }


    /**
     * 通过工作单号查询出入库方式
     *
     * @param orderItem
     * @return
     */
    @RequestMapping("/queryOrder")
    public RestResponse queryOrder(@RequestParam String orderItem) {
        Map<String, String> data = orderHeaderInfoService.queryOrder(orderItem);
        return RestResponse.success().put("data", data);
    }

    /**
     * 通过工作单号查询运输信息（保存之前）
     *
     * @param orderItem
     * @return
     */
    @RequestMapping("/queryTransport")
    public RestResponse queryTransport(@RequestParam String orderItem) {
        //分别查出部分信息
        Map<String, Object> transport = orderHeaderInfoService.queryTransportOrder(orderItem);
        if (transport == null) {
            return RestResponse.error(-10, "参数错误");
        }
        if (orderItem.startsWith("KSR")) {
            Map<String, Object> transportCarrier = orderHeaderInfoService.queryTransportCarrier(orderItem);
            Map<String, Object> transportOutInfo = orderHeaderInfoService.OutInfo(orderItem);
            if (transportCarrier != null) {
                transport.putAll(transportCarrier);
            }
            if (transportOutInfo != null) {
                transport.putAll(transportOutInfo);
            }
        } else {
            Map<String, Object> transportCarrier = orderHeaderInfoService.queryTransportCarrier(orderItem);
            Map<String, Object> transportInInfo = orderHeaderInfoService.InInfo(orderItem);
            if (transportCarrier != null) {
                transport.putAll(transportCarrier);
            }
            if (transportInInfo != null) {
                transport.putAll(transportInInfo);
            }
        }


        return RestResponse.success().put("transport", transport);
    }

    @RequestMapping("/getByOrderItem")
    public RestResponse getByOrderItem(@RequestParam String orderItem) {
        OrderHeaderInfoEntity orderHeaderInfoEntity = orderHeaderInfoService.getByOrderItem(orderItem);
        return RestResponse.success().put("orderHeaderInfoEntity", orderHeaderInfoEntity);
    }

    @RequestMapping("/queryOrderItem")
    public RestResponse queryOrderItem() {
        List<String> orderItem = orderHeaderInfoService.queryOrderItem();
        return RestResponse.success().put("orderItem", orderItem);
    }

    /**
     * @param orderItem 根据内部订单编号获取订单号
     * @return
     */
    @RequestMapping("/getByOrderNo")
    public RestResponse orderNo(@RequestParam String orderItem) {
        String orderNo = orderHeaderInfoService.getByOrderNo(orderItem);
        return RestResponse.success().put("orderNo", orderNo);
    }

    /**
     * 根据工作单号查询入库扫码内容
     *
     * @param orderItem   工作单号
     * @param operateType 操作类型
     * @return
     */
    @RequestMapping("/queryScanInAll")
    public RestResponse queryScanInAll(@RequestParam("orderItem") String orderItem, @RequestParam("operateType") String operateType) {
        List<Map<String, Object>> list = orderHeaderInfoService.queryScanInAll(orderItem, operateType);
        return RestResponse.success().put("list", list);
    }

    /**
     * 根据工作单号查询出库扫码内容
     *
     * @param orderItem   工作单号
     * @param operateType 操作类型
     * @return
     */
    @RequestMapping("/queryScanOutAll")
    public RestResponse queryScanOutAll(@RequestParam("orderItem") String orderItem, @RequestParam("operateType") String operateType) {

        List<Map<String, Object>> list = orderHeaderInfoService.queryScanOutAll(orderItem, operateType);
        return RestResponse.success().put("list", list);
    }


    /**
     * 新增订单头表
     *
     * @param orderHeaderInfo orderHeaderInfo
     * @return RestResponse
     */
    @SysLog("新增订单头表 ")
    @RequestMapping("/save")
    @RequiresPermissions("order:headerinfo:save")
    public RestResponse save(@RequestBody OrderHeaderInfoEntity orderHeaderInfo) {

        orderHeaderInfoService.add(orderHeaderInfo);

        return RestResponse.success();
    }

    /**
     * 修改订单头表
     *
     * @param orderHeaderInfo orderHeaderInfo
     * @return RestResponse
     */
    @SysLog("修改订单头表 ")
    @RequestMapping("/update")
    @RequiresPermissions("order:headerinfo:update")
    public RestResponse update(@RequestBody OrderHeaderInfoEntity orderHeaderInfo) {

        orderHeaderInfoService.update(orderHeaderInfo);

        return RestResponse.success();
    }

    /**
     * 根据主键删除订单头表
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除订单头表 ")
    @RequestMapping("/delete")
    @RequiresPermissions("order:headerinfo:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        orderHeaderInfoService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 更改订单状态
     *
     * @param orderHeaderInfo
     * @return
     */
    @PostMapping("/changeStatus")
    public RestResponse changeStatus(@RequestBody OrderHeaderInfoEntity orderHeaderInfo) {
        orderHeaderInfoService.changeStatus(orderHeaderInfo);
        return RestResponse.success();
    }

    /**
     * 导出订单信息
     *
     * @param params 返回参数
     * @param response 请求相应对象
     */
    @GetMapping("/exportData")
    public void exportData(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        OrderExportUtil.orderExport(orderHeaderInfoService, params, response, "static/export_order.xls");
    }

    /**
     * excel文件导入
     *
     * @param file
     * @param action
     * @return
     * @throws Exception
     */
    @RequestMapping("/import")
    public RestResponse upload(@RequestParam(value = "file", required = false) MultipartFile file,
                               @RequestParam(value = "action", required = false) String action) throws Exception {
        return RestResponse.success();
    }

    /***
     * 通过订单信息表获取实际的扫描记录信息
     * @param orderItem
     * @return
     */
    @RequestMapping("/scanListByOrderItem")
    public RestResponse goodScanListByOrderItem(@RequestParam("orderItem") String orderItem, @RequestParam("operateType") String operateType) {
        List<Map<String, Object>> orderGoodUniqueCodeScanEntityList = orderGoodUniqueCodeScanService.listByOrderItem(orderItem, operateType);
        return RestResponse.success().put("list", orderGoodUniqueCodeScanEntityList);
    }

    /**
     * 模拟请求处理
     *
     * @param orderItem 工作单号
     */
    public void mockRequest(String orderItem, String orderType) {
        try {
            // 1、给仓发送订单
            logger.error("1、给仓发送订单");
            // 2、给运输公司发送订单
            logger.error("2、给运输公司发送订单");
            // 运输单号
            String wayBillNo = createWayBill(orderItem);
            // 3、给物联网平台发送运输信息
            logger.error("3、给物联网平台发送运输信息");
            // 4、接收物联网平台的运单信息
            logger.error("4、接收物联网平台的运单信息");
            Map<String, Object> map = new HashMap<>(1);
            map.put("statusType", orderType);
            List<BaseStatusCodeEntity> statusList = baseStatusCodeService.queryAll(map);
            for (BaseStatusCodeEntity status : statusList) {
                // 入库订单创建、出库订单创建
                if ("1000".equals(status.getStatusCode()) || "2000".equals(status.getStatusCode())) {
                    continue;
                }
                // 入库：扫码确认，出库：扫码出库
                if ("1090".equals(status.getStatusCode()) || "".equals(status.getStatusCode())) {
                    scanCode(orderItem);
                }
                // 入库：增加库存，出库：扣除库存
                if ("1100".equals(status.getStatusCode()) || "2040".equals(status.getStatusCode())) {
                    freshStore(orderItem);
                }
                // 暂停5秒钟
                Thread.sleep(5 * 1000);
                logger.error(status.getStatusCode() + " -- " + status.getStatusName());
                OrderHeaderInfoEntity order = new OrderHeaderInfoEntity();
                order.setOrderItem(orderItem);
                order.setStatus(status.getStatusCode());
                order.setStatusName(status.getStatusName());
                order.setStatusUpdte(new Date());
                changeStatus(order);
            }
            // 5、接收仓库的入库信息
            logger.error("5、接收仓库的入库信息");
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 出入库更新库存
     *
     * @param orderItem 工作单号
     */
    public void freshStore(String orderItem) {
        try {
            RestResponse rs = storeInventoryInfoService.fresh(orderItem);
            if (!"0".equals(rs.get("code").toString())) {
                // 库存更新失败
                logger.error(rs.get("msg").toString());
            } else {
                // 库存更新成功
                // 订单信息
                OrderHeaderInfoEntity order = orderHeaderInfoService.getByOrderItem(orderItem);
                // 订单下商品明细
                List<OrderDetailGoodsInfoEntity> goodsList = orderDetailGoodsInfoService.findByOrderItem(orderItem);
                if (OrderType.R_.equals(order.getOrderType())) {
                    wMsServiceBusiness.inStoragePlan(order, goodsList);
                } else {
                    wMsServiceBusiness.outStoragePlan(order, goodsList);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 模拟扫码功能
     *
     * @param orderItem
     */
    public void scanCode(String orderItem) {
        try {
            // 订单下商品明细
            List<OrderDetailGoodsInfoEntity> goodsList = orderDetailGoodsInfoService.findByOrderItem(orderItem);
            //插入扫描IAO总
            List<OrderGoodUniqueCodeScanEntity> goodUniqueCodeScanEntityList = new ArrayList<>(goodsList.size());
            goodsList.forEach(goods -> {
                OrderGoodUniqueCodeScanEntity entity = new OrderGoodUniqueCodeScanEntity();
                entity.setOrderItem(orderItem);
                entity.setGoodCode(goods.getGoodsCode());
                entity.setGoodName(goods.getGoodsName());
                entity.setOperatorDate(new Date());
                entity.setOrderItem(goods.getOrderItem());
                entity.setUniqueCode(goods.getBarCode());
                goodUniqueCodeScanEntityList.add(entity);
            });
            orderGoodUniqueCodeScanService.saveBatch(goodUniqueCodeScanEntityList);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 创建运输单
     *
     * @param orderItem 工作单号
     * @return
     */
    public String createWayBill(String orderItem) {
        // 运输单号
        //判断入库订单还是出库订单
        String wayBillNo = "";
        if (orderItem.startsWith("KSI")) {
            wayBillNo = "TI-" + DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS);
        } else {
            wayBillNo = "TR-" + DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS);
        }

        InPlanLogisticEntity logistic = new InPlanLogisticEntity();
        logistic.setOrderItem(orderItem);
        logistic.setWayBillNo(wayBillNo);
        // 运输信息
        logistic.setCarrierId("1");
        logistic.setCarrierMan("王晓伟");
        logistic.setCarrierMobile("18654729293");
        logistic.setPlateNumber("鲁AXX123");
        logistic.setCarrierCompany("青岛传化公路港物流有限公司");
        logistic.setCarrierName("青岛传化公路港物流有限公司");
        // 配货地址
        logistic.setDeliveryAddress("寿光市侯镇项目区新海路以北大地路以西");

        // 收货地址
        logistic.setReceiveAddress("山东省潍坊市高新区潍坊软件园");
        logistic.setExpectArriveDate(new Date());
        logistic.setExpectPickDate(new Date());

        inPlanLogisticService.add(logistic);
        return wayBillNo;
    }
}
