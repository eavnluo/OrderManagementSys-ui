/*
 * 项目名称:platform-plus
 * 类名称:BaseCarInfoServiceImpl.java
 * 包名称:com.platform.modules.base.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-29 09:32:01        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.base.dao.BaseCarInfoDao;
import com.platform.modules.base.entity.BaseCarInfoEntity;
import com.platform.modules.base.service.BaseCarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author zlm
 * @date 2019-09-29 09:32:01
 */
@Service("baseCarInfoService")
public class BaseCarInfoServiceImpl extends ServiceImpl<BaseCarInfoDao, BaseCarInfoEntity>
        implements BaseCarInfoService {

    @Autowired
    private BaseCarInfoDao baseCarInfoDao;

    @Override
    public List<BaseCarInfoEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<BaseCarInfoEntity> page = new Query<BaseCarInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectBaseCarInfoPage(page, params));
    }

    @Override
    public boolean add(BaseCarInfoEntity baseCarInfo) {
        return this.save(baseCarInfo);
    }

    @Override
    public boolean update(BaseCarInfoEntity baseCarInfo) {
        return this.updateById(baseCarInfo);
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
    public boolean updateDelFlagByBelongTo(String[] ids) {
        baseCarInfoDao.updateDelFlagByBelongTo(ids);
        return true;
    }

    @Override
    public List<BaseCarInfoEntity> findByBelongTo(String belongTo){
        return baseMapper.selectList(new QueryWrapper<BaseCarInfoEntity>().eq("BELONG_TO", belongTo).eq("DEL_FLAG",0));
    }

    /**
     * 获取格式化的卡车司机信息
     *
     * @return
     */
    @Override
    public JSONArray findFormatList() {
        JSONArray array = new JSONArray();
        List<BaseCarInfoEntity> list = queryAll(new HashMap<>(0));
        for(BaseCarInfoEntity car : list) {
            JSONObject obj = new JSONObject();
            obj.put("username", car.getDriverLink());
            obj.put("name", car.getDriver());
            obj.put("userType", 500);
            obj.put("orgCode", car.getBelongTo());
            array.add(obj);
        }
        return array;
    }

    /**
     * 分页关联查询
     *
     * @param params 查询参数
     * @return Page
     */
    @Override
    public Page findPage(Map<String, Object> params) {
        Page<BaseCarInfoEntity> page = new Query<BaseCarInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.findPage(page, params));
    }

}
