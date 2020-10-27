package com.platform.modules.home;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.OrderType;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseGoodsInfoEntity;
import com.platform.modules.base.service.BaseGoodsInfoService;
import com.platform.modules.order.service.OrderGoodUniqueCodeScanService;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 首页数据展示接口
 * @author Mr.panbb
 * @date 2019-12-26
 */
@RestController
@RequestMapping("/home/chart")
public class HomeChartsController extends AbstractController {

	@Autowired
	private OrderHeaderInfoService orderHeaderInfoService;
	@Autowired
	private OrderGoodUniqueCodeScanService orderGoodUniqueCodeScanService;
	@Autowired
	private BaseGoodsInfoService baseGoodsInfoService;

	/**
	 * 获取本月各仓库订单数目
	 * @return
	 */
	@GetMapping("/countOrderNumber")
	public RestResponse countOrderNumberEveryMonth() {
		List<Map<String, Object>> list = orderHeaderInfoService.countStoreOrderEveryMonth();
		List<Object> nameList = new ArrayList<>();
		List<Object> numList = new ArrayList<>();
		list.forEach(m -> {
			nameList.add(m.get("name"));
			numList.add(m.get("value"));
		});
		JSONObject data = new JSONObject();
		data.put("nameList", nameList);
		data.put("numList", numList);
		return RestResponse.success().put("data", data);
	}

