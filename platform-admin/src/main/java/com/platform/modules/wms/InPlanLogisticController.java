/*
 * 项目名称:platform-plus
 * 类名称:InPlanLogisticController.java
 * 包名称:com.platform.modules.in.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 16:47:54        jk     初版做成
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
import com.platform.modules.wms.entity.InPlanLogisticEntity;
import com.platform.modules.wms.service.InPlanLogisticService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 入库物流信息 Controller
 *
 * @author jk
 * @date 2019-09-04 16:47:54
 */
@RestController
@RequestMapping("in/planlogistic")
public class InPlanLogisticController extends AbstractController {
	@Autowired
	private InPlanLogisticService inPlanLogisticService;
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
	@RequiresPermissions("in:planlogistic:list")
	public RestResponse queryAll(@RequestParam Map<String, Object> params) {
		List<InPlanLogisticEntity> list = inPlanLogisticService.queryAll(params);

		return RestResponse.success().put("list", list);
	}

	/**
	 * 分页查询入库物流信息
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@GetMapping("/list")
	@RequiresPermissions("in:planlogistic:list")
	public RestResponse list(@RequestParam Map<String, Object> params) {
		Page page = inPlanLogisticService.queryPage(params);

		return RestResponse.success().put("page", page);
	}

	/**
	 * 根据主键查询详情
	 *
	 * @param logisticsInfoNo 主键
	 * @return RestResponse
	 */
	@RequestMapping("/info/{logisticsInfoNo}")
	@RequiresPermissions("in:planlogistic:info")
	public RestResponse info(@PathVariable("logisticsInfoNo") String logisticsInfoNo) {
		InPlanLogisticEntity inPlanLogistic = inPlanLogisticService.getById(logisticsInfoNo);

		return RestResponse.success().put("planlogistic", inPlanLogistic);
	}

	/**
	 * 新增入库物流信息
	 *
	 * @param inPlanLogistic inPlanLogistic
	 * @return RestResponse
	 */
	@SysLog("新增入库物流信息 ")
	@RequestMapping("/save")
	@RequiresPermissions("in:planlogistic:save")
	public RestResponse save(@RequestBody InPlanLogisticEntity inPlanLogistic) {
		//获取当前用户
		SysUserEntity currentUser = getUser();
		inPlanLogistic.setCreatedBy(currentUser.getUserId());
		inPlanLogistic.setUpdatedBy(currentUser.getUserId());
		inPlanLogisticService.add(inPlanLogistic);

		return RestResponse.success();
	}

	/**
	 * 修改入库物流信息
	 *
	 * @param inPlanLogistic inPlanLogistic
	 * @return RestResponse
	 */
	@SysLog("修改入库物流信息 ")
	@RequestMapping("/update")
	@RequiresPermissions("in:planlogistic:update")
	public RestResponse update(@RequestBody InPlanLogisticEntity inPlanLogistic) {
		inPlanLogistic.setUpdatedBy(getUser().getUserId());
		inPlanLogisticService.update(inPlanLogistic);

		return RestResponse.success();
	}

	/**
	 * 根据主键删除入库物流信息
	 *
	 * @param logisticsInfoNos logisticsInfoNos
	 * @return RestResponse
	 */
	@SysLog("删除入库物流信息 ")
	@RequestMapping("/delete")
	@RequiresPermissions("in:planlogistic:delete")
	public RestResponse delete(@RequestBody String[] logisticsInfoNos) {
		inPlanLogisticService.deleteBatch(logisticsInfoNos);

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
		List<InPlanLogisticEntity> list = inPlanLogisticService.queryLogisticsInfo(orderItem);
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
		Boolean result = iotService.sentInInfo(wayBillNo);
		if (result) {
			Integer num = inPlanLogisticService.setPublish(orderItem, getUserId());
			if (num == 1) {
				//下发成功，改变运输状态

				return RestResponse.success();
			}
		}
		return RestResponse.error("运输信息下发失败，请检查相关信息。");
	}

	/**
	 * 为省市添加编号
	 *
	 * @param inPlanLogistic
	 * @return
	 */
	public InPlanLogisticEntity queryCode(InPlanLogisticEntity inPlanLogistic) {
		inPlanLogistic.setReceiveCityCode(baseAreaService.queryCode(inPlanLogistic.getReceiveCity()));
		inPlanLogistic.setReceiveProvinceCode(baseAreaService.queryCode(inPlanLogistic.getReceiveProvince()));
		inPlanLogistic.setReceiveDistrictCode(baseAreaService.queryCode(inPlanLogistic.getReceiveDistrict()));
		inPlanLogistic.setReceiveTownCode(baseAreaService.queryCode(inPlanLogistic.getReceiveTown()));
		inPlanLogistic.setDeliveryCityCode(baseAreaService.queryCode(inPlanLogistic.getDeliveryCity()));
		inPlanLogistic.setDeliveryProvinceCode(baseAreaService.queryCode(inPlanLogistic.getDeliveryProvince()));
		inPlanLogistic.setDeliveryDistrictCode(baseAreaService.queryCode(inPlanLogistic.getDeliveryDistrict()));
		inPlanLogistic.setDeliveryTownCode(baseAreaService.queryCode(inPlanLogistic.getDeliveryTown()));
		return inPlanLogistic;
	}

	/**
	 * 生成运输信息
	 *
	 * @param inPlanLogistic
	 * @return
	 */
	@RequestMapping("/createLogisticsInfo")
	public RestResponse createLogisticsInfo(@RequestBody InPlanLogisticEntity inPlanLogistic) {
		//添加省市编码
		//inPlanLogistic = queryCode(inPlanLogistic);
		String orderItem = inPlanLogistic.getOrderItem();
		//获取指定工作单号的运输信息
		List<InPlanLogisticEntity> list = inPlanLogisticService.queryLogisticsInfo(orderItem);
		// 存在时，修改，不存在时，添加
		if (list == null) {
			save(inPlanLogistic);
			orderRelationshipService.saveRelationship(inPlanLogistic.getOrderItem(),inPlanLogistic.getWayBillNo(),getUserId(),getUserId());
			return RestResponse.success();
		} else if (list.size() == 1) {
			inPlanLogistic.setId(list.get(0).getId());
			update(inPlanLogistic);
			return RestResponse.success();
		} else {
			return RestResponse.error(-10, "参数错误");
		}
	}
}
