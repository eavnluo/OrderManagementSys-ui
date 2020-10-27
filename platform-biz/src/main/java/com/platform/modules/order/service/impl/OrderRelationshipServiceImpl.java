/*
 * 项目名称:platform-plus
 * 类名称:OrderRelationshipServiceImpl.java
 * 包名称:com.platform.modules.order.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-11-05 10:34:40        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.order.dao.OrderRelationshipDao;
import com.platform.modules.order.entity.OrderRelationshipEntity;
import com.platform.modules.order.service.OrderRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 凯晟admin
 * @date 2019-11-05 10:34:40
 */
@Service("orderRelationshipService")
public class OrderRelationshipServiceImpl extends ServiceImpl<OrderRelationshipDao, OrderRelationshipEntity> implements OrderRelationshipService {

    @Autowired
    private OrderRelationshipDao orderRelationshipDao;

    @Override
    public List<OrderRelationshipEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<OrderRelationshipEntity> page = new Query<OrderRelationshipEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOrderRelationshipPage(page, params));
    }

    @Override
    public boolean add(OrderRelationshipEntity orderRelationship) {
        return this.save(orderRelationship);
    }

    @Override
    public boolean update(OrderRelationshipEntity orderRelationship) {
        return this.updateById(orderRelationship);
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
    public boolean saveRelationship(String orderItem, String wayBillNo,String createdUserID,String updatedUserID) {
        OrderRelationshipEntity orderRelationshipEntity = new OrderRelationshipEntity(orderItem, wayBillNo);
        orderRelationshipEntity.setCreatedBy(createdUserID);
        orderRelationshipEntity.setUpdatedBy(updatedUserID);
        orderRelationshipEntity.setCreatedTime(new Date());
        orderRelationshipEntity.setUpdatedTime(new Date());
        return add(orderRelationshipEntity);
    }

    @Override
    public List<OrderRelationshipEntity> findByOrderItem(String orderItem) {
        return orderRelationshipDao.findByOrderItem(orderItem);
    }
}
