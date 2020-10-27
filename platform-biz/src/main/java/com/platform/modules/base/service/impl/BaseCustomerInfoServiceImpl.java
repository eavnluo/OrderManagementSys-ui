/*
 * 项目名称:platform-plus
 * 类名称:BaseCustomerInfoServiceImpl.java
 * 包名称:com.platform.modules.base.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-20 15:00:22        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Query;
import com.platform.modules.base.dao.BaseCustomerInfoDao;
import com.platform.modules.base.entity.BaseCityCodeEntity;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.service.BaseAreaService;
import com.platform.modules.base.service.BaseCityCodeService;
import com.platform.modules.base.service.BaseCustomerInfoService;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 客户信息表Service实现类
 *
 * @author jk
 * @date 2019-09-20 15:00:22
 */
@Service("baseCustomerInfoService")
public class BaseCustomerInfoServiceImpl extends ServiceImpl<BaseCustomerInfoDao, BaseCustomerInfoEntity>
		implements BaseCustomerInfoService {

	@Autowired
	private BaseCustomerInfoDao baseCustomerInfoDao;
	@Autowired
	private BaseCityCodeService baseCityCodeService;
	@Autowired
	private BaseAreaService baseAreaService;

	@Override
	public List<BaseCustomerInfoEntity> queryAll(Map<String, Object> params) {
		return baseMapper.queryAll(params);
	}

	@Override
	public Page queryPage(Map<String, Object> params) {
		//排序
		params.put("sidx", "T.id");
		params.put("asc", false);
		Page<BaseCustomerInfoEntity> page = new Query<BaseCustomerInfoEntity>(params).getPage();
		return page.setRecords(baseMapper.selectBaseCustomerInfoPage(page, params));
	}

	@Override
	public boolean add(BaseCustomerInfoEntity baseCustomerInfo) {
		// 获取公司名称和城市生成公司代码
		String customerCode = generateCustomerCode(baseCustomerInfo.getCustomerName(), baseCustomerInfo.getCity());
		// 查询是否有重复的公司代码
		BaseCustomerInfoEntity findResult = findByCode(customerCode);
		if (findResult != null) {
			// 有重复的公司代码
			throw new BusinessException("公司代码已存在，请重新输入公司名称和城市");
		}
		baseCustomerInfo.setCustomerCode(customerCode);
		// 设置公司地址
		return this.save(baseCustomerInfo);
	}

	@Override
	public boolean update(BaseCustomerInfoEntity baseCustomerInfo) {
		// 根据提交的公司名称和城市生成code和数据库中的进行比较，
		// 如果一致则认为没有修改，不一致则认为已经修改了，
		String customerCode = generateCustomerCode(baseCustomerInfo.getCustomerName(), baseCustomerInfo.getCity());
		BaseCustomerInfoEntity findResult = baseMapper.selectById(baseCustomerInfo.getId());
		if (!customerCode.equals(findResult.getCustomerCode())) {
			// 不一致则认为已经修改了公司名称或城市
			BaseCustomerInfoEntity result = findByCode(customerCode);
			// 判断重新生成的code是否已经存在
			if (result != null) {
				throw new BusinessException("公司代码已存在，请重新输入公司名称和城市");
			}
			baseCustomerInfo.setCustomerCode(customerCode);
		}
		// 设置公司地址
		return this.updateById(baseCustomerInfo);
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
	public List<BaseCustomerInfoEntity> findCustomerLists() {
		return baseCustomerInfoDao.findCustomerLists();
	}

	/**
	 * 获取格式化的数据： C-采购商；F-分销商；G-工厂
	 *
	 * @return
	 */
	@Override
	public JSONArray findFormatList() {
		JSONArray array = new JSONArray();
		List<BaseCustomerInfoEntity> list = queryAll(new HashMap<>());
		for (BaseCustomerInfoEntity customer : list) {
			JSONObject obj = new JSONObject();
			obj.put("code", customer.getCustomerCode());
			obj.put("name", customer.getCustomerName());
			obj.put("orgType", getType(customer.getType()));
			array.add(obj);
		}
		return array;
	}

	@Override
	public List<BaseCustomerInfoEntity> getByOwner() {
		return baseCustomerInfoDao.getByOwner();
	}

	/**
	 * 转化组织类型
	 *
	 * @param type 类型 C-采购商；F-分销商；G-工厂
	 * @return number
	 */
	public int getType(String type) {
		if ("F".equals(type)) {
			return 3;
		} else if ("C".equals(type)) {
			return 1;
		} else {
			return 2;
		}
	}

	@Override
	public JSONObject findCustomerList() {
		JSONObject data = new JSONObject();
		// 1、买方列表
		List<BaseCustomerInfoEntity> buyList = baseCustomerInfoDao.findBuyList();
		data.put("buyList", buyList);
		// 2、卖方列表
		List<BaseCustomerInfoEntity> sellList = baseCustomerInfoDao.findSellList();
		data.put("sellList", sellList);

		return data;
	}

	@Override
	public BaseCustomerInfoEntity queryById(String id) {
		return baseCustomerInfoDao.findById(id);
	}

	@Override
	public BaseCustomerInfoEntity findByCode(String customerCode) {
		return baseCustomerInfoDao.selectOne(new QueryWrapper<BaseCustomerInfoEntity>().eq("CUSTOMER_CODE", customerCode).eq("DEL_FLAG", 0));
	}

	/**
	 * 转化成仓库的格式
	 *
	 * @return
	 */
	@Override
	public JSONArray findFormatStoreList() {
		JSONArray array = new JSONArray();
		List<BaseCustomerInfoEntity> list = queryAll(new HashMap<>());
		for (BaseCustomerInfoEntity customer : list) {
			JSONObject obj = new JSONObject();
			obj.put("code", customer.getCustomerCode());
			obj.put("name", customer.getCustomerName());
			obj.put("address", customer.getAddress());
			obj.put("lnglatType", "gps");
			obj.put("lng", 0);
			obj.put("lat", 0);
			obj.put("orgCode", "KF-00000");
			array.add(obj);
		}
		return array;
	}

	/**
	 * 生成客户代码
	 *
	 * @param customerName 客户名称
	 * @param city         城市ID
	 * @return
	 */
	@Override
	public String generateCustomerCode(String customerName, String city) {

		if (customerName.length() < 3) {
			throw new BusinessException("公司名称长度太短，不能小于3位长度");
		}
		// 获取公司前三个字
		char[] characters = customerName.substring(0, 3).toCharArray();
		StringBuilder builder = new StringBuilder();
		for (char hanzi : characters) {
			String pinyin = convertSingleHanziToPinyin(hanzi);
			// 获取拼音首字母并大写
			String firstChar = pinyin.substring(0, 1).toUpperCase();
			builder.append(firstChar);
		}
		String cityName = baseAreaService.getCityName(city);
		BaseCityCodeEntity cityCodeEntity = baseCityCodeService.findByName(cityName);
		if (cityCodeEntity == null) {
			throw new BusinessException("没有此城市的三字代码,请确认输入城市是否正确");
		}
		builder.append(cityCodeEntity.getCode());
		return builder.toString();
	}

	/**
	 * 根据注册用户ID查询信息
	 *
	 * @param registerUserId 注册用户ID
	 * @return
	 */
	@Override
	public BaseCustomerInfoEntity findByRegistryUserId(String registerUserId) {
		return baseMapper.findByRegistryUserId(registerUserId);
	}

	/**
	 * 根据订单编工作单号查询客户信息
	 *
	 * @param orderItem 工作单号
	 * @return 客户信息
	 */
	@Override
	public BaseCustomerInfoEntity getByOrderItem(String orderItem) {
		return baseMapper.getByOrderItem(orderItem);
	}

	/***
	 * 将单个汉字转成拼音
	 *
	 * @param hanzi 汉字
	 * @return 拼音
	 */
	private static String convertSingleHanziToPinyin(char hanzi) {
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		String[] res;
		StringBuffer sb = new StringBuffer();
		try {
			res = PinyinHelper.toHanyuPinyinStringArray(hanzi, outputFormat);
			// 对于多音字，只用第一个拼音
			sb.append(res[0]);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return sb.toString();
	}

	/**
	 * 设置公司地址
	 * @param entity
	 */
	private void generateCustomerAddress(BaseCustomerInfoEntity entity) {
		if (entity != null) {
			if (entity.getCustomerAddress() == null || "".equals(entity.getCustomerAddress().trim())) {
				StringBuilder builder = new StringBuilder();
				String address = builder.append(entity.getPrivince()).append(" ").append(entity.getCity()).append(" ").append(entity.getArea()).toString();
				entity.setCustomerAddress(address);
			}
		}
	}
}
