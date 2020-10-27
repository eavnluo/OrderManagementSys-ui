/*
 * 项目名称:platform-plus
 * 类名称:OrderInterfaceLogServiceImpl.java
 * 包名称:com.platform.modules.order.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-10-23 09:23:54        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.order.dao.OrderInterfaceLogDao;
import com.platform.modules.order.entity.OrderInterfaceLogEntity;
import com.platform.modules.order.service.OrderInterfaceLogService;
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
 * @date 2019-10-23 09:23:54
 */
@Service("orderInterfaceLogService")
public class OrderInterfaceLogServiceImpl extends ServiceImpl<OrderInterfaceLogDao, OrderInterfaceLogEntity> implements OrderInterfaceLogService {

    @Override
    public List<OrderInterfaceLogEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<OrderInterfaceLogEntity> page = new Query<OrderInterfaceLogEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOrderInterfaceLogPage(page, params));
    }

    @Override
    public boolean add(OrderInterfaceLogEntity orderInterfaceLog) {
        return this.save(orderInterfaceLog);
    }

    @Override
    public boolean update(OrderInterfaceLogEntity orderInterfaceLog) {
        return this.updateById(orderInterfaceLog);
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
    public boolean saveLog(String orderItem, String url, String params, String result, String type) {
        OrderInterfaceLogEntity orderInterfaceLogEntity = new OrderInterfaceLogEntity();
        orderInterfaceLogEntity.setOrderItem(orderItem);
        orderInterfaceLogEntity.setUrl(url);
        orderInterfaceLogEntity.setParams(params);
        orderInterfaceLogEntity.setResult(result);
        orderInterfaceLogEntity.setType(type);
        orderInterfaceLogEntity.setCreatedTime(new Date());
        orderInterfaceLogEntity.setUpdatedTime(new Date());
        return add(orderInterfaceLogEntity);
    }
}
