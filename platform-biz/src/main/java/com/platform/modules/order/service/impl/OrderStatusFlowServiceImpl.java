/*
 * 项目名称:platform-plus
 * 类名称:OrderStatusFlowServiceImpl.java
 * 包名称:com.platform.modules.order.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:31        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.order.dao.OrderStatusFlowDao;
import com.platform.modules.order.entity.OrderStatusFlowEntity;
import com.platform.modules.order.service.OrderStatusFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 订单状态流水表 Service实现类
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:31
 */
@Service("orderStatusFlowService")
public class OrderStatusFlowServiceImpl extends ServiceImpl<OrderStatusFlowDao, OrderStatusFlowEntity> implements OrderStatusFlowService {

    private Logger logger = LoggerFactory.getLogger(OrderStatusFlowServiceImpl.class);

    @Override
    public List<OrderStatusFlowEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<OrderStatusFlowEntity> page = new Query<OrderStatusFlowEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOrderStatusFlowPage(page, params));
    }

    @Override
    public boolean add(OrderStatusFlowEntity orderStatusFlow) {
        return this.save(orderStatusFlow);
    }

    @Override
    public boolean update(OrderStatusFlowEntity orderStatusFlow) {
        return this.updateById(orderStatusFlow);
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
}
