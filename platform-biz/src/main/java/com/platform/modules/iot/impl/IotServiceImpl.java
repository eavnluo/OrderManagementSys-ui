package com.platform.modules.iot.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.common.OrderType;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.HttpUtils;
import com.platform.common.utils.StringUtils;
import com.platform.modules.base.service.BaseCarInfoService;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseGoodsInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.iot.IotService;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.service.OrderDetailGoodsInfoService;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.order.service.OrderInterfaceLogService;
import com.platform.modules.wms.entity.InPlanLogisticEntity;
import com.platform.modules.wms.entity.OutPlanLogisticEntity;
import com.platform.modules.wms.service.InPlanLogisticService;
import com.platform.modules.wms.service.OutPlanLogisticService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 物联网平台接口实现
 *
 * @author Mr.panbb
 * @date 2019-09-18
 */
@Service("iotService")
public class IotServiceImpl implements IotService {
	private Logger logger = LoggerFactory.getLogger(IotServiceImpl.class);
	/**
	 * 信息下发接口地址
	 */
	private final static String POST_URL = "http://sw.dev.fusionfintrade.com/pmpb/services/common/api/transport/dispatch";
	/**
	 * 组织信息下发接口
	 */
	private final static String ORG_URL = "http://sw.dev.fusionfintrade.com/pmpb/services/common/api/organize/saveupdate";
	/**
	 * 仓库信息下发接口
	 */
	private final static String STORE_URL = "http://sw.dev.fusionfintrade.com/pmpb/services/common/api/warehouse/saveupdate";
	/**
	 * 商品信息下发接口
	 */
	private final static String GOODS_URL = "http://sw.dev.fusionfintrade.com/pmpb/services/common/api/assetspec/saveupdate";
	/**
	 * 用户信息下发接口
	 */
	private final static String USER_URL = "http://sw.dev.fusionfintrade.com/pmpb/services/common/api/user/saveupdate";

	/**
	 * 出入库单同步
	 */
	private final static String ORDER_SYN_URL = "http://sw.dev.fusionfintrade.com/pmpb/services/common/api/order/warehouse/entry/save";


	@Autowired
	private OrderDetailGoodsInfoService orderDetailGoodsInfoService;
	@Autowired
	private InPlanLogisticService inPlanLogisticService;
	@Autowired
	private OutPlanLogisticService outPlanLogisticService;
	@Autowired
	private BaseSupplyInfoService baseSupplyInfoService;
	@Autowired
	private BaseCustomerInfoService baseCustomerInfoService;
	@Autowired
	private BaseGoodsInfoService baseGoodsInfoService;
	@Autowired
	private BaseCarInfoService baseCarInfoService;
	@Autowired
	private OrderHeaderInfoService orderHeaderInfoService;
	@Autowired
	private OrderInterfaceLogService orderInterfaceLogService;


	/**
	 * 下发运输入库运输信息
	 *
	 * @param wayBillNo 运输单号
	 * @return true 下发成功，false 下发失败
	 */
	@Override
	public boolean sentInInfo(String wayBillNo) {
		List<InPlanLogisticEntity> list = inPlanLogisticService.findByWayBillNo(wayBillNo);
		boolean flag = false;
		if (CollectionUtils.isNotEmpty(list)) {
			InPlanLogisticEntity logistic = list.get(0);
			OrderHeaderInfoEntity order = orderHeaderInfoService.getByOrderItem(logistic.getOrderItem());
			JSONObject params = mockData(order, logistic);
			flag = doSend(POST_URL, params.toJSONString(), order.getOrderItem());
		}
		return flag;
	}

	/**
	 * 下发出库运输信息
	 *
	 * @param wayBillNo 运输单号
	 * @return true 下发成功，false 下发失败
	 */
	@Override
	public boolean sendOutInfo(String wayBillNo) {
		boolean flag = false;
		List<OutPlanLogisticEntity> list = outPlanLogisticService.findByWayBillNo(wayBillNo);
		if (list != null && list.size() > 0) {
			OutPlanLogisticEntity logistic = list.get(0);
			OrderHeaderInfoEntity order = orderHeaderInfoService.getByOrderItem(logistic.getOrderItem());
			JSONObject params = mockData(order, logistic);
			flag = doSend(POST_URL, params.toJSONString(), order.getOrderItem());
		}
		return flag;
	}

