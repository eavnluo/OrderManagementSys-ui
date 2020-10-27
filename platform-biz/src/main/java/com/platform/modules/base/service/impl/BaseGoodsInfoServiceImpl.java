/*
 * 项目名称:platform-plus
 * 类名称:BaseGoodsInfoServiceImpl.java
 * 包名称:com.platform.modules.base.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-02 17:29:38        jk     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.base.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.base.dao.BaseGoodsInfoDao;
import com.platform.modules.base.entity.BaseGoodsInfoEntity;
import com.platform.modules.base.service.BaseGoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品基础信息表 Service实现类
 *
 * @author jk
 * @date 2019-09-02 17:29:38
 */
@Service("baseGoodsInfoService")
public class BaseGoodsInfoServiceImpl extends ServiceImpl<BaseGoodsInfoDao, BaseGoodsInfoEntity>
		implements BaseGoodsInfoService {

	@Autowired
	private BaseGoodsInfoDao baseGoodsInfoDao;

	@Override
	public List<BaseGoodsInfoEntity> queryAll(Map<String, Object> params) {
		return baseMapper.queryAll(params);
	}

	@Override
	public Page queryPage(Map<String, Object> params) {
		//排序
		params.put("sidx", "T.UPDATED_TIME");
		params.put("asc", false);
		Page<BaseGoodsInfoEntity> page = new Query<BaseGoodsInfoEntity>(params).getPage();
		List<BaseGoodsInfoEntity> goodsInfoEntityList = baseMapper.selectBaseGoodsInfoPage(page, params);
		return page.setRecords(goodsInfoEntityList);
	}

	@Override
	public boolean add(BaseGoodsInfoEntity baseGoodsInfo) {

		return this.save(baseGoodsInfo);
	}

	@Override
	public boolean update(BaseGoodsInfoEntity baseGoodsInfo) {
		return this.updateById(baseGoodsInfo);
	}

	@Override
	public boolean delete(String goodsId) {
		return this.removeById(goodsId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatch(String[] goodsIds) {
		return this.removeByIds(Arrays.asList(goodsIds));
	}

	/**
	 * 获取商品基础数据列表
	 *
	 * @param params 查询参数
	 * @return list
	 */
	@Override
	public List<Map<String, Object>> findList(Map<String, Object> params) {
		return baseGoodsInfoDao.findList(params);
	}

	@Override
	public List<BaseGoodsInfoEntity> findGoodsList() {
		return baseGoodsInfoDao.findGoodsList();
	}

	@Override
	public JSONArray findFormatList() {
		List<BaseGoodsInfoEntity> list = queryAll(new HashMap<>(0));
		JSONArray array = new JSONArray();
		for (BaseGoodsInfoEntity goods : list) {
			JSONObject obj = new JSONObject();
			obj.put("code", goods.getGoodsCode());
			obj.put("name", goods.getGoodsName());
			// 扩展属性
			JSONObject item = new JSONObject();
			item.put("brand", goods.getBrand());
			item.put("specification", goods.getSpecification());
			item.put("unnit", goods.getUnit());
			item.put("origin", goods.getOrigin());
			item.put("price", goods.getPrice());
			item.put("goodsLong", goods.getGoodsLong());
			item.put("goodshigh", goods.getGoodsHigh());
			item.put("goodsWidth", goods.getGoodsWidth());
			item.put("goodsWeight", goods.getGoodsWeight());
			item.put("color", goods.getColor());
			// 型号
			obj.put("extAttr", item);
			array.add(obj);
		}
		return array;
	}

	@Override
	public List<BaseGoodsInfoEntity> findByBarCode(String[] barCodeArray) {
		return baseGoodsInfoDao.selectByBarCode(barCodeArray);
	}
}
