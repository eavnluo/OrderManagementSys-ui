/*
 * 项目名称:platform-plus
 * 类名称:PriceInfoServiceImpl.java
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
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.Query;
import com.platform.modules.price.dao.PriceDetailDao;
import com.platform.modules.price.dao.PriceInfoDao;
import com.platform.modules.price.entity.PriceDetailEntity;
import com.platform.modules.price.entity.PriceInfoEntity;
import com.platform.modules.price.service.PriceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 价格信息Service实现类
 *
 * @author Mr.panbb
 * @date 2020-03-24 13:23:09
 */
@Service("priceInfoService")
public class PriceInfoServiceImpl extends ServiceImpl<PriceInfoDao, PriceInfoEntity> implements PriceInfoService {

    @Autowired
    private PriceDetailDao priceDetailDao;

    @Override
    public List<PriceInfoEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<PriceInfoEntity> page = new Query<PriceInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectPriceInfoPage(page, params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(PriceInfoEntity priceInfo) {
        synchronized (this) {
            // 生成报价单号
            priceInfo.setPriceNo(createPriceNo());
            Date date = new Date();
            priceInfo.setCreateTime(date);
            this.save(priceInfo);
            List<PriceDetailEntity> list = priceInfo.getPriceDetailList();
            list.forEach(d -> {
                d.setId(d.getId().replace("-", ""));
                d.setPriceInfoId(priceInfo.getId());
                d.setCreateTime(date);
                priceDetailDao.insert(d);
            });
            return true;
        }
    }

    @Override
    public boolean update(PriceInfoEntity priceInfo) {
        return this.updateById(priceInfo);
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
     * 创建报价单编号
     *
     * @return priceNo
     */
    @Override
    public String createPriceNo() {
        // 今天的报价单数量 + 1
        int value = baseMapper.getCountForToday() + 1;
        String priceNo = "JWT" + DateUtils.format(new Date(), "yyyyMMdd") +String.format("%03d", value);
        return priceNo;
    }

    /**
     * 作废报价单
     *
     * @param id         信息ID
     * @param userId     更新人
     * @param updateTime 更新时间
     * @param status     状态
     */
    @Override
    public void updateStatus(String id, String userId, Date updateTime, String status) {
        baseMapper.updateStatus(id, userId, updateTime, status);
    }

    /**
     * 将过了有效期的订单作废
     */
    @Override
    public void toInvalid() {
        baseMapper.toInvalid();
    }

    /**
     * 根据费用项，查询相关报价供应商列表
     *
     * @param params 查询参数
     * @return List
     */
    @Override
    public List<Map<String, Object>> findSupplyList(Map<String, Object> params) {
        return baseMapper.findSupplyList(params);
    }
}
