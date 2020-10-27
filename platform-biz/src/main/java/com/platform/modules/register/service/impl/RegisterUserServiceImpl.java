/*
 * 项目名称:platform-plus
 * 类名称:RegisterUserServiceImpl.java
 * 包名称:com.platform.modules.register.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-17 08:52:12        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.register.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.RegisterUserType;
import com.platform.common.SupplyType;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.common.validator.AbstractAssert;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.register.dao.RegisterUserDao;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.register.service.RegisterUserService;
import com.platform.modules.requesting.entity.RequestingResearchEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author Mr.panbb
 * @date 2020-03-17 08:52:12
 */
@Service("registerUserService")
public class RegisterUserServiceImpl extends ServiceImpl<RegisterUserDao, RegisterUserEntity> implements RegisterUserService {
	@Autowired
	private BaseSupplyInfoService baseSupplyInfoService;
	@Override
	public List<RegisterUserEntity> queryAll(Map<String, Object> params) {
		return baseMapper.queryAll(params);
	}

	@Override
	public Page queryPage(Map<String, Object> params) {
		//排序
		params.put("asc", false);
		Page<RegisterUserEntity> page = new Query<RegisterUserEntity>(params).getPage();
		return page.setRecords(baseMapper.selectRegisterUserPage(page, params));
	}

	@Override
	public boolean add(RegisterUserEntity registerUser) {
		if (StringUtils.isBlank(registerUser.getPhoneNumber())) {
			throw new BusinessException("手机号不能为空");
		}
		RegisterUserEntity user = baseMapper.getByPhone(registerUser.getPhoneNumber(), "");
		if (user != null) {
			throw new BusinessException("该手机号已经被注册");
		}

		registerUser.setRegisterTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		if (StringUtils.isEmpty(registerUser.getPassword())) {
			registerUser.setPassword(new Sha256Hash(Constant.DEFAULT_PW, salt).toHex());
		} else {
			registerUser.setPassword(new Sha256Hash(registerUser.getPassword(), salt).toHex());
		}

		registerUser.setSalt(salt);
		return this.save(registerUser);
	}

	@Override
	public boolean update(RegisterUserEntity registerUser) {
		if (StringUtils.isBlank(registerUser.getPassword())) {
			registerUser.setPassword(null);
		} else {
			registerUser.setPassword(new Sha256Hash(registerUser.getPassword(), registerUser.getSalt()).toHex());
		}
		registerUser.setUpdateTime(new Date());
		return this.updateById(registerUser);
	}

	@Override
	public boolean delete(String id) {
		return deleteBatch(Arrays.asList(id));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatch(List<String> ids) {
		String status = "-1";
		Date updateTime = new Date();
		return baseMapper.updateStatusByIds(ids, updateTime, status);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean checkBatch(String[] ids) {
		String status = "1";
		Date updateTime = new Date();
		return baseMapper.updateStatusByIds(Arrays.asList(ids), updateTime, status);
	}

	@Override
	public boolean enable(String id) {
		String[] ids = {id};
		return checkBatch(ids);
	}

	@Override
	public RegisterUserEntity login(String phoneNumber, String password) {
		// 验证用户是否存在
		RegisterUserEntity user = baseMapper.getByPhone(phoneNumber, "1");
		AbstractAssert.isNull(user, "该用户暂未注册或未审核通过");

		// 验证密码
		String checkPassword = new Sha256Hash(password, user.getSalt()).toHex();
		if (!user.getPassword().equals(checkPassword)) {
			throw new BusinessException("账号或密码错误");
		}
		return user;
	}

	@Override
	public void addFromRequesting(RequestingResearchEntity requestingResearch) {
		RegisterUserEntity user = new RegisterUserEntity();
		user.setName(requestingResearch.getCompanyName());
		user.setPhoneNumber(requestingResearch.getPhoneNumber());
		user.setType("0");
		user.setPassword("888888");
		user.setEmail(requestingResearch.getEmail());
		user.setStatus("0");
		this.add(user);
	}

	@Override
	public void editPassword(String phoneNumber, String password) {
		RegisterUserEntity user = baseMapper.getByPhone(phoneNumber, "1");
		AbstractAssert.isNull(user, "该用户暂未注册或未审核通过");
		// 生成新的密码
		String newPassword = new Sha256Hash(password, user.getSalt()).toHex();
		baseMapper.editPassword(phoneNumber, newPassword, new Date());
	}

	/**
	 * 修改认证状态
	 *
	 * @param id                   id
	 * @param authenticationStatus 认证状态
	 * @return
	 */
	@Override
	public boolean updateAuthenticationStatus(String id, String authenticationStatus) {
		Integer rows = baseMapper.updateAuthenticationStatus(id, authenticationStatus);
		if (rows != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否认证
	 *
	 * @param entity 登录用户信息
	 * @return true 已认证，false 未认证
	 */
	@Override
	public boolean isAuthentication(RegisterUserEntity entity) {
		if (Constant.STR_ZERO.equals( entity.getAuthenticationStatus())) {
			return false;
		}
		return true;
	}

	@Override
	public BaseSupplyInfoEntity checkoutUserType(RegisterUserEntity user) {
		// 判断用户是否认证
		if (!isAuthentication(user)) {
			throw new BusinessException("企业未认证，请先完善信息");
		}
		BaseSupplyInfoEntity supplyInfo = baseSupplyInfoService.findByRegistryUserId(user.getId());
		// 判断当前登录的用户是不是卡车供应商
		if (!RegisterUserType.SUPPLY.equals(user.getType()) || !supplyInfo.getType().contains(SupplyType.K_)) {
			throw new BusinessException("当前登录用户不是卡车供应商，此操作不被允许");
		}
		return supplyInfo;
	}
}
