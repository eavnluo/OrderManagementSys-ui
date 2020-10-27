package com.platform.modules.wms;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.EasyPoiUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.order.entity.OrderGoodUniqueCodeScanEntity;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.wms.entity.StoreInventoryInfoEntity;
import com.platform.modules.wms.service.StoreInventoryInfoService;
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
@RestController
@RequestMapping("store/inventoryinfo")
public class StoreInventoryInfoController extends AbstractController {
	@Autowired
	private StoreInventoryInfoService storeInventoryInfoService;

	/**
	 * 查看所有列表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@RequestMapping("/queryAll")
	public RestResponse queryAll(@RequestParam Map<String, Object> params) {
		List<StoreInventoryInfoEntity> list = storeInventoryInfoService.queryAll(params);

		return RestResponse.success().put("list", list);
	}

	/**
	 * 分页查询库存管理
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@GetMapping("/list")
	public RestResponse list(@RequestParam Map<String, Object> params) {
		Page page = storeInventoryInfoService.queryPage(params);

		return RestResponse.success().put("page", page);
	}

	/**
	 * 获取库存列表数据
	 *
	 * @param params 请求参数
	 * @return obj
	 */
	@GetMapping("/findStoreList")
	public RestResponse findStoreList(@RequestParam Map<String, Object> params) {
		Page page = storeInventoryInfoService.queryStorePage(params);

		return RestResponse.success().put("page", page);
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
	 * 根据主键查询详情
	 *
	 * @param id 主键
	 * @return RestResponse
	 */
	@RequestMapping("/info/{id}")
	public RestResponse info(@PathVariable("id") String id) {
		StoreInventoryInfoEntity storeInventoryInfo = storeInventoryInfoService.getById(id);

		return RestResponse.success().put("inventoryinfo", storeInventoryInfo);
	}

	/**
	 * 新增库存管理
	 *
	 * @param storeInventoryInfo storeInventoryInfo
	 * @return RestResponse
	 */
	@SysLog("新增库存管理 ")
	@RequestMapping("/save")
	public RestResponse save(@RequestBody StoreInventoryInfoEntity storeInventoryInfo) {
		SysUserEntity currentUser = getUser();
		storeInventoryInfo.setCreatedBy(currentUser.getUserId());
		storeInventoryInfo.setUpdatedBy(currentUser.getUserId());
		storeInventoryInfoService.add(storeInventoryInfo);

		return RestResponse.success();
	}

	/**
	 * 修改库存管理
	 *
	 * @param storeInventoryInfo storeInventoryInfo
	 * @return RestResponse
	 */
	@SysLog("修改库存管理 ")
	@RequestMapping("/update")
	public RestResponse update(@RequestBody StoreInventoryInfoEntity storeInventoryInfo) {
		storeInventoryInfo.setUpdatedBy(getUser().getUserId());
		storeInventoryInfoService.update(storeInventoryInfo);

		return RestResponse.success();
	}

	/**
	 * 根据主键删除库存管理
	 *
	 * @param ids ids
	 * @return RestResponse
	 */
	@SysLog("删除库存管理 ")
	@RequestMapping("/delete")
	public RestResponse delete(@RequestBody String[] ids) {
		storeInventoryInfoService.deleteBatch(ids);

		return RestResponse.success();
	}

	/**
	 * 查询库存明细
	 *
	 * @param params {货主编码、仓库编码、商品编码}
	 * @return
	 */
	@SysLog("查询库存明细")
	@RequestMapping("/getGoodsInfoList")
	public RestResponse getGoodsInfoList(@RequestParam Map<String, Object> params) {
		if (params.size() != 3) {
			return RestResponse.error("参数不全");
		}
		List<OrderGoodUniqueCodeScanEntity> list = storeInventoryInfoService.findGoodUniqueList(params);
		return RestResponse.success().put("data", list);
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

	/**
	 * 导出库存信息
	 *
	 * @param params   过滤条件
	 * @param response
	 */
	@GetMapping("/exportStoreData")
	public void exportStoreData(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		try {
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
			logger.error("[exportStoreData]", e);
		}
	}

	/**
	 * 导出入库信息
	 *
	 * @param params   过滤条件
	 * @param response
	 */
	@GetMapping("/exportInStoreData")
	public void exportInStoreData(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		try {
			List<Map<String, Object>> list = storeInventoryInfoService.findInStoreList(params);
			Map<String, Object> maps = Maps.newHashMap();
			maps.put("dataList", list);
			TemplateExportParams exportParams = new TemplateExportParams(
					"static/export_in_store.xls");
			exportParams.setSheetName("入库信息");
			Workbook workbook = ExcelExportUtil.exportExcel(exportParams, maps);
			EasyPoiUtils.downLoadExcel("入库信息-v" + System.currentTimeMillis() + ".xls", response, workbook);
		} catch (Exception e) {
			logger.error("[exportStoreData]", e);
		}
	}

	/**
	 * 导出出库信息
	 *
	 * @param params   过滤条件
	 * @param response
	 */
	@GetMapping("/exportOutStoreData")
	public void exportOutStoreData(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		try {
			List<Map<String, Object>> list = storeInventoryInfoService.findOutStoreList(params);
			Map<String, Object> maps = Maps.newHashMap();
			maps.put("dataList", list);
			TemplateExportParams exportParams = new TemplateExportParams(
					"static/export_out_store.xls");
			exportParams.setSheetName("出库信息");
			Workbook workbook = ExcelExportUtil.exportExcel(exportParams, maps);
			EasyPoiUtils.downLoadExcel("出库信息-v" + System.currentTimeMillis() + ".xls", response, workbook);
		} catch (Exception e) {
			logger.error("[exportStoreData]", e);
		}
	}
}