	/**
	 * 构造下发的运输信息
	 * @param order 订单信息
	 * @param logistic 运输信息
	 * @return Object
	 */
	public JSONObject mockData(OrderHeaderInfoEntity order, Object logistic) {
		JSONObject params = new JSONObject();
		// 货单列表
		JSONArray goods = new JSONArray();
		JSONObject bill = new JSONObject();
		//出入库类型
		params.put("orderType", order.getOrderItem().startsWith("KSI") ? 1 : 0);
		params.put("startDate", new Date());
		params.put("endDate", DateUtils.addDateDays(new Date(), 2));
		bill.put("orderNo", order.getOrderNo());
		bill.put("goodsOrderNo", order.getDynamic());
		bill.put("acceptionConfirm", 1);
		bill.put("deliveryConfirm", 1);
		if (logistic instanceof InPlanLogisticEntity) {
			// 入库
			InPlanLogisticEntity inPlanLogisticEntity = (InPlanLogisticEntity) logistic;
			params.put("srcAddressCode", order.getSellerCode());
			params.put("destAddressCode", order.getStoreHouseCode());
			params.put("vehicle", inPlanLogisticEntity.getPlateNumber());
			params.put("carrierMan", inPlanLogisticEntity.getCarrierMan());
			params.put("carrierMobile", inPlanLogisticEntity.getCarrierMobile());
			params.put("transportNo", inPlanLogisticEntity.getWayBillNo());
			bill.put("sellOrgCode", order.getSellerCode());
			bill.put("buyOrgCode", order.getBuyerCode());
			bill.put("srcAddressCode", order.getSellerCode());
			bill.put("destAddressCode", order.getStoreHouseCode());
			bill.put("sellLinkMan", inPlanLogisticEntity.getDeliveryName());
			bill.put("sellLinkPhone", inPlanLogisticEntity.getDeliveryMobile());
			bill.put("buyLinkMan", inPlanLogisticEntity.getConsignee());
			bill.put("bueLinkPhone", inPlanLogisticEntity.getReceiveMobile());
		} else if (logistic instanceof OutPlanLogisticEntity) {
			// 出库
			OutPlanLogisticEntity outPlanLogisticEntity = (OutPlanLogisticEntity) logistic;
			params.put("srcAddressCode", order.getStoreHouseCode());
			params.put("destAddressCode", order.getBuyerCode());
			params.put("vehicle", outPlanLogisticEntity.getPlateNumber());
			params.put("carrierMan", outPlanLogisticEntity.getCarrierMan());
			params.put("carrierMobile", outPlanLogisticEntity.getCarrierMobile());
			params.put("transportNo", outPlanLogisticEntity.getWayBillNo());
			bill.put("sellOrgCode", order.getSellerCode());
			bill.put("buyOrgCode", order.getBuyerCode());
			bill.put("srcAddressCode", order.getStoreHouseCode());
			bill.put("destAddressCode", order.getBuyerCode());
			bill.put("sellLinkMan", outPlanLogisticEntity.getDeliveryName());
			bill.put("sellLinkPhone", outPlanLogisticEntity.getDeliveryMobile());
			bill.put("buyLinkMan", outPlanLogisticEntity.getConsignee());
			bill.put("bueLinkPhone", outPlanLogisticEntity.getReceiveMobile());
		}
		// 商品
		JSONArray itemList = new JSONArray();
		// 根据工作单号获取订单包含的商品列表
		List<OrderDetailGoodsInfoEntity> goodsList = orderDetailGoodsInfoService.findByOrderItem(order.getOrderItem());
		if (CollectionUtils.isNotEmpty(goodsList)) {
			for (OrderDetailGoodsInfoEntity g : goodsList) {
				JSONObject item = new JSONObject();
				item.put("spec", g.getGoodsCode());
				item.put("amount", g.getGoodsNum());
				item.put("unit", "件");
				if (StringUtils.isNotBlank(g.getGoodsCode())) {
					item.put("codes", g.getGoodsCode().split(","));
				} else {
					item.put("codes", new JSONArray());
				}
				itemList.add(item);
			}
			bill.put("item", itemList);
		} else {
			logger.error("[工作单编号]： " + order.getOrderItem() + ", 未查出相应的产品信息");
		}
		goods.add(bill);
		params.put("goods", goods);
		return params;
	}

