package com.platform.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.platform.modules.order.dto.OrderExportDto;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.service.OrderHeaderInfoService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 订单导出Excel工具类
 * @author Mr.panbb
 * @date 2020-04-20
 */
public abstract class OrderExportUtil {

	/**
	 * 导出订单列表
	 * @param orderHeaderInfoService 依赖注入服务
	 * @param params 查询参数
	 * @param response 返回流
	 * @param templateUrl 模板路径
	 */
	public static void orderExport(OrderHeaderInfoService orderHeaderInfoService, Map<String, Object> params, HttpServletResponse response, String templateUrl) {
		List<OrderHeaderInfoEntity> list = orderHeaderInfoService.queryAll(params);
		List<OrderExportDto> dtoList = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			OrderExportDto dto = new OrderExportDto();
			BeanUtils.copyProperties(list.get(i), dto);
			dto.setId("" + (i + 1));
			dtoList.add(dto);
		}
		Map<String, Object> maps = Maps.newHashMap();
		maps.put("maplist", dtoList);
		maps.put("startDate", params.get("orderBeginDate"));
		maps.put("endDate", params.get("orderEndDate"));
		TemplateExportParams exportParams = new TemplateExportParams(templateUrl);
		exportParams.setSheetName("订单信息");
		Workbook workbook = ExcelExportUtil.exportExcel(exportParams, maps);
		EasyPoiUtils.downLoadExcel("订单信息" + DateUtils.getNow("yyyyMMMdd") + RandomUtils.nextInt()
				+ ".xls", response, workbook);
	}

}