	/**
	 * 获取过去一段时间中，每天对应的天数
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	@GetMapping("/getByOrderCount")
	public RestResponse getByOrderCount(String startTime, String endTime){
		try {
			List<Map<String,Object>> outOrderList = orderHeaderInfoService.getByOrderCount(OrderType.I_, startTime, endTime);
			List<Map<String,Object>> inOrderList = orderHeaderInfoService.getByOrderCount(OrderType.R_, startTime, endTime);
			Map<String, Object[]> map = new LinkedHashMap<>(outOrderList.size());
			outOrderList.forEach(o -> {
				Object[] array = new Object[2];
				array[0] = o.get("count");
				array[1] = 0;
				map.put(o.get("days").toString(), array);
			});
			inOrderList.forEach(i -> {
				Object[] array = map.get(i.get("days").toString());
				if (array == null) {
					array = new Object[2];
					array[0] = 0;
					map.put(i.get("days").toString(), array);
				}
				array[1] = i.get("count");
			});
			// 按照日期顺序排序
			List<Map.Entry<String, Object[]>> list = new ArrayList<>(map.entrySet());
			Collections.sort(list, Comparator.comparing(Map.Entry::getKey));
			map.clear();
			list.forEach(e -> map.put(e.getKey(), e.getValue()));
			return RestResponse.success().put("data", map);
		} catch (Exception e) {
			logger.error("getByOrderCount", e);
		}
		return RestResponse.error();
	}

	/**
	 * 获取首页表格数据
	 * @return Table data
	 */
	@GetMapping("/findTableData")
	public RestResponse findTableData() {
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			Date currentDate = new Date();
			// 订单
			list.add(orderNum("订单总数", currentDate, null));
			list.add(orderNum("出库订单", currentDate, OrderType.I_));
			list.add(orderNum("入库订单", currentDate, OrderType.R_));
			// 库存
			// 当日库存
			List<Map<String, Object>> dayList = orderGoodUniqueCodeScanService.findList(DateUtils.format(currentDate), "%Y-%m-%d");
			// 当月库存
			List<Map<String, Object>> monthList = orderGoodUniqueCodeScanService.findList(DateUtils.format(currentDate), "%Y-%m-%d");
			// 上月库存
			List<Map<String, Object>> lastMonthList = orderGoodUniqueCodeScanService.findList(DateUtils.format(DateUtils.addDateMonths(currentDate, -1)), "%Y-%m-%d");
			// 产品基础信息
			List<BaseGoodsInfoEntity> goodsList = baseGoodsInfoService.findGoodsList();
			list.add(storeGoods(dayList, monthList, lastMonthList));
			list.add(storeGoodsType(dayList, monthList, lastMonthList, goodsList));
			list.add(storeGoodsPrice(dayList, monthList, lastMonthList, goodsList));
		} catch (Exception e) {
			logger.error("[findTableData]", e);
		}
		return RestResponse.success().put("data", list);
	}

	/**
	 * 订单数量
	 * @param title 统计项名称
	 * @param currentDate 当前时间
	 * @param orderType 订单类型
	 * @return map
	 */
	public Map<String, Object> orderNum(String title, Date currentDate, String orderType) {
		Map<String, Object> map = new HashMap<>(5);
		try {
			map.put("itemName", title);
			int dayValue = orderHeaderInfoService.getNum(DateUtils.format(currentDate), "%Y-%m-%d", orderType);
			map.put("dayValue", dayValue);
			int monthValue = orderHeaderInfoService.getNum(DateUtils.format(currentDate,"yyyy-MM"), "%Y-%m", orderType);
			map.put("monthValue", monthValue);
			int lastMonthValue = orderHeaderInfoService.getNum(DateUtils.format(DateUtils.addDateMonths(currentDate, -1), "yyyy-MM"), "%Y-%m", orderType);
			map.put("lastMonthValue", lastMonthValue);
			calculateMom(map, monthValue, lastMonthValue);
		} catch (Exception e) {
			logger.error("[orderNum]", e);
		}
		return map;
	}

	/**
	 * 库存商品总数
	 * @param dayList 当日库存列表
	 * @param monthList 当月库存列表
	 * @param lastMonthList 上月库存列表
	 * @return map
	 */
	public Map<String, Object> storeGoods(List<Map<String, Object>> dayList,
										  List<Map<String, Object>> monthList,
										  List<Map<String, Object>> lastMonthList) {
		Map<String, Object> map = new HashMap<>(5);
		try {
			map.put("itemName", "库存商品总数");
			int dayValue = 0;
			for (Map<String, Object> m : dayList) {
				dayValue += Integer.parseInt(m.get("num").toString());
			}
			map.put("dayValue", dayValue);
			int monthValue = 0;
			for (Map<String, Object> m : monthList) {
				monthValue += Integer.parseInt(m.get("num").toString());
			}
			map.put("monthValue", monthValue);
			int lastMonthValue = 0;
			for (Map<String, Object> m : lastMonthList) {
				lastMonthValue += Integer.parseInt(m.get("num").toString());
			}
			map.put("lastMonthValue", lastMonthValue);
			calculateMom(map, monthValue, lastMonthValue);
		} catch (Exception e) {
			logger.error("[storeGoods]", e);
		}
		return map;
	}

	/**
	 * 库存商品类型
	 * @param dayList 当日库存列表
	 * @param monthList 当月库存列表
	 * @param lastMonthList 上月库存列表
	 * @param goodsList 商品基础信息列表
	 * @return map
	 */
	public Map<String, Object> storeGoodsType(List<Map<String, Object>> dayList,
											  List<Map<String, Object>> monthList,
											  List<Map<String, Object>> lastMonthList,
											  List<BaseGoodsInfoEntity> goodsList) {
		Map<String, Object> map = new HashMap<>(5);
		try {
			Map<String, String> goodsTypeMap = new HashMap<>(6);
			for (BaseGoodsInfoEntity goods : goodsList) {
				goodsTypeMap.put(goods.getGoodsCode(), goods.getCategoryCode());
			}
			// 当日
			Map<String, Integer> typeMap = new HashMap<>(6);
			map.put("itemName", "库存商品类型");
			for(Map<String, Object> m : dayList) {
				typeMap.put(goodsTypeMap.get(m.get("goodsCode").toString()), 1);
			}
			map.put("dayValue", typeMap.size());
			// 当月
			typeMap.clear();
			for(Map<String, Object> m : monthList) {
				typeMap.put(goodsTypeMap.get(m.get("goodsCode").toString()), 1);
			}
			int monthValue = typeMap.size();
			map.put("monthValue", monthValue);
			// 上月
			typeMap.clear();
			for(Map<String, Object> m : lastMonthList) {
				typeMap.put(goodsTypeMap.get(m.get("goodsCode").toString()), 1);
			}
			int lastMonthValue = typeMap.size();
			map.put("lastMonthValue", lastMonthValue);
			calculateMom(map, monthValue, lastMonthValue);
		} catch (Exception e) {
			logger.error("[storeGoodsType]", e);
		}
		return map;
	}

	/**
	 * 库存商品总额(RMB)
	 * @param dayList 当日库存列表
	 * @param monthList 当月库存列表
	 * @param lastMonthList 上月库存列表
	 * @param goodsList 商品基础信息列表
	 * @return map
	 */
	public Map<String, Object> storeGoodsPrice(List<Map<String, Object>> dayList,
											   List<Map<String, Object>> monthList,
											   List<Map<String, Object>> lastMonthList,
											   List<BaseGoodsInfoEntity> goodsList) {
		Map<String, Object> map = new HashMap<>(5);
		try {
			Map<String, Double> goodsPriceMap = new HashMap<>(6);
			for (BaseGoodsInfoEntity goods : goodsList) {
				if (goods.getPrice() != null) {
					goodsPriceMap.put(goods.getGoodsCode(), goods.getPrice().doubleValue());
				}
			}
			map.put("itemName", "库存商品总额(RMB)");
			// 当日
			double dayValue = 0D;
			for(Map<String, Object> m : dayList) {
				if (goodsPriceMap.get(m.get("goodsCode").toString()) != null) {
					// 数量*单价
					dayValue += Integer.parseInt(m.get("num").toString()) * goodsPriceMap.get(m.get("goodsCode").toString());
				}
			}
			map.put("dayValue", dayValue);
			// 当月
			double monthValue = 0D;
			for(Map<String, Object> m : monthList) {
				if (goodsPriceMap.get(m.get("goodsCode").toString()) != null) {
					// 数量*单价
					monthValue += Integer.parseInt(m.get("num").toString()) * goodsPriceMap.get(m.get("goodsCode").toString());
				}
			}
			map.put("monthValue", monthValue);
			// 上月
			double lastMonthValue = 0D;
			for(Map<String, Object> m : lastMonthList) {
				if (goodsPriceMap.get(m.get("goodsCode").toString()) != null) {
					// 数量*单价
					lastMonthValue += Integer.parseInt(m.get("num").toString()) * goodsPriceMap.get(m.get("goodsCode").toString());
				}
			}
			map.put("lastMonthValue", lastMonthValue);
			calculateMom(map, monthValue, lastMonthValue);
		} catch (Exception e) {
			logger.error("[storeGoodsPrice]", e);
		}
		return map;
	}

	/**
	 * 计算环比
	 * @param map
	 * @param monthValue
	 * @param lastMonthValue
	 */
	public void calculateMom(Map<String, Object> map,
							 double monthValue,
							 double lastMonthValue) {
		double mom = 0D;
		if (lastMonthValue > 0) {
			mom = ((monthValue-lastMonthValue) / lastMonthValue) * 100;
		}
		map.put("mom", String.format("%.2f", mom) + "%");
	}
}
