package com.platform.modules.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.common.validator.AbstractAssert;
import com.platform.modules.base.entity.BaseAreaEntity;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseIndustryTypeEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseAreaService;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseIndustryTypeService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.cooperation.entity.CooperationGuideEntity;
import com.platform.modules.cooperation.service.CooperationGuideService;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.register.service.RegisterUserService;
import com.platform.modules.sys.entity.SysDictEntity;
import com.platform.modules.sys.service.SysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web信息接口
 *
 * @author Mr.panbb
 * @date 2020-03-17
 */
@Slf4j
@RestController
@RequestMapping("/web/info")
@Api(tags = "WebInfoController|注册用户接口")
public class WebInfoController extends ApiBaseController {

	@Autowired
	private CooperationGuideService cooperationGuideService;
	@Autowired
	private BaseCustomerInfoService baseCustomerInfoService;
	@Autowired
	private BaseSupplyInfoService baseSupplyInfoService;
	@Autowired
	private BaseIndustryTypeService baseIndustryTypeService;
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private BaseAreaService baseAreaService;
	@Autowired
	private RegisterUserService registerUserService;


	@GetMapping("/test")
	@ApiOperation(value = "测试接口", notes = "测试接口")
	public RestResponse test(@LoginUser RegisterUserEntity user, String msg) {

		return RestResponse.success();
	}

	/**
	 * 获取合作指南的信息
	 *
	 * @param user
	 * @param params type：字典类型值，例如：01，02，03，04
	 * @return
	 */
	@RequestMapping("/findGuideList")
	@ApiOperation(value = "获取合作指南列表", notes = "获取合作指南列表")
	public RestResponse findGuideList(@LoginUser RegisterUserEntity user, @RequestParam Map<String, Object> params) {
		AbstractAssert.isNull(params.get("type"), "请输入具体查询合作指南下面的项目");
		if (StringUtils.ZERO.equals(user.getType())) {
			params.put("objectOriented", "客户");
		} else {
			params.put("objectOriented", "供应商");
		}
		List<CooperationGuideEntity> list = cooperationGuideService.queryAll(params);
		return RestResponse.success().put("list", list);
	}

	/**
	 * 完善客户信息
	 *
	 * @param entity 要完善的用户信息
	 * @return 完善结果
	 */
	@PostMapping("/perfectCustomerInfo")
	public RestResponse perfectCustomerInfo(@LoginUser RegisterUserEntity user, @RequestBody BaseCustomerInfoEntity entity) {
		entity.setRegisterUserId(user.getId());
		user.setName(entity.getCustomerName());
		// 判断有无ID，有ID是修改，无ID是添加
		if (StringUtils.isBlank(entity.getId())) {
			baseCustomerInfoService.add(entity);
			user.setAuthenticationStatus(Constant.STR_ONE);
		} else {
			baseCustomerInfoService.update(entity);
		}
		registerUserService.updateById(user);
		return RestResponse.success();
	}

	/**
	 * 完善供应商信息
	 *
	 * @param entity 要完善的用户信息
	 * @return 完善结果
	 */
	@PostMapping("/perfectSupplyInfo")
	public RestResponse perfectSupplyInfo(@LoginUser RegisterUserEntity user, @RequestBody BaseSupplyInfoEntity entity) {
		entity.setRegisterUserId(user.getId());
		user.setName(entity.getSupplyName());
		// 判断有无ID，有ID是修改，无ID是添加
		if (StringUtils.isBlank(entity.getId())) {
			baseSupplyInfoService.add(entity);
			user.setAuthenticationStatus(Constant.STR_ONE);
		} else {
			baseSupplyInfoService.update(entity);
		}
		registerUserService.updateById(user);
		return RestResponse.success();
	}

	/**
	 * 获取客户信息
	 *
	 * @param user
	 * @return
	 */
	@GetMapping("/getCustomerInfo")
	public RestResponse getCustomerInfo(@LoginUser RegisterUserEntity user) {

		BaseCustomerInfoEntity entity = baseCustomerInfoService.getBaseMapper().selectOne(new QueryWrapper<BaseCustomerInfoEntity>().eq("REGISTER_USER_ID", user.getId()).eq("DEL_FLAG", 0));
		List<BaseAreaEntity> provinceList = baseAreaService.getListByParentId("100000");
		if (entity == null) {
			entity = new BaseCustomerInfoEntity();
			entity.setCustomerName(user.getName());
			entity.setLinkPhone(user.getPhoneNumber());
			return RestResponse.success().put("data", entity).put("provinceList", provinceList);
		} else {
			// 获取省市区列表
			List<BaseAreaEntity> cityList = baseAreaService.getListByParentId(entity.getPrivince());
			List<BaseAreaEntity> areaList = baseAreaService.getListByParentId(entity.getCity());
			return RestResponse.success().put("data", entity).put("provinceList", provinceList).put("cityList", cityList).put("areaList", areaList);
		}

	}

	/**
	 * 获取供应商信息
	 *
	 * @param user
	 * @return
	 */
	@GetMapping("/getSupplyInfo")
	public RestResponse getSupplyInfo(@LoginUser RegisterUserEntity user) {

		BaseSupplyInfoEntity entity = baseSupplyInfoService.getBaseMapper().selectOne(new QueryWrapper<BaseSupplyInfoEntity>().eq("REGISTER_USER_ID", user.getId()).eq("DEL_FLAG", 0));
		List<BaseAreaEntity> provinceList = baseAreaService.getListByParentId("100000");
		if (entity == null) {
			entity = new BaseSupplyInfoEntity();
			entity.setSupplyName(user.getName());
			entity.setLinkPhone(user.getPhoneNumber());
			return RestResponse.success().put("data", entity).put("provinceList", provinceList);
		} else {
			// 获取省市区列表
			List<BaseAreaEntity> cityList = baseAreaService.getListByParentId(entity.getPrivince());
			List<BaseAreaEntity> areaList = baseAreaService.getListByParentId(entity.getCity());
			return RestResponse.success().put("data", entity).put("provinceList", provinceList).put("cityList", cityList).put("areaList", areaList);
		}
	}

	/**
	 * 根据字典分组编码获取字典列表
	 *
	 * @param code 分组编码
	 * @return RestResponse
	 */
	@IgnoreAuth
	@GetMapping("findDicList")
	@ApiOperation(value = "获取字典列表", notes = "获取字典列表")
	public RestResponse findDicList(String code) {
		// 构造参数查询字典
		Map<String, Object> params = new HashMap<>(2);
		if (StringUtils.isNotBlank(code)) {
			params.put("code", code);
		}

		List<SysDictEntity> list = sysDictService.queryAll(params);
		return RestResponse.success().put("list", list);
	}


	/**
	 * 获取业务类型列表
	 *
	 * @return
	 */
	@GetMapping("/getIndustryTypeList")
	public RestResponse getIndustryTypeList() {
		List<BaseIndustryTypeEntity> list = baseIndustryTypeService.queryAll(new HashMap<String, Object>());
		return RestResponse.success().put("list", list);
	}

	/**
	 * 通过父ID获取省市区列表
	 *
	 * @param parentId
	 * @return
	 */
	@GetMapping("/getListByParentId/{parentId}")
	public RestResponse getListByParentId(@PathVariable("parentId") String parentId) {
		List<BaseAreaEntity> list = baseAreaService.getListByParentId(parentId);
		return RestResponse.success().put("list", list);
	}

}