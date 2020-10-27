package com.platform.modules.iot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.common.OrderStatus;
import com.platform.common.OrderType;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.web.ApiBaseController;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import com.platform.modules.order.entity.OrderGoodUniqueCodeScanEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.entity.OrderTrackInfoEntity;
import com.platform.modules.order.service.*;
import com.platform.modules.wms.entity.InPlanLogisticEntity;
import com.platform.modules.wms.entity.OutPlanLogisticEntity;
import com.platform.modules.wms.service.InPlanLogisticService;
import com.platform.modules.wms.service.OutPlanLogisticService;
import com.platform.modules.wms.service.StoreInventoryInfoService;
import com.platform.modules.wms.service.WmsServiceBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 物联网平台接口
 *
 * @author Mr.panbb
 * @date 2019-09-18
 */
@Api(tags = "IotApi|物联网平台接口")
@RestController
@RequestMapping(value = "/iot")
public class IotApi extends ApiBaseController {
	private Logger logger = LoggerFactory.getLogger(IotApi.class);

	@Autowired
	private InPlanLogisticService inPlanLogisticService;
	@Autowired
	private OutPlanLogisticService outPlanLogisticService;
	@Autowired
	private OrderGoodUniqueCodeScanService orderGoodUniqueCodeScanService;
	@Autowired
	private IotService iotService;
	@Autowired
	private OrderHeaderInfoService orderHeaderInfoService;
	@Autowired
	private OrderDetailGoodsInfoService orderDetailGoodsInfoService;
	@Autowired
	private StoreInventoryInfoService storeInventoryInfoService;
	@Autowired
	private WmsServiceBusiness wmsServiceBusiness;
	@Autowired
	private OrderInterfaceLogService orderInterfaceLogService;
	@Autowired
	private OrderTrackInfoService orderTrackInfoService;

	/**
	 * 入库运输单下发物联网平台
	 *
	 * @param wayBillNo 运输单号
	 * @return Object
	 */
	@ApiOperation(value = "入库运输单下发物联网平台", notes = "测试")
	@GetMapping(value = "/in")
	public RestResponse in(String wayBillNo) {
		iotService.sentInInfo(wayBillNo);
		return RestResponse.success();
	}

	/**
	 * 组织基本信息下发物联网平台
	 *
	 * @return Object
	 */
	@ApiOperation(value = "组织基本信息下发物联网平台", notes = "测试")
	@GetMapping(value = "/sendOrgInfo")
	public RestResponse sendOrgInfo() {
		if (iotService.sendOrgInfo()) {
			return RestResponse.success();
		}
		return RestResponse.error();
	}

	/**
	 * 仓库基本信息下发物联网平台
	 *
	 * @return Object
	 */
	@ApiOperation(value = "仓库基本信息下发物联网平台", notes = "测试")
	@GetMapping(value = "/sendStoreInfo")
	public RestResponse sendStoreInfo() {
		if (iotService.sendStoreInfo()) {
			return RestResponse.success();
		}
		return RestResponse.error();
	}

	/**
	 * 产品基本信息下发物联网平台
	 *
	 * @return Object
	 */
	@ApiOperation(value = "产品基本信息下发物联网平台", notes = "测试")
	@GetMapping(value = "/sendGoodsInfo")
	public RestResponse sendGoodsInfo() {
		if (iotService.sendGoodsInfo()) {
			return RestResponse.success();
		}
		return RestResponse.error();
	}

	/**
	 * 用户基本信息下发物联网平台
	 *
	 * @return Object
	 */
	@ApiOperation(value = "用户基本信息下发物联网平台", notes = "测试")
	@GetMapping(value = "/sendUserInfo")
	public RestResponse sendUserInfo() {
		if (iotService.sendUserInfo()) {
			return RestResponse.success();
		}
		return RestResponse.error();
	}