	/**
	 * 调用接口发送消息
	 *
	 * @param url        请求地址
	 * @param params     数据
	 * @param orderItem  工作单号
	 * @return true      下发成功，false 下发失败
	 */
	public boolean doSend(String url, String params, String orderItem) {
		logger.error("请求地址：" + url);
		logger.error("请求参数：" + params);
		JSONObject headerParams = new JSONObject();
		headerParams.put("app-id", "10000000");
		String msg = HttpUtils.sendPost(url, params, headerParams);
		logger.error("返回结果：" + msg);
		//保存接口调用的日志信息
		orderInterfaceLogService.saveLog(orderItem, url, params,msg, "1");
		if (StringUtils.isNotBlank(msg)) {
			JSONObject data = JSONObject.parseObject(msg);
			if (data.getIntValue("code") != 0) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 下发组织基本信息
	 *
	 * @return
	 */
	@Override
	public boolean sendOrgInfo() {
		JSONArray params = new JSONArray();
		// 获取卡车、库房
		JSONArray a1 = baseSupplyInfoService.findFormatList();
		// 采购商、分销商、工厂
		JSONArray a2 = baseCustomerInfoService.findFormatList();

		params.addAll(a1);
		params.addAll(a2);
		return doSend(ORG_URL, params.toJSONString(), "");
	}

	/**
	 * 下发仓库基本信息
	 *
	 * @return
	 */
	@Override
	public boolean sendStoreInfo() {
		JSONArray params = new JSONArray();
		JSONArray a1 = baseSupplyInfoService.findFormatStoreList();
		JSONArray a2 = baseCustomerInfoService.findFormatStoreList();
		params.addAll(a1);
		params.addAll(a2);
		return doSend(STORE_URL, params.toJSONString(), "");
	}

	/**
	 * 下发产品基本信息
	 *
	 * @return
	 */
	@Override
	public boolean sendGoodsInfo() {
		JSONArray params = baseGoodsInfoService.findFormatList();
		return doSend(GOODS_URL, params.toJSONString(), "");
	}

	/**
	 * 下发用户基本信息
	 *
	 * @return
	 */
	@Override
	public boolean sendUserInfo() {
		JSONArray params = baseCarInfoService.findFormatList();
		return doSend(USER_URL, params.toJSONString(), "");
	}

	@Override
	public boolean orderSyn(OrderHeaderInfoEntity order) {
		List<OrderDetailGoodsInfoEntity> goodsList = orderDetailGoodsInfoService.listByOrderItem(order.getOrderItem());
		JSONObject params = assemData(order, goodsList);
		return doSend(ORDER_SYN_URL, params.toJSONString(), order.getOrderItem());
	}

	/**
	 * 组装数据
	 *
	 * @return
	 */
	private JSONObject assemData(OrderHeaderInfoEntity order, List<OrderDetailGoodsInfoEntity> goodsInfoEntityList) {
		JSONObject params = new JSONObject();
		JSONArray array = new JSONArray();
		params.put("orderType", OrderType.I_.equals(order.getOrderType()) ? 1 : 0);
		params.put("entryOrderNo", order.getOrderItem());
		params.put("orderNo", order.getOrderNo());
		params.put("invoiceNo", order.getInvoiceNo());
		params.put("sellOrgCode", order.getSellerCode());
		params.put("buyOrgCode", order.getBuyerCode());
		if ("I".equals(order.getOrderType())) {
			//出库
			//卖方仓库代码
			params.put("sellWareHouseCode", order.getStoreHouseCode());
			//买方仓库代码
			params.put("buyWarehouseCode", "");
		} else {
			//入库
			//卖方仓库代码
			params.put("sellWareHouseCode", "");
			//买方仓库代码
			params.put("buyWarehouseCode", order.getStoreHouseCode());
		}
		params.put("orderDate", order.getCreatedTime());
		for (OrderDetailGoodsInfoEntity goodsInfoEntity : goodsInfoEntityList) {
			JSONObject item = new JSONObject();
			item.put("spec", goodsInfoEntity.getGoodsCode());
			item.put("unit", "件");
			item.put("amount", goodsInfoEntity.getGoodsNum());
			array.add(item);
		}
		params.put("item", array);
		return params;
	}
}
