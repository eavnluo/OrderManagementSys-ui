package com.platform.modules.web.base;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.LoginUser;
import com.platform.common.RegisterUserType;
import com.platform.common.SupplyType;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseCarInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCarInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.web.ApiBaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * web车辆管理接口
 *
 * @author 朱黎明
 * @date 2020-03-23
 */
@Slf4j
@RestController
@RequestMapping("/web/car")
@Api(tags = "BaseCarInfoController|web车辆管理接口")
public class BaseCarInfoController extends ApiBaseController {
	@Autowired
	private BaseCarInfoService baseCarInfoService;
	@Autowired
	private BaseSupplyInfoService baseSupplyInfoService;

	/**
	 * 分页查询
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@PostMapping("/list")
	public RestResponse list(@LoginUser RegisterUserEntity user, @RequestBody Map<String, Object> params) {
		BaseSupplyInfoEntity entity = baseSupplyInfoService.findByRegistryUserId(user.getId());
		params.put("belongTo", entity.getRegisterUserId());
		Page page = baseCarInfoService.queryPage(params);
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
		BaseCarInfoEntity baseCarInfo = baseCarInfoService.getById(id);
		return RestResponse.success().put("carinfo", baseCarInfo);
	}

	/**
	 * 新增
	 *
	 * @param baseCarInfo baseCarInfo
	 * @return RestResponse
	 */
	@RequestMapping("/save")
	public RestResponse save(@LoginUser RegisterUserEntity user, @RequestBody BaseCarInfoEntity baseCarInfo) {
		BaseSupplyInfoEntity entity = baseSupplyInfoService.findByRegistryUserId(user.getId());
		// 判断供应商类型是不是卡车供应商
		if (!entity.getType().contains(SupplyType.K_)) {
			throw new BusinessException("当前登录用户不是卡车供应商，此操作不被允许");
		}
		baseCarInfo.setBelongTo(entity.getRegisterUserId());
		baseCarInfoService.add(baseCarInfo);
		return RestResponse.success();
	}

	/**
	 * 修改
	 *
	 * @param baseCarInfo baseCarInfo
	 * @return RestResponse
	 */
	@RequestMapping("/update")
	public RestResponse update(@RequestBody BaseCarInfoEntity baseCarInfo) {
		baseCarInfoService.update(baseCarInfo);
		return RestResponse.success();
	}

	/**
	 * 根据主键删除
	 *
	 * @param params params
	 * @return RestResponse
	 */
	@RequestMapping("/delete")
	public RestResponse delete(@RequestBody Map<String, String[]> params) {
		baseCarInfoService.deleteBatch(params.get("ids"));
		return RestResponse.success();
	}

	/**
	 * 获取卡车供应商车辆信息列表
	 *
	 * @param user
	 * @return
	 */
	@GetMapping("/getCarListBySupply")
	public RestResponse getCarListBySupply(@LoginUser RegisterUserEntity user) {
		// 判断当前登录用户是客户还是供应商
		if (RegisterUserType.CUSTOMER.equals(user.getType())) {
			// 用户类型是客户，不需要查数据库，返回空数组
			return RestResponse.success().put("list", new JSONArray());
		}
		List<BaseCarInfoEntity> list = baseCarInfoService.findByBelongTo(user.getId());
		return RestResponse.success().put("list", list);
	}
}
