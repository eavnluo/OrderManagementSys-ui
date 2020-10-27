/*
 * 项目名称:platform-plus
 * 类名称:BaseAreaServiceImpl.java
 * 包名称:com.platform.modules.base.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-30 09:33:03        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Query;
import com.platform.modules.base.dao.BaseAreaDao;
import com.platform.modules.base.entity.BaseAreaEntity;
import com.platform.modules.base.service.BaseAreaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 凯晟admin
 * @date 2019-09-30 09:33:03
 */
@Service("baseAreaService")
public class BaseAreaServiceImpl extends ServiceImpl<BaseAreaDao, BaseAreaEntity> implements BaseAreaService {

	@Override
	public List<BaseAreaEntity> queryAll(Map<String, Object> params) {
		return baseMapper.queryAll(params);
	}

	@Override
	public Page queryPage(Map<String, Object> params) {
		//排序
		params.put("sidx", "T.id");
		params.put("asc", false);
		Page<BaseAreaEntity> page = new Query<BaseAreaEntity>(params).getPage();
		return page.setRecords(baseMapper.selectBaseAreaPage(page, params));
	}

	@Override
	public boolean add(BaseAreaEntity baseArea) {
		return this.save(baseArea);
	}

	@Override
	public boolean update(BaseAreaEntity baseArea) {
		return this.updateById(baseArea);
	}

	@Override
	public boolean delete(String id) {
		return this.removeById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatch(String[] ids) {
		return this.removeByIds(Arrays.asList(ids));
	}

	@Override
	public String queryCode(String name) {
		name = name + "%";
		return baseMapper.queryCode(name);
	}

	@Override
	public List<BaseAreaEntity> getListByParentId(String parentId) {
		return list(new QueryWrapper<BaseAreaEntity>().eq("PARENT_ID", parentId));
	}

	/**
	 * 根据ID获取城市名称，不包含"市"
	 * @param id id
	 * @return
	 */
	@Override
	public String getCityName(String id) {
		// 根据city查询出对应的城市名称
		BaseAreaEntity findResult = baseMapper.selectById(id);
		if (findResult == null) {
			throw new BusinessException("没有此城市的信息");
		}
		String cityName = findResult.getName();
		if ("市".equals(cityName.substring(cityName.length() - 1))) {
			cityName = cityName.substring(0, cityName.length() - 1);
		}
		return cityName;
	}
}
