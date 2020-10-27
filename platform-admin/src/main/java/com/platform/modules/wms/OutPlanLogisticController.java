/*
 * 项目名称:platform-plus
 * 类名称:OutPlanLogisticController.java
 * 包名称:com.platform.modules.out.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 09:31:42        stg     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.service.BaseAreaService;
import com.platform.modules.iot.IotService;
import com.platform.modules.order.service.OrderRelationshipService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.wms.entity.OutPlanLogisticEntity;
import com.platform.modules.wms.service.OutPlanLogisticService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 出库物流信息表 Controller
 *
 * @author stg
 * @date 2019-09-05 09:31:42
 */
@RestController
@RequestMapping("out/planlogistic")
public class OutPlanLogisticController extends AbstractController {
	@Autowired
	private OutPlanLogisticService outPlanLogisticService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private IotService iotService;
	@Autowired
	private OrderRelationshipService orderRelationshipService;

	/**
	 * 查看所有列表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@RequestMapping("/queryAll")
	@RequiresPermissions("out:planlogistic:list")
	public RestResponse queryAll(@RequestParam Map<String, Object> params) {
		List<OutPlanLogisticEntity> list = outPlanLogisticService.queryAll(params);

		return RestResponse.success().put("list", list);
	}

	/**
	 * 分页查询出库物流信息表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@GetMapping("/list")
	@RequiresPermissions("out:planlogistic:list")
	public RestResponse list(@RequestParam Map<String, Object> params) {
		Page page = outPlanLogisticService.queryPage(params);

		return RestResponse.success().put("page", page);
	}

	/**
	 * 根据主键查询详情
	 *
	 * @param carrierCompany 主键
	 * @return RestResponse
	 */
	@RequestMapping("/info/{carrierCompany}")
	@RequiresPermissions("out:planlogistic:info")
	public RestResponse info(@PathVariable("carrierCompany") String carrierCompany) {
		OutPlanLogisticEntity outPlanLogistic = outPlanLogisticService.getById(carrierCompany);

		return RestResponse.success().put("planlogistic", outPlanLogistic);
	}

	/**
	 * 新增出库物流信息表
	 *
	 * @param outPlanLogistic outPlanLogistic
	 * @return RestResponse
	 */
	@SysLog("新增出库物流信息表 ")
	@RequestMapping("/save")
	@RequiresPermissions("out:planlogistic:save")
	public RestResponse save(@RequestBody OutPlanLogisticEntity outPlanLogistic) {

		Date now = new Date();
		//封装创建时间和修改时间
		outPlanLogistic.setCreatedTime(now);
		outPlanLogistic.setUpdatedTime(now);
		//获取当前用户
		SysUserEntity currentUser = getUser();
		outPlanLogistic.setCreatedBy(currentUser.getUserId());
		outPlanLogistic.setUpdatedBy(currentUser.getUserId());
		outPlanLogisticService.add(outPlanLogistic);

		return RestResponse.success();
	}

	/**
	 * 修改出库物流信息表
	 *
	 * @param outPlanLogistic outPlanLogistic
	 * @return RestResponse
	 */
	@SysLog("修改出库物流信息表 ")
	@RequestMapping("/update")
	@RequiresPermissions("out:planlogistic:update")
	public RestResponse update(@RequestBody OutPlanLogisticEntity outPlanLogistic) {

		outPlanLogisticService.update(outPlanLogistic);

		return RestResponse.success();
	}

	/**
	 * 根据主键删除出库物流信息表
	 *
	 * @param carrierCompanys carrierCompanys
	 * @return RestResponse
	 */
	@SysLog("删除出库物流信息表 ")
	@RequestMapping("/delete")
	@RequiresPermissions("out:planlogistic:delete")
	public RestResponse delete(@RequestBody String[] carrierCompanys) {
		outPlanLogisticService.deleteBatch(carrierCompanys);

		return RestResponse.success();
	}


	/**
	 * 根据条件物流信息
	 *
	 * @param orderItem
	 * @return
	 */
	@GetMapping("/queryLogisticsInfo")
	public RestResponse queryLogisticsInfo(@RequestParam String orderItem) {
		List<OutPlanLogisticEntity> list = outPlanLogisticService.queryLogisticsInfo(orderItem);
		return RestResponse.success().put("list", list);
	}

	/**
	 * 修改下发标志
	 *
	 * @param orderItem
	 * @return
	 */
	@RequestMapping("/setPublish")
	public RestResponse setPublish(@RequestParam String orderItem, String wayBillNo) {
		Boolean result = iotService.sendOutInfo(wayBillNo);
		if (result) {
			Integer num = outPlanLogisticService.setPublish(orderItem, getUserId());
			if (num == 1) {
				return RestResponse.success();
			}
		}
		return RestResponse.error();
	}

	/**
	 * 为省市添加编号
	 *
	 * @param outPlanLogistic
	 * @return
	 */
	public OutPlanLogisticEntity queryCode(OutPlanLogisticEntity outPlanLogistic) {
		outPlanLogistic.setReceiveCityCode(baseAreaService.queryCode(outPlanLogistic.getReceiveCity()));
		outPlanLogistic.setReceiveProvinceCode(baseAreaService.queryCode(outPlanLogistic.getReceiveProvince()));
		outPlanLogistic.setReceiveDistrictCode(baseAreaService.queryCode(outPlanLogistic.getReceiveDistrict()));
		outPlanLogistic.setReceiveTownCode(baseAreaService.queryCode(outPlanLogistic.getReceiveTown()));

		outPlanLogistic.setDeliveryCityCode(baseAreaService.queryCode(outPlanLogistic.getDeliveryCity()));
		outPlanLogistic.setDeliveryProvinceCode(baseAreaService.queryCode(outPlanLogistic.getDeliveryProvince()));
		outPlanLogistic.setDeliveryDistrictCode(baseAreaService.queryCode(outPlanLogistic.getDeliveryDistrict()));
		outPlanLogistic.setDeliveryTownCode(baseAreaService.queryCode(outPlanLogistic.getDeliveryTown()));
		return outPlanLogistic;
	}

	/**
	 * 生成运输信息
	 *
	 * @param outPlanLogistic
	 * @return
	 */
	@RequestMapping("/createLogisticsInfo")
	public RestResponse createLogisticsInfo(@RequestBody OutPlanLogisticEntity outPlanLogistic) {
		//添加省市编码
		//outPlanLogistic = queryCode(outPlanLogistic);
		outPlanLogistic.setCarrierId(outPlanLogistic.getWayBillNo());
		String orderItem = outPlanLogistic.getOrderItem();
		//获取指定工作单号的运输信息
		List<OutPlanLogisticEntity> list = outPlanLogisticService.queryLogisticsInfo(orderItem);
		//存在时，修改，不存在时，添加
		if (list == null) {
			save(outPlanLogistic);
			orderRelationshipService.saveRelationship(orderItem,outPlanLogistic.getWayBillNo(),getUserId(),getUserId());
			return RestResponse.success();
		} else if (list.size() == 1) {
			update(outPlanLogistic);
			return RestResponse.success();
		} else {
			return RestResponse.error(-10, "参数错误");
		}
	}
}