	/**
	 * 接收收货信息
	 *
	 * @param params 请求参数
	 * @return Object
	 */
	@ApiOperation(value = "接收收货信息", notes = "接口")
	@RequestMapping(value = "/receiveStatusForGoods")
	public RestResponse receiveStatusForGoods(@RequestBody JSONObject params) {
		// 判断入库订单状态
		logger.error("[receiveStatusForGoods]: " + JSON.toJSONString(params));
		try {
			String orderItem = getOrderItem(params.getString("wayBillNo"));
			if (StringUtils.isBlank(orderItem)) {
				return RestResponse.error("请检查传入参数准确性");
			}
			if (!checkOrderStatus(orderItem, OrderStatus.InTiHuo.getCode())) {
				return RestResponse.error("订单状态错误");
			}
			// 在途、到达工厂、确认提货、返程
			OrderStatus[] array = {OrderStatus.InOnWay, OrderStatus.InFactory, OrderStatus.InConfrimTake, OrderStatus.InBack};
			doReceive(orderItem, params, array, "1");
			if (StringUtils.isBlank(orderItem)) {
				return RestResponse.error("请检查传入参数准确性");
			}
			return RestResponse.success();
		} catch (Exception e) {
			logger.error("receiveStatusForGoods", e);
			return RestResponse.error("请检查传入参数准确性");
		}
	}

	/**
	 * 接收商品入库信息
	 *
	 * @param params 请求参数
	 * @return Object
	 */
	@ApiOperation(value = "接收商品入库信息", notes = "接口")
	@RequestMapping(value = "/receiveStatusForStore")
	public RestResponse receiveStatusForStore(@RequestBody JSONObject params) {
		logger.error("[receiveStatusForStore]: " + JSON.toJSONString(params));
		try {
			String orderItem = getOrderItem(params.getString("wayBillNo"));
			if (StringUtils.isBlank(orderItem)) {
				return RestResponse.error("请检查传入参数准确性");
			}
			if (!checkOrderStatus(orderItem, OrderStatus.InBack.getCode())) {
				return RestResponse.error("订单状态错误");
			}
			// 到达仓库、扫码确认、入库、完成
			OrderStatus[] array = {OrderStatus.InOnStorage, OrderStatus.InConfirmScan,
					OrderStatus.InToStorage, OrderStatus.InComplete};
			doReceive(orderItem, params, array, "2");
			// 更新库存
			freshStore(orderItem);
			return RestResponse.success();
		} catch (Exception e) {
			logger.error("receiveStatusForStore", e);
			return RestResponse.error("请检查传入参数准确性");
		}
	}

	/**
	 * 出库扫码
	 *
	 * @param params 请求参数
	 * @return Object
	 */
	@ApiOperation(value = "出库扫码", notes = "接口")
	@RequestMapping(value = "/receiveStatusForOutStore")
	public RestResponse receiveStatusForOutStore(@RequestBody JSONObject params) {
		logger.error("[receiveStatusForOutStore]: " + JSON.toJSONString(params));
		try {
			String orderItem = getOrderItem(params.getString("wayBillNo"));
			if (StringUtils.isBlank(orderItem)) {
				return RestResponse.error("请检查传入参数准确性");
			}
			if (!checkOrderStatus(orderItem, OrderStatus.OutChuStorage.getCode())) {
				return RestResponse.error("订单状态错误");
			}
			// 扫码出库、装货、确认提货、送货
			OrderStatus[] array = {OrderStatus.OutScanOut, OrderStatus.OutZhuanghuo,
					OrderStatus.OutConfrimTake, OrderStatus.OutDelivery};
			doReceive(orderItem, params, array, "3");

			// 更新库存
			freshStore(orderItem);
			return RestResponse.success();
		} catch (Exception e) {
			logger.error("receiveStatusForOutStore", e);
			return RestResponse.error("请检查传入参数准确性");
		}
	}

