/*
 * 项目名称:platform-plus
 * 类名称:OrderTrackInfoServiceImpl.java
 * 包名称:com.platform.modules.order.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-11-05 08:52:24        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.order.dao.OrderTrackInfoDao;
import com.platform.modules.order.entity.OrderTrackInfoEntity;
import com.platform.modules.order.service.OrderTrackInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author zlm
 * @date 2019-11-05 08:52:24
 */
@Service("orderTrackInfoService")
public class OrderTrackInfoServiceImpl extends ServiceImpl<OrderTrackInfoDao, OrderTrackInfoEntity> implements OrderTrackInfoService {

    @Autowired
    private OrderTrackInfoDao orderTrackInfoDao;

    @Override
    public List<OrderTrackInfoEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<OrderTrackInfoEntity> page = new Query<OrderTrackInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOrderTrackInfoPage(page, params));
    }

    @Override
    public boolean add(OrderTrackInfoEntity orderTrackInfo) {
        return this.save(orderTrackInfo);
    }

    @Override
    public boolean update(OrderTrackInfoEntity orderTrackInfo) {
        return this.updateById(orderTrackInfo);
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
     * 根据工作单号获取追踪宝信息
     *
     * @param orderItem 工作单号
     * @return 追踪宝信息
     */
    @Override
    public OrderTrackInfoEntity getByOrderItem(String orderItem) {
        return orderTrackInfoDao.getByOrderItem(orderItem);
    }
}
