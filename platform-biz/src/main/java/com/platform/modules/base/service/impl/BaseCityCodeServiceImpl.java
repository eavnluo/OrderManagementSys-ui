/*
 * 项目名称:platform-plus
 * 类名称:BaseCityCodeServiceImpl.java
 * 包名称:com.platform.modules.base.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 11:04:39        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.base.dao.BaseCityCodeDao;
import com.platform.modules.base.entity.BaseCityCodeEntity;
import com.platform.modules.base.service.BaseCityCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author zlm
 * @date 2020-03-18 11:04:39
 */
@Service("baseCityCodeService")
public class BaseCityCodeServiceImpl extends ServiceImpl<BaseCityCodeDao, BaseCityCodeEntity> implements BaseCityCodeService {

	@Override
	public List<BaseCityCodeEntity> queryAll(Map<String, Object> params) {
		return baseMapper.queryAll(params);
	}

	@Override
	public Page queryPage(Map<String, Object> params) {
		//排序
		params.put("sidx", "T.fullName");
		params.put("asc", false);
		Page<BaseCityCodeEntity> page = new Query<BaseCityCodeEntity>(params).getPage();
		return page.setRecords(baseMapper.selectBaseCityCodePage(page, params));
	}

	@Override
	public boolean add(BaseCityCodeEntity baseCityCode) {
		return this.save(baseCityCode);
	}

	@Override
	public boolean update(BaseCityCodeEntity baseCityCode) {
		return this.updateById(baseCityCode);
	}

	@Override
	public boolean delete(String fullName) {
		return this.removeById(fullName);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatch(String[] fullNames) {
		return this.removeByIds(Arrays.asList(fullNames));
	}

	/**
	 * 根据城市名称查询代码
	 *
	 * @param cityName 城市名称
	 * @return
	 */
	@Override
	public BaseCityCodeEntity findByName(String cityName) {
		return baseMapper.selectOne(new QueryWrapper<BaseCityCodeEntity>().eq("FULL_NAME", cityName));
	}
}
