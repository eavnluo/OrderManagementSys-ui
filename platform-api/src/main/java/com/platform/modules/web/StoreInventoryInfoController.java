package com.platform.modules.web;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.platform.annotation.LoginUser;
import com.platform.common.RegisterUserType;
import com.platform.common.utils.EasyPoiUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.wms.service.StoreInventoryInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 库存管理 Controller
 *
 * @author jk
 * @date 2019-09-05 14:14:39
 */
@Slf4j
@RestController
@RequestMapping("web/inventoryinfo")
@Api(tags = "StoreInventoryInfoController|库存接口")
public class StoreInventoryInfoController extends ApiBaseController {
	@Autowired
	private StoreInventoryInfoService storeInventoryInfoService;
	@Autowired
	private BaseCustomerInfoService baseCustomerInfoService;
	@Autowired
	private BaseSupplyInfoService baseSupplyInfoService;

	/**
	 * 获取当前登录用户库存列表数据
	 *
	 * @param params 请求参数
	 * @return obj
	 */
	@PostMapping("/findStoreList")
	public RestResponse findStoreList(@LoginUser RegisterUserEntity user, @RequestBody Map<String, Object> params) {
		try {
			if (RegisterUserType.CUSTOMER.equals(user.getType())) {
				// 客户
				BaseCustomerInfoEntity customerInfo = baseCustomerInfoService.findByRegistryUserId(user.getId());
				if (customerInfo != null) {
					params.put("ownerCode", customerInfo.getCustomerCode());
				} else {
					return RestResponse.success();
				}
			} else {
				// 供应商
				BaseSupplyInfoEntity supplyInfo = baseSupplyInfoService.findByRegistryUserId(user.getId());
				if (supplyInfo != null) {
					params.put("storeCode", supplyInfo.getSupplyCode());
				} else {
					return RestResponse.success();
				}
			}
			Page page = storeInventoryInfoService.queryStorePage(params);
			return RestResponse.success().put("page", page);
		} catch (Exception e) {
			log.error("", e);
		}
		return RestResponse.success();
	}

	/**
	 * 获取入库库存列表数据
	 *
	 * @param params 请求参数
	 * @return obj
	 */
	@GetMapping("/findInStoreList")
	public RestResponse findInStoreList(@RequestParam Map<String, Object> params) {
		Page page = storeInventoryInfoService.queryInStorePage(params);
		return RestResponse.success().put("page", page);
	}

	/**
	 * 获取出库库存列表数据
	 *
	 * @param params 请求参数
	 * @return obj
	 */
	@GetMapping("/findOutStoreList")
	public RestResponse findOutStoreList(@RequestParam Map<String, Object> params) {
		Page page = storeInventoryInfoService.queryOutStorePage(params);
		return RestResponse.success().put("page", page);
	}

	/**
	 * 导出库存信息
	 *
	 * @param params   过滤条件
	 * @param response
	 */
	@GetMapping("/exportStoreData")
	public void exportStoreData(@LoginUser RegisterUserEntity user, @RequestParam Map<String, Object> params, HttpServletResponse response) {
		try {
			if (user.getType().equals(RegisterUserType.CUSTOMER)) {
				// 客户
				BaseCustomerInfoEntity customerInfo = baseCustomerInfoService.findByRegistryUserId(user.getId());
				if (customerInfo != null) {
					params.put("ownerCode", customerInfo.getCustomerCode());
				}
			} else {
				// 供应商
				BaseSupplyInfoEntity supplyInfo = baseSupplyInfoService.findByRegistryUserId(user.getId());
				if (supplyInfo != null) {
					params.put("storeCode", supplyInfo.getSupplyCode());
				}
			}
			List<Map<String, Object>> list = storeInventoryInfoService.findStoreList(params);
			Map<String, Object> maps = Maps.newHashMap();
			// 汇总金额
			BigDecimal summaryPrice = new BigDecimal(0);
			// 汇总数量
			BigDecimal summaryNumber = new BigDecimal(0);
			for (Map<String, Object> m : list) {
				summaryPrice = summaryPrice.add(new BigDecimal(m.get("totalPrice").toString()));
				summaryNumber = summaryNumber.add(new BigDecimal(m.get("itemQuantity").toString()));
			}
			// 汇总
			Map<String, Object> summary = new HashMap<>(10);
			summary.put("goodsName", "合计");
			summary.put("goodsModel", "--");
			summary.put("specification", "--");
			summary.put("goodsPacking", "--");
			summary.put("goodsMaterial", "--");
			summary.put("price", "--");
			summary.put("itemQuantity", summaryNumber.doubleValue());
			summary.put("totalPrice", summaryPrice.doubleValue());
			summary.put("ownerCode", "--");
			summary.put("storeCode", "--");
			list.add(summary);
			maps.put("dataList", list);
			TemplateExportParams exportParams = new TemplateExportParams(
					"static/export_store.xls");
			exportParams.setSheetName("库存信息");
			Workbook workbook = ExcelExportUtil.exportExcel(exportParams, maps);
			EasyPoiUtils.downLoadExcel("库存信息-v" + System.currentTimeMillis() + ".xls", response, workbook);
		} catch (Exception e) {
			log.error("[exportStoreData]", e);
		}
	}

	/**
	 * 获取商品出入库记录
	 *
	 * @return
	 */
	@GetMapping("/getOutInRecords")
	public RestResponse getOutInRecords(@RequestParam Map<String, Object> params) {
		Page<Map<String, Object>> recordsList = storeInventoryInfoService.getOutInRecords(params);
		return RestResponse.success().put("page", recordsList);
	}
}
