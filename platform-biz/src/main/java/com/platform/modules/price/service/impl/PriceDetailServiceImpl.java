/*
 * 项目名称:platform-plus
 * 类名称:PriceDetailServiceImpl.java
 * 包名称:com.platform.modules.price.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 13:23:09        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.price.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.common.utils.StringUtils;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.price.dao.PriceDetailDao;
import com.platform.modules.price.entity.PriceDetailEntity;
import com.platform.modules.price.service.PriceDetailService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 报价单包含的报价项清单Service实现类
 *
 * @author Mr.panbb
 * @date 2020-03-24 13:23:09
 */
@Service("priceDetailService")
public class PriceDetailServiceImpl extends ServiceImpl<PriceDetailDao, PriceDetailEntity>
        implements PriceDetailService {

    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;
    @Override
    public List<PriceDetailEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<PriceDetailEntity> page = new Query<PriceDetailEntity>(params).getPage();
        return page.setRecords(baseMapper.selectPriceDetailPage(page, params));
    }

    @Override
    public boolean add(PriceDetailEntity priceDetail) {
        return this.save(priceDetail);
    }

    @Override
    public boolean update(PriceDetailEntity priceDetail) {
        return this.updateById(priceDetail);
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
     * 获取价格明细
     *
     * @param params 参数
     * @return 明细
     */
    @Override
    public PriceDetailEntity get(Map<String, Object> params) {
        return baseMapper.get(params);
    }

    /**
     * 获取项目分类列表
     *
     * @param params 查询参数
     * @return map
     */
    @Override
    public List<PriceDetailEntity> findTypeList(Map<String, Object> params) {
        List<String> codeList = new ArrayList<>();
        if (!StringUtils.isEmpty(params.get("supplyCode"))) {
            codeList = Arrays.asList(params.get("supplyCode").toString().split(","));
        }
        // 根据客户代码查询报价明细
        String companyCode = "";
        if (!StringUtils.isEmpty(params.get("companyCode"))) {
            companyCode = params.get("companyCode").toString();
        }
        List<PriceDetailEntity> list = baseMapper.findTypeList(companyCode, codeList);
        return list;
    }

    /**
     * 查询费用清单列表
     *
     * @param params 查询参数
     * @return list
     */
    @Override
    public List<PriceDetailEntity> findList(Map<String, Object> params) {
        return baseMapper.findList(params);
    }
}
