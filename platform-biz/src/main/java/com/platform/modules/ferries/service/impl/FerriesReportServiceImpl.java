/*
 * 项目名称:platform-plus
 * 类名称:FerriesReportServiceImpl.java
 * 包名称:com.platform.modules.ferries.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-06 11:15:10        JK     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.ferries.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.ferries.dao.FerriesReportDao;
import com.platform.modules.ferries.entity.FerriesReportEntity;
import com.platform.modules.ferries.service.FerriesReportService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 船运报表Service实现类
 *
 * @author JK
 * @date 2019-09-06 11:15:10
 */
@Service("ferriesReportService")
public class FerriesReportServiceImpl extends ServiceImpl<FerriesReportDao, FerriesReportEntity>
        implements FerriesReportService {

    @Autowired
    private FerriesReportDao ferriesReportDao;

    @Override
    public List<FerriesReportEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.FILLING_DATE");
        params.put("asc", false);
        Page<FerriesReportEntity> page = new Query<FerriesReportEntity>(params).getPage();
        return page.setRecords(baseMapper.selectFerriesReportPage(page, params));
    }

    @Override
    public boolean add(FerriesReportEntity ferriesReport) {
        Date now = new Date();
        ferriesReport.setCreatedTime(now);
        ferriesReport.setUpdatedTime(now);
        return this.save(ferriesReport);
    }

    @Override
    public boolean update(FerriesReportEntity ferriesReport) {
        ferriesReport.setUpdatedTime(new Date());
        return this.updateById(ferriesReport);
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

    /**
     * 获取本年图表监控数据
     *
     * @return Object
     */
    @Override
    public JSONObject findChartData() {
        JSONObject object = new JSONObject();
        JSONObject data = new JSONObject();
        List<FerriesReportEntity> list = ferriesReportDao.findListForChart();
        // 国内
        List<Integer> gn = Lists.newArrayList();
        // 中亚
        List<Integer> zy = Lists.newArrayList();
        // 中俄
        List<Integer> ze = Lists.newArrayList();
        // 中欧
        List<Integer> zo = Lists.newArrayList();
        list.forEach(i -> {
            gn.add(i.getInland());
            zy.add(i.getZhongYa());
            ze.add(i.getZhongE());
            zo.add(i.getZhongOu());
        });
        data.put("国内", gn);
        data.put("中亚", zy);
        data.put("中俄", ze);
        data.put("中欧", zo);
        // TODO 计算周的数据
        String[] times = {"1周", "2周", "3周", "4周", "5周", "6周", "7周"};
        object.put("data", data);
        object.put("times", times);
        return object;
    }

    /**
     * 查询最新的运输信息
     *
     * @return Object
     */
    @Override
    public JSONObject getNewInfo() {
        JSONObject data = new JSONObject();
        FerriesReportEntity fr = ferriesReportDao.getNewInfo();
        String[] xData = {"海铁联运","中欧","中亚","中俄"};
        List<Integer> yData = Lists.newArrayList();
        if (fr != null) {
            yData.add(fr.getInland());
            yData.add(fr.getZhongOu());
            yData.add(fr.getZhongYa());
            yData.add(fr.getZhongE());
        }
        data.put("xData", xData);
        data.put("yData", yData);
        return data;
    }
}
