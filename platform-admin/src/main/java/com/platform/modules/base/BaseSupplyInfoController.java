package com.platform.modules.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.EasyPoiUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseAreaEntity;
import com.platform.modules.base.entity.BaseCarInfoEntity;
import com.platform.modules.base.entity.BaseLinkmanInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseAreaService;
import com.platform.modules.base.service.BaseCarInfoService;
import com.platform.modules.base.service.BaseLinkmanInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysUserEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 供应商信息管理 Controller
 *
 * @author jk
 * @date 2019-09-03 17:21:59
 */
@RestController
@RequestMapping("base/supplyinfo")
public class BaseSupplyInfoController extends AbstractController {
	@Autowired
	private BaseSupplyInfoService baseSupplyInfoService;
	@Autowired
	private BaseLinkmanInfoService baseLinkmanInfoService;
	@Autowired
	private BaseCarInfoService baseCarInfoService;
	@Autowired
	private BaseAreaService baseAreaService;

	/**
	 * 查看所有列表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@RequestMapping("/queryAll")
	public RestResponse queryAll(@RequestParam Map<String, Object> params) {
		List<BaseSupplyInfoEntity> list = baseSupplyInfoService.queryAll(params);

		return RestResponse.success().put("list", list);
	}

	/**
	 * 分页查询供应商信息管理
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@GetMapping("/list")
	@RequiresPermissions("base:supplyinfo:list")
	public RestResponse list(@RequestParam Map<String, Object> params) {
		Page page = baseSupplyInfoService.queryPage(params);

		return RestResponse.success().put("page", page);
	}

	/**
	 * 根据主键查询详情
	 *
	 * @param supplyId 主键
	 * @return RestResponse
	 */
	@RequestMapping("/info/{supplyId}")
	@RequiresPermissions("base:supplyinfo:info")
	public RestResponse info(@PathVariable("supplyId") String supplyId) {
		BaseSupplyInfoEntity baseSupplyInfo = baseSupplyInfoService.getById(supplyId);
		String registerUserId = baseSupplyInfo.getRegisterUserId();
		//查询联系人集合
		List<BaseLinkmanInfoEntity> linkmans = baseLinkmanInfoService.findByBelongTo(registerUserId);
		//查询车辆信息集合
		List<BaseCarInfoEntity> cars = baseCarInfoService.findByBelongTo(registerUserId);
		// 获取省市区列表
		List<BaseAreaEntity> cityList = baseAreaService.getListByParentId(baseSupplyInfo.getPrivince());
		List<BaseAreaEntity> areaList = baseAreaService.getListByParentId(baseSupplyInfo.getCity());
		//封装信息
		baseSupplyInfo.setLinkmanList(linkmans);
		baseSupplyInfo.setCarList(cars);

		return RestResponse.success().put("supplyinfo", baseSupplyInfo).put("cityList", cityList).put("areaList", areaList);
	}

	/**
	 * 新增供应商信息管理
	 *
	 * @param baseSupplyInfo baseSupplyInfo
	 * @return RestResponse
	 */
	@SysLog("新增供应商信息管理 ")
	@RequestMapping("/save")
	@RequiresPermissions("base:supplyinfo:save")
	public RestResponse save(@RequestBody BaseSupplyInfoEntity baseSupplyInfo) {
		//获取当前用户
		SysUserEntity currentUser = getUser();
		baseSupplyInfo.setCreatedBy(currentUser.getUserId());
		baseSupplyInfo.setUpdatedBy(currentUser.getUserId());
		boolean saveResult = baseSupplyInfoService.add(baseSupplyInfo);
		if (saveResult) {
			return RestResponse.success();
		}
		return RestResponse.error(-1, "该供应商信息已存在");

	}

	/**
	 * 修改供应商信息管理
	 *
	 * @param baseSupplyInfo baseSupplyInfo
	 * @return RestResponse
	 */
	@SysLog("修改供应商信息管理 ")
	@RequestMapping("/update")
	@RequiresPermissions("base:supplyinfo:update")
	public RestResponse update(@RequestBody BaseSupplyInfoEntity baseSupplyInfo) {
		baseSupplyInfo.setUpdatedBy(getUser().getUserId());
		baseSupplyInfoService.update(baseSupplyInfo);

		return RestResponse.success();
	}

	/**
	 * 根据主键删除供应商信息管理
	 *
	 * @param supplyIds supplyIds
	 * @return RestResponse
	 */
	@SysLog("删除供应商信息管理 ")
	@RequestMapping("/delete")
	@RequiresPermissions("base:supplyinfo:delete")
	public RestResponse delete(@RequestBody String[] supplyIds) {
		//删除供应商信息
		baseSupplyInfoService.deleteBatch(supplyIds);
		//删除联系人信息
		baseLinkmanInfoService.updateDelFlagByBelongTo(supplyIds);
		//删除车辆信息
		baseCarInfoService.updateDelFlagByBelongTo(supplyIds);
		return RestResponse.success();
	}

	@SysLog("按照供应商编码查询数据")
	@RequestMapping("/queryBycode")
	public RestResponse queryBycode(String code) {
		BaseSupplyInfoEntity supplyInfo = baseSupplyInfoService.getBaseMapper().selectOne(new QueryWrapper<BaseSupplyInfoEntity>().eq("SUPPLY_CODE", code));

		return RestResponse.success().put("supplyInfo", supplyInfo);
	}

	/**
	 * 导出供应商基础信息
	 *
	 * @param params   过滤条件
	 * @param response
	 */
	@GetMapping("/exportData")
	public void exportData(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		try {
			List<BaseSupplyInfoEntity> list = baseSupplyInfoService.queryAll(params);
			for (BaseSupplyInfoEntity entity : list) {
				String type = entity.getType().replace("K","卡车");
				type = type.replace("F","仓储");
				type = type.replace("O","其他");
				entity.setType(type);
			}
			Map<String, Object> map = Maps.newHashMap();
			map.put("dataList", list);
			TemplateExportParams exportParams = new TemplateExportParams(
					"static/export_supplier.xls");
			exportParams.setSheetName("供应商基础信息");
			Workbook workbook = ExcelExportUtil.exportExcel(exportParams, map);
			EasyPoiUtils.downLoadExcel("供应商基础信息-v" + System.currentTimeMillis() + ".xls", response, workbook);
		} catch (Exception e) {
			logger.error("[exportData]", e);
		}
	}
}
