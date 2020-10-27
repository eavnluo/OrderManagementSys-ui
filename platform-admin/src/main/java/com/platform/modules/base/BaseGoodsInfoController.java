package com.platform.modules.base;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.EasyPoiUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseGoodsInfoEntity;
import com.platform.modules.base.service.BaseGoodsInfoService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysUserEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品基础信息表 Controller
 *
 * @author jk
 * @date 2019-09-02 17:29:38
 */
@RestController
@RequestMapping("base/goodsinfo")
public class BaseGoodsInfoController extends AbstractController {
	@Autowired
	private BaseGoodsInfoService baseGoodsInfoService;

	/**
	 * 查看所有列表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@RequestMapping("/queryAll")
	@RequiresPermissions("base:goodsinfo:list")
	public RestResponse queryAll(@RequestParam Map<String, Object> params) {
		List<BaseGoodsInfoEntity> list = baseGoodsInfoService.queryAll(params);
		return RestResponse.success().put("list", list);
	}

	/**
	 * 分页查询商品基础信息表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@GetMapping("/list")
	@RequiresPermissions("base:goodsinfo:list")
	public RestResponse list(@RequestParam Map<String, Object> params) {
		Page page = baseGoodsInfoService.queryPage(params);

		return RestResponse.success().put("page", page);
	}

	/**
	 * 根据主键查询详情
	 *
	 * @param itemId 主键
	 * @return RestResponse
	 */
	@RequestMapping("/info/{itemId}")
	@RequiresPermissions("base:goodsinfo:info")
	public RestResponse info(@PathVariable("itemId") String itemId) {
		BaseGoodsInfoEntity baseGoodsInfo = baseGoodsInfoService.getById(itemId);

		return RestResponse.success().put("goodsinfo", baseGoodsInfo);
	}

	/**
	 * 新增商品基础信息表
	 *
	 * @param baseGoodsInfo baseGoodsInfo
	 * @return RestResponse
	 */
	@SysLog("新增商品基础信息表 ")
	@RequestMapping("/save")
	@RequiresPermissions("base:goodsinfo:save")
	public RestResponse save(@RequestBody BaseGoodsInfoEntity baseGoodsInfo) {
		Date now = new Date();
		//封装创建时间和修改时间
		baseGoodsInfo.setCreatedTime(now);
		baseGoodsInfo.setUpdatedTime(now);
		//获取当前用户
		SysUserEntity currentUser = getUser();
		baseGoodsInfo.setCreatedBy(currentUser.getUserId());
		baseGoodsInfo.setUpdatedBy(currentUser.getUserId());
		baseGoodsInfoService.add(baseGoodsInfo);

		return RestResponse.success();
	}

	/**
	 * 修改商品基础信息表
	 *
	 * @param baseGoodsInfo baseGoodsInfo
	 * @return RestResponse
	 */
	@SysLog("修改商品基础信息表 ")
	@RequestMapping("/update")
	@RequiresPermissions("base:goodsinfo:update")
	public RestResponse update(@RequestBody BaseGoodsInfoEntity baseGoodsInfo) {
		baseGoodsInfo.setUpdatedTime(new Date());
		baseGoodsInfo.setUpdatedBy(getUser().getUserId());
		baseGoodsInfoService.update(baseGoodsInfo);

		return RestResponse.success();
	}

	/**
	 * 根据主键删除商品基础信息表
	 *
	 * @param itemIds itemIds
	 * @return RestResponse
	 */
	@SysLog("删除商品基础信息表 ")
	@RequestMapping("/delete")
	@RequiresPermissions("base:goodsinfo:delete")
	public RestResponse delete(@RequestBody String[] itemIds) {
		baseGoodsInfoService.deleteBatch(itemIds);

		return RestResponse.success();
	}

	/**
	 * 导出商品基础信息
	 *
	 * @param params   过滤条件
	 * @param response
	 */
	@GetMapping("/exportData")
	public void exportData(@RequestParam Map<String, Object> params, HttpServletResponse response) {
		try {
			List<BaseGoodsInfoEntity> list = baseGoodsInfoService.queryAll(params);
			Map<String, Object> maps = Maps.newHashMap();
			maps.put("dataList", list);
			TemplateExportParams exportParams = new TemplateExportParams(
					"static/export_goods.xls");
			exportParams.setSheetName("商品基础信息");
			Workbook workbook = ExcelExportUtil.exportExcel(exportParams, maps);
			EasyPoiUtils.downLoadExcel("商品基础信息-v" + System.currentTimeMillis() + ".xls", response, workbook);
		} catch (Exception e) {
			logger.error("[exportData]", e);
		}
	}

}
