/*
 * 项目名称:platform-plus
 * 类名称:BaseSupplyInfoServiceImpl.java
 * 包名称:com.platform.modules.base.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-03 17:21:59        jk     初版做成
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
import com.platform.modules.base.dao.BaseSupplyInfoDao;
import com.platform.modules.base.entity.*;
import com.platform.modules.base.service.*;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 供应商信息管理 Service实现类
 *
 * @author jk
 * @date 2019-09-03 17:21:59
 */
@Service("baseSupplyInfoService")
public class BaseSupplyInfoServiceImpl extends ServiceImpl<BaseSupplyInfoDao, BaseSupplyInfoEntity>
        implements BaseSupplyInfoService {

    @Autowired
    private BaseSupplyInfoDao baseSupplyInfoDao;
    @Autowired
    private BaseLinkmanInfoService baseLinkmanInfoService;
    @Autowired
    private BaseCarInfoService baseCarInfoService;
    @Autowired
    private BaseCityCodeService baseCityCodeService;
    @Autowired
    private BaseAreaService baseAreaService;

    @Override
    public List<BaseSupplyInfoEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.UPDATED_TIME");
        params.put("asc", false);
        Page<BaseSupplyInfoEntity> page = new Query<BaseSupplyInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectBaseSupplyInfoPage(page, params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(BaseSupplyInfoEntity baseSupplyInfo) {
        // 生成供应商编号
        String supplyCode = generateSupplyCode(baseSupplyInfo.getSupplyName(), baseSupplyInfo.getCity());
        baseSupplyInfo.setSupplyCode(supplyCode);
        // 按编号查找看是否存在此编号的数据条数
        Integer count = baseMapper.selectCount(new QueryWrapper<BaseSupplyInfoEntity>().eq("SUPPLY_CODE", supplyCode).eq("DEL_FLAG", 0));
        // 如果不等于0，供应商编号重复，结束方法调用，返回false
        if (count != 0) {
            throw new BusinessException("公司代码已存在，请重新输入公司名称和城市");
        }
        Date now = new Date();
        // 封装创建时间和修改时间
        baseSupplyInfo.setCreatedTime(now);
        baseSupplyInfo.setUpdatedTime(now);
        // 设置公司地址
        // 保存供应商信息
        boolean saveSupplyResult = this.save(baseSupplyInfo);
        // 获取联系人集合
        List<BaseLinkmanInfoEntity> linkmans = baseSupplyInfo.getLinkmanList();
        // 有联系人信息
        boolean saveLinkmanResult = true;
        if (linkmans != null) {
            // 获取自增的返回值
            for (BaseLinkmanInfoEntity linkman : linkmans) {
                // 封装创建时间和修改时间
                linkman.setCreatedTime(now);
                linkman.setUpdatedTime(now);
                // 封装创建人和修改人
                linkman.setCreatedBy(baseSupplyInfo.getCreatedBy());
                linkman.setUpdatedBy(baseSupplyInfo.getUpdatedBy());
                // 封装联系人的归属
                linkman.setBelongTo(baseSupplyInfo.getRegisterUserId());
            }
            saveLinkmanResult = baseLinkmanInfoService.saveBatch(linkmans);
        }
        // 获取卡车集合
        List<BaseCarInfoEntity> cars = baseSupplyInfo.getCarList();
        boolean saveCarResult = true;
        // 有卡车信息
        if (cars != null) {
            for (BaseCarInfoEntity car : cars) {
                car.setCreatedTime(now);
                car.setUpdatedTime(now);
                car.setCreatedBy(baseSupplyInfo.getCreatedBy());
                car.setUpdatedBy(baseSupplyInfo.getUpdatedBy());
                car.setBelongTo(baseSupplyInfo.getRegisterUserId());
            }
            saveCarResult = baseCarInfoService.saveBatch(cars);
        }
        return saveSupplyResult && saveLinkmanResult && saveCarResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(BaseSupplyInfoEntity baseSupplyInfo) {
        String code = generateSupplyCode(baseSupplyInfo.getSupplyName(), baseSupplyInfo.getCity());
        BaseSupplyInfoEntity findResult = baseMapper.selectById(baseSupplyInfo.getId());
        // code不一致
        if (!code.equals(findResult.getSupplyCode())) {
            BaseSupplyInfoEntity result = baseMapper.selectOne(new QueryWrapper<BaseSupplyInfoEntity>().eq("SUPPLY_CODE", code).eq("DEL_FLAG", 0));
            if (result != null) {
                throw new BusinessException("公司代码已存在，请重新输入公司名称和城市");
            }
            baseSupplyInfo.setSupplyCode(code);
        }
        baseSupplyInfo.setUpdatedTime(new Date());
        // 获取联系人集合
        List<BaseLinkmanInfoEntity> linkmans = baseSupplyInfo.getLinkmanList();
        // 获取车辆集合
        List<BaseCarInfoEntity> cars = baseSupplyInfo.getCarList();
        if (linkmans != null) {
            List<BaseLinkmanInfoEntity> saveLinkmans = new ArrayList<>();
            List<BaseLinkmanInfoEntity> updateLinkmans = new ArrayList<>();
            for (BaseLinkmanInfoEntity linkman : linkmans) {
                // 封装修改信息
                linkman.setUpdatedBy(baseSupplyInfo.getUpdatedBy());
                linkman.setUpdatedTime(baseSupplyInfo.getUpdatedTime());
                // 判断id是否为空
                if (linkman.getId() == null) {
                    // 为空，新数据，添加一条联系人
                    linkman.setBelongTo(baseSupplyInfo.getSupplyCode());
                    linkman.setCreatedBy(baseSupplyInfo.getCreatedBy());
                    linkman.setCreatedTime(new Date());
                    saveLinkmans.add(linkman);
                } else {
                    // 不为空，已有的数据，进行更改
                    updateLinkmans.add(linkman);
                }
            }
            if (saveLinkmans.size() > 0) {
                baseLinkmanInfoService.saveBatch(saveLinkmans);
            }
            if (updateLinkmans.size() > 0) {
                baseLinkmanInfoService.updateBatchById(updateLinkmans);
            }
        }
        // 判断车辆集合是够为空
        if (cars != null) {
            List<BaseCarInfoEntity> saveCars = new ArrayList<>();
            List<BaseCarInfoEntity> updateCars = new ArrayList<>();
            for (BaseCarInfoEntity car : cars) {
                // 封装修改信息
                car.setUpdatedBy(baseSupplyInfo.getUpdatedBy());
                car.setUpdatedTime(baseSupplyInfo.getUpdatedTime());
                // 判断id是否为空
                if (car.getId() == null) {
                    // 为空，新数据，添加一条车辆信息
                    car.setBelongTo(baseSupplyInfo.getSupplyCode());
                    car.setCreatedBy(baseSupplyInfo.getCreatedBy());
                    car.setCreatedTime(new Date());
                    saveCars.add(car);
                } else {
                    // 不为空，已有的数据，进行更改
                    updateCars.add(car);
                }
            }
            if (saveCars.size() > 0) {
                baseCarInfoService.saveBatch(saveCars);
            }
            if (updateCars.size() > 0) {
                baseCarInfoService.updateBatchById(updateCars);
            }
        }
        // 设置公司地址
        return this.updateById(baseSupplyInfo);
    }

    @Override
    public boolean delete(String supplyId) {
        return this.removeById(supplyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] supplyIds) {
        return this.removeByIds(Arrays.asList(supplyIds));
    }

    /**
     * 查询工厂信息列表
     *
     * @param params 查询条件
     * @return list
     */
    @Override
    public List<Map<String, Object>> findList(Map<String, Object> params) {
        return baseSupplyInfoDao.findeList(params);
    }

    /**
     * 获取结构化数据列表 // K - 4: 卡车，F - 5: 库房
     *
     * @return
     */
    @Override
    public JSONArray findFormatList() {
        JSONArray array = new JSONArray();
        List<BaseSupplyInfoEntity> list = queryAll(new HashMap<>(0));
        for (BaseSupplyInfoEntity supply : list) {
            if ("K".equals(supply.getType())) {
                JSONObject obj = new JSONObject();
                obj.put("code", supply.getSupplyCode());
                obj.put("name", supply.getSupplyName());
                obj.put("orgType", 4);
                obj.put("contactMan", supply.getLinkName());
                obj.put("contactPhone", supply.getLinkPhone());
                array.add(obj);
            }
        }
        return array;
    }

    /**
     * 获取格式化后的仓库列表，quan
     *
     * @return
     */
    @Override
    public JSONArray findFormatStoreList() {
        Map<String, Object> params = new HashMap<>(1);
        List<Map<String, String>> list = baseSupplyInfoDao.getSupplyInfo();
        JSONArray array = new JSONArray();
        for (Map<String, String> supply : list) {
            JSONObject obj = new JSONObject();
            obj.put("code", supply.get("supplyCode"));
            obj.put("name", supply.get("supplyName"));
            obj.put("address", supply.get("supplyAddress"));
            obj.put("lnglatType", "gps");
            obj.put("lng", 0);
            obj.put("lat", 0);
            obj.put("orgCode", "KF-00000");
            obj.put("contactMan", supply.get("name"));
            obj.put("contactPhone", supply.get("phone"));
            array.add(obj);
        }
        return array;
    }

    @Override
    public List<BaseSupplyInfoEntity> getBySupply() {
        return baseSupplyInfoDao.getBySupply();
    }

    @Override
    public List<BaseSupplyInfoEntity> getBySupplyType() {
        return baseSupplyInfoDao.getBySupplyType();
    }

    @Override
    public List<Map<String, String>> getSupplyInfo() {
        return baseSupplyInfoDao.getSupplyInfo();
    }

    /**
     * 查询库房数量
     *
     * @param isYesterday 是否昨天
     * @return
     */
    @Override
    public int findNumberForChart(boolean isYesterday) {
        return baseSupplyInfoDao.findNumberForChart(isYesterday);
    }

    /**
     * 生成供应商代码
     *
     * @param supplyName 供应商名称
     * @param city       城市ID
     * @return 供应商代码
     */
    @Override
    public String generateSupplyCode(String supplyName, String city) {
        if (supplyName.length() < 2) {
            throw new BusinessException("供应商名称长度太短，不能小于2位长度");
        }
        StringBuilder builder = new StringBuilder("@");
        try {
            // 获取供应商前三个字
            char[] characters = supplyName.substring(0, 2).toCharArray();
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
        } catch (Exception e) {
            throw new BusinessException("请输入正确的公司名称（中文）");
        }
        return builder.toString();
    }

    /**
     * 根据供应商类型获取供应商列表
     *
     * @param companyCode 公司代码
     * @param type        供应商类型
     * @return 供应商列表
     */
    @Override
    public List<BaseSupplyInfoEntity> findListByType(String companyCode, String type) {
        return baseMapper.findListByType(companyCode, type);
    }

    /**
     * 根据注册用户id查找供应商信息
     *
     * @param id
     * @return
     */
    @Override
    public BaseSupplyInfoEntity findByRegistryUserId(String id) {
        BaseSupplyInfoEntity entity = baseMapper.selectOne(new QueryWrapper<BaseSupplyInfoEntity>().eq("REGISTER_USER_ID", id));
        if (entity == null) {
            throw new BusinessException("供应商信息不存在,请先完善信息");
        }
        return entity;
    }

    /**
     * 根据编码获取信息
     *
     * @param supplyCode
     * @return
     */
    @Override
    public BaseSupplyInfoEntity findByCode(String supplyCode) {
        return getOne(new QueryWrapper<BaseSupplyInfoEntity>().eq("SUPPLY_CODE", supplyCode));
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
     *
     * @param entity
     */
    private void genenrateSupplyAddress(BaseSupplyInfoEntity entity) {
        if (entity != null) {
            if (entity.getSupplyAddress() == null || "".equals(entity.getSupplyAddress().trim())) {
                StringBuilder builder = new StringBuilder();
                String address = builder.append(entity.getPrivince()).append(" ").append(entity.getCity()).append(" ").append(entity.getArea()).toString();
                entity.setSupplyAddress(address);
            }
        }
    }
}
