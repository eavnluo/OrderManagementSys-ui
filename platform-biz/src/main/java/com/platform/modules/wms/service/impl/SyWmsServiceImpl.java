package com.platform.modules.wms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.platform.common.OrderType;
import com.platform.common.utils.HttpUtils;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.service.OrderDetailGoodsInfoService;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.order.service.OrderInterfaceLogService;
import com.platform.modules.wms.service.SyWmsService;
import com.platform.modules.wms.utils.MockDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SY-WMS接口实现
 *
 * @author Mr.panbb
 * @date 2019-09-20
 */
@Service("syWmsService")
public class SyWmsServiceImpl implements SyWmsService {

    private Logger logger = LoggerFactory.getLogger(SyWmsServiceImpl.class);

    @Value("${sy-wms.base-url}")
    private String baseUrl;
    @Value("${sy-wms.name}")
    private String name;
    @Value("${sy-wms.token}")
    private String token;

    @Autowired
    private OrderHeaderInfoService orderHeaderInfoService;
    @Autowired
    private OrderDetailGoodsInfoService orderDetailGoodsInfoService;
    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;
    @Autowired
    private BaseSupplyInfoService baseSupplyInfoService;
    @Autowired
    private OrderInterfaceLogService orderInterfaceLogService;

    /**
     * 将订单下发到仓库
     *
     * @param orderItem 工作单号
     */
    @Override
    public void sendOrder(String orderItem) {
        // 接口地址
        String api;
        // 请求参数
        JSONObject params;
        OrderHeaderInfoEntity order = orderHeaderInfoService.getByOrderItem(orderItem);
        List<OrderDetailGoodsInfoEntity> goodsList = orderDetailGoodsInfoService.findByOrderItem(orderItem);
        //货主信息
        BaseCustomerInfoEntity buyer = baseCustomerInfoService.findByCode(order.getBuyerCode());
        //仓库信息
        BaseSupplyInfoEntity store = baseSupplyInfoService.getOne(new QueryWrapper<BaseSupplyInfoEntity>().eq("SUPPLY_CODE", order.getStoreHouseCode()));
        //卡车供应商信息
        BaseSupplyInfoEntity carSupply = baseSupplyInfoService.getOne(new QueryWrapper<BaseSupplyInfoEntity>().eq("SUPPLY_CODE", order.getSupplyCode()));
        if (OrderType.R_.equals(order.getOrderType())) {
            // 入库订单
            api = "/api/Purchase_Order/Add";
            params = MockDataUtil.inOrderData(order, goodsList, buyer, store, carSupply);
        } else {
            //仓库信息
            // 出库订单
            api = "/api/Sale_Order/Add";
            params = MockDataUtil.outOrderData(order, goodsList, store);
        }

        // 请求地址
        String requestUrl = baseUrl + api + "?name=" + name + "&token=" + token;
        String rData = HttpUtils.sendPost(requestUrl, params.toJSONString(), null);
        //保存接口调用日志信息
        orderInterfaceLogService.saveLog(orderItem,requestUrl,params.toJSONString(),rData,"0");
        //将返回json字符串转换成json对象
        JSONObject obj = JSONObject.parseObject(rData);
        //保存返回的"Dynamic":"PO20191023004"
        if (obj != null && obj.getString("Dynamic") != null) {
            orderHeaderInfoService.update(new UpdateWrapper<OrderHeaderInfoEntity>().set("DYNAMIC", obj.getString("Dynamic")).eq("ORDER_ITEM", orderItem));
        } else if (obj != null && obj.getJSONObject("Data") != null) {
            orderHeaderInfoService.update(new UpdateWrapper<OrderHeaderInfoEntity>().set("DYNAMIC", obj.getJSONObject("Data").getString("OrderCode")).eq("ORDER_ITEM", orderItem));
        }
    }
}
