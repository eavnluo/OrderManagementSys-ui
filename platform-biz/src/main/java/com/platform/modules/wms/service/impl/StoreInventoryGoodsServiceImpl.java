/*
 * 项目名称:platform-plus
 * 类名称:StoreInventoryGoodsServiceImpl.java
 * 包名称:com.platform.modules.store.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-31 10:34:30        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.wms.dao.StoreInventoryGoodsDao;
import com.platform.modules.wms.entity.StoreInventoryGoodsEntity;
import com.platform.modules.wms.service.StoreInventoryGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author zlm
 * @date 2020-03-31 10:34:30
 */
@Service("storeInventoryGoodsService")
public class StoreInventoryGoodsServiceImpl extends ServiceImpl<StoreInventoryGoodsDao, StoreInventoryGoodsEntity> implements StoreInventoryGoodsService {

	@Override
	public List<StoreInventoryGoodsEntity> queryAll(Map<String, Object> params) {
		return baseMapper.queryAll(params);
	}

	@Override
	public Page queryPage(Map<String, Object> params) {
		//排序
		params.put("sidx", "T.id");
		params.put("asc", false);
		Page<StoreInventoryGoodsEntity> page = new Query<StoreInventoryGoodsEntity>(params).getPage();
		return page.setRecords(baseMapper.selectStoreInventoryGoodsPage(page, params));
	}

	@Override
	public boolean add(StoreInventoryGoodsEntity storeInventoryGoods) {
		return this.save(storeInventoryGoods);
	}

	@Override
	public boolean update(StoreInventoryGoodsEntity storeInventoryGoods) {
		return this.updateById(storeInventoryGoods);
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
	public List<StoreInventoryGoodsEntity> listByOrderItem(String orderItem) {
		return baseMapper.selectList(new QueryWrapper<StoreInventoryGoodsEntity>().eq("ORDER_ITEM", orderItem));
	}

	@Override
	public Page<Map<String, Object>> getOutInRecords(Map<String,Object> params, String goodsId, List<String> orderItems) {
		Page<Map<String,Object>> page = new Query<Map<String,Object>>(params).getPage();
		List<Map<String,Object>> list = baseMapper.getOutInRecords(page,orderItems,goodsId);
		return page.setRecords(list);
	}
}
