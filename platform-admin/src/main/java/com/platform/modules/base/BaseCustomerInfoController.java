package com.platform.modules.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.EasyPoiUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseAreaEntity;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.service.BaseAreaService;
import com.platform.modules.base.service.BaseCityCodeService;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 客户信息表Controller
 *
 * @author jk
 * @date 2019-09-20 15:00:22
 */
@RestController
@RequestMapping("base/customerinfo")
public class BaseCustomerInfoController extends AbstractController {
	@Autowired
	private BaseCustomerInfoService baseCustomerInfoService;
	@Autowired
	private BaseCityCodeService baseCityCodeService;
	@Autowired
	private BaseAreaService baseAreaService;

	/**
	 * 查看所有列表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@RequestMapping("/queryAll")
	@RequiresPermissions("base:customerinfo:list")
	public RestResponse queryAll(@RequestParam Map<String, Object> params) {
		List<BaseCustomerInfoEntity> list = baseCustomerInfoService.queryAll(params);

		return RestResponse.success().put("list", list);
	}

	/**
	 * 分页查询客户信息表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@GetMapping("/list")
	@RequiresPermissions("base:customerinfo:list")
	public RestResponse list(@RequestParam Map<String, Object> params) {
		Page page = baseCustomerInfoService.queryPage(params);
		return RestResponse.success().put("page", page);
	}

	/**
	 * 根据主键查询详情
	 *
	 * @param id 主键
	 * @return RestResponse
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("base:customerinfo:info")
	public RestResponse info(@PathVariable("id") String id) {
		BaseCustomerInfoEntity baseCustomerInfo = baseCustomerInfoService.queryById(id);
		// 获取省市区列表
		List<BaseAreaEntity> cityList = baseAreaService.getListByParentId(baseCustomerInfo.getPrivince());
		List<BaseAreaEntity> areaList = baseAreaService.getListByParentId(baseCustomerInfo.getCity());
		return RestResponse.success().put("customerinfo", baseCustomerInfo).put("cityList", cityList).put("areaList", areaList);
	}

	/**
	 * 新增客户信息表
	 *
	 * @param baseCustomerInfo baseCustomerInfo
	 * @return RestResponse
	 */
	@SysLog("新增客户信息表")
	@RequestMapping("/save")
	@RequiresPermissions("base:customerinfo:save")
	public RestResponse save(@RequestBody BaseCustomerInfoEntity baseCustomerInfo) {
		baseCustomerInfoService.add(baseCustomerInfo);
		return RestResponse.success();
	}

	/**
	 * 修改客户信息表
	 *
	 * @param baseCustomerInfo baseCustomerInfo
	 * @return RestResponse
	 */
	@SysLog("修改客户信息表")
	@RequestMapping("/update")
	@RequiresPermissions("base:customerinfo:update")
	public RestResponse update(@RequestBody BaseCustomerInfoEntity baseCustomerInfo) {
		baseCustomerInfoService.update(baseCustomerInfo);
		return RestResponse.success();
	}

	/**
	 * 根据主键删除客户信息表
	 *
	 * @param ids ids
	 * @return RestResponse
	 */
	@SysLog("删除客户信息表")
	@RequestMapping("/delete")
	@RequiresPermissions("base:customerinfo:delete")
	public RestResponse delete(@RequestBody String[] ids) {
		baseCustomerInfoService.deleteBatch(ids);

		return RestResponse.success();
	}

	/**
	 * 获取客户列表，卖方、卖方
	 *
	 * @return Object
	 */
	@GetMapping(value = "/findCustomerList")
	public RestResponse findCustomerList() {
		JSONObject data = baseCustomerInfoService.findCustomerList();
		return RestResponse.success().put("data", data);
	}

	/**
	 * 导出客户基础信息
	 *
	 * @param params   过滤条件
	 * @param response
	 */
	@GetMapping("/exportData")
	public void exportData(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		try {
			List<BaseCustomerInfoEntity> list = baseCustomerInfoService.queryAll(params);
			list.forEach(info -> {
				if ("S".equals(info.getType())) {
					info.setType("卖方");
				} else if ("B".equals(info.getType())) {
					info.setType("买方");
				} else {
					info.setType("买方，卖方");
				}
			});
			Map<String, Object> map = Maps.newHashMap();
			map.put("dataList", list);
			TemplateExportParams exportParams = new TemplateExportParams(
					"static/export_customer.xls");
			exportParams.setSheetName("客户基础信息");
			Workbook workbook = ExcelExportUtil.exportExcel(exportParams, map);
			EasyPoiUtils.downLoadExcel("客户基础信息-v" + System.currentTimeMillis() + ".xls", response, workbook);
		} catch (Exception e) {
			logger.error("[exportData]", e);
		}
	}

	/**
	 * 根据注册用户ID查询客户信息
	 * @param registerUserId 注册用户ID
	 * @return RestResponse
	 */
	@RequestMapping(value = "/getByRegisterUserId/{registerUserId}")
	public RestResponse getByRegisterUserId(@PathVariable("registerUserId") String registerUserId){
		BaseCustomerInfoEntity customer = baseCustomerInfoService.findByRegistryUserId(registerUserId);
		return RestResponse.success().put("customer", customer);
	}

}