	/**
	 * 确认收货
	 *
	 * @param params 参数
	 * @return Object
	 */
	@ApiOperation(value = "确认收货", notes = "接口")
	@RequestMapping(value = "/receiveStatusForReceipt")
	public RestResponse receiveStatusForReceipt(@RequestBody JSONObject params) {
		logger.error("[receiveStatusForReceipt]: " + JSON.toJSONString(params));
		try {
			String orderItem = getOrderItem(params.getString("wayBillNo"));
			if (StringUtils.isBlank(orderItem)) {
				return RestResponse.error("请检查传入参数准确性");
			}
			if (!checkOrderStatus(orderItem, OrderStatus.OutDelivery.getCode())) {
				return RestResponse.error("订单状态错误");
			}
			// 到达、卸货、扫码确认、签收确认、完成
			OrderStatus[] array = {OrderStatus.OutArrive, OrderStatus.OutUnload, OrderStatus.OutConfirmScan,
					OrderStatus.OutConfirmSign, OrderStatus.OutComplete};
			doReceive(orderItem, params, array, "4");
			return RestResponse.success();
		} catch (Exception e) {
			logger.error("receiveStatusForOutStore", e);
			return RestResponse.error("请检查传入参数准确性");
		}
	}

	/**
	 * 保存追踪宝与订单绑定
	 *
	 * @param trackInfo
	 * @return
	 */
	@RequestMapping("/stackInfo/add")
	public RestResponse saveStackInfo(@RequestBody OrderTrackInfoEntity trackInfo) {
		logger.error("[receiveStatusForReceipt]: " + JSON.toJSONString(trackInfo));
		try {
			//验证该订单是否存在
			if (orderHeaderInfoService.getByOrderItem(trackInfo.getOrderItem()) == null) {
				return RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "订单" + trackInfo.getOrderItem() + "不存在");
			}
			if (StringUtils.isEmpty(trackInfo.getDeviceCode())) {
				return RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "绑定设备的编号不能为空");
			}
			OrderTrackInfoEntity info = orderTrackInfoService.getByOrderItem(trackInfo.getOrderItem());
			if (info == null) {
				if (trackInfo.getStartTime() == null) {
					return RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "初次绑定设备时开始时间不能为空");
				}
				trackInfo.setCreatedTime(new Date());
				// 插入新的记录
				orderTrackInfoService.add(trackInfo);
			} else {
				if (trackInfo.getStartTime() == null || trackInfo.getEndTime() == null) {
					return RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "再次绑定设备时开始时间或结束时间不能为空");
				}
				// 更新记录
				info.setDeviceCode(trackInfo.getDeviceCode());
				info.setStartTime(trackInfo.getStartTime());
				info.setEndTime(trackInfo.getEndTime());
				info.setUpdatedTime(new Date());
				orderTrackInfoService.update(info);
			}
			return RestResponse.success("操作成功");
		} catch (Exception e) {
			logger.error("", e);
		}
		return RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "操作失败");
	}

	/**
	 * 检查订单状态
	 *
	 * @param orderItem  工作单号
	 * @param orderSatus 订单状态
	 * @return 与传入的订单状态一致返回true，否则返回false
	 */
	private boolean checkOrderStatus(String orderItem, String orderSatus) {
		// 1、根据工作单号获取订单信息
		// 2、拿到订单状态进行判断
		if (orderHeaderInfoService.getByOrderItem(orderItem).getStatus().equals(orderSatus)) {
			// 与传入的订单状态一致返回true
			return true;
		}
		// 不一致 返回false
		return false;
	}

	/**
	 * 处理接收的请求
	 *
	 * @param orderItem   工作单号
	 * @param params      请求参数
	 * @param statusArr   对应订单状态集合
	 * @param operateType 操作类型
	 * @return 工作单号
	 */
	private String doReceive(String orderItem, JSONObject params, OrderStatus[] statusArr, String operateType) {
		try {
			//条形码、仓位
			JSONArray codeArr = params.getJSONArray("barCodeList");
			//更新时间
			Date updateTime = params.getDate("time");
			// 保存请求
			orderInterfaceLogService.saveLog(orderItem, null, params.toJSONString(), null, "2");
			if (codeArr != null) {
				//保存条形码
				saveBarCode(orderItem, operateType, updateTime, codeArr);
				// 更新入库条码对应的状态
				updateBarCode(orderItem, codeArr);
			}
			// 更新订单状态，插入状态记录
			for (int i = 0; i < statusArr.length; i++) {
				updateOrderStatus(orderItem, statusArr[i].getCode(), statusArr[i].getStatusName(), updateTime);
			}
			return orderItem;
		} catch (Exception e) {
			logger.error("receiveStatusForOutStore", e);
			return null;
		}
	}

	/**
	 * 更新订单状态
	 *
	 * @param orderItem  工作单号
	 * @param code       状态编号
	 * @param name       状态名称
	 * @param updateTime 更新时间
	 */
	private void updateOrderStatus(String orderItem, String code, String name, Date updateTime) {
		OrderHeaderInfoEntity order = new OrderHeaderInfoEntity();
		order.setOrderItem(orderItem);
		order.setStatus(code);
		order.setStatusName(name);
		order.setUpdatedTime(updateTime);
		orderHeaderInfoService.changeStatus(order);
	}

	/**
	 * 根据运输单号获取订单号
	 *
	 * @param wayBillNo 运输单号
	 * @return 订单编号
	 */
	private String getOrderItem(String wayBillNo) {
		//订单号
		String orderItem = null;
		if (wayBillNo.startsWith("TR-")) {
			// 入库单
			List<InPlanLogisticEntity> list = inPlanLogisticService.findByWayBillNo(wayBillNo);
			if (CollectionUtils.isNotEmpty(list)) {
				orderItem = list.get(0).getOrderItem();
			}
		} else {
			// 出库单
			List<OutPlanLogisticEntity> list = outPlanLogisticService.findByWayBillNo(wayBillNo);
			if (CollectionUtils.isNotEmpty(list)) {
				orderItem = list.get(0).getOrderItem();
			}
		}
		return orderItem;
	}

	/**
	 * 保存条码库位信息
	 *
	 * @param orderItem   工作单号
	 * @param operateType 操作类型
	 * @param time        时间
	 * @param codeArr     条码、仓位数据
	 */
	private void saveBarCode(String orderItem, String operateType, Date time, JSONArray codeArr) {
		// 订单信息
		OrderHeaderInfoEntity order = orderHeaderInfoService.getByOrderItem(orderItem);
		//条形码集合
		List<OrderGoodUniqueCodeScanEntity> barCodeList = new ArrayList<>(codeArr.size());
		for (int i = 0; i < codeArr.size(); i++) {
			JSONObject obj = codeArr.getJSONObject(i);
			//组装数据
			OrderGoodUniqueCodeScanEntity barCode = new OrderGoodUniqueCodeScanEntity();
			barCode.setOrderItem(orderItem);
			barCode.setOperateType(operateType);
			barCode.setOperatorDate(time);
			barCode.setStoreHouse(obj.getString("space"));
			barCode.setUniqueCode(obj.getString("code"));
			barCode.setGoodCode(obj.getString("goodsCode"));
			barCode.setGoodName(obj.getString("goodsName"));
			barCode.setStoreCode(order.getStoreHouseCode());
			// 入库状态
			barCode.setIsInStore("1");
			barCode.setCreatedTime(new Date());
			//添加到条形码集合中
			barCodeList.add(barCode);
		}
		//保存条形码
		orderGoodUniqueCodeScanService.saveBatch(barCodeList);
	}

	/**
	 * 扫码出库时更新入库信息的出库状态
	 * @param orderItem 工作单号
	 * @param codeArr   条码列表
	 */
	private void updateBarCode(String orderItem, JSONArray codeArr) {
		for (int i = 0; i < codeArr.size(); i++) {
			JSONObject obj = codeArr.getJSONObject(i);
			// 条码
			String barCode = obj.getString("code");
			// 产品编码
			String goodsCode = obj.getString("goodsCode");
			// 更新商品出库状态
			orderGoodUniqueCodeScanService.updateBarCode(orderItem, barCode, goodsCode);
		}
	}

	/**
	 * 出入库更新库存
	 *
	 * @param orderItem 工作单号
	 */
	private void freshStore(String orderItem) {
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
					wmsServiceBusiness.inStoragePlan(order, goodsList);
				} else {
					wmsServiceBusiness.outStoragePlan(order, goodsList);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
