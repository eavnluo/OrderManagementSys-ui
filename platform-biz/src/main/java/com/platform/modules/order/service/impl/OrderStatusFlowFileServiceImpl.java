/*
 * 项目名称:platform-plus
 * 类名称:OrderStatusFlowFileServiceImpl.java
 * 包名称:com.platform.modules.order.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-07 11:23:38        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.order.dao.OrderStatusFlowFileDao;
import com.platform.modules.order.entity.OrderStatusFlowFileEntity;
import com.platform.modules.order.service.OrderStatusFlowFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author zlm
 * @date 2020-03-07 11:23:38
 */
@Service("orderStatusFlowFileService")
public class OrderStatusFlowFileServiceImpl extends ServiceImpl<OrderStatusFlowFileDao, OrderStatusFlowFileEntity> implements OrderStatusFlowFileService {

    @Override
    public List<OrderStatusFlowFileEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<OrderStatusFlowFileEntity> page = new Query<OrderStatusFlowFileEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOrderStatusFlowFilePage(page, params));
    }

    @Override
    public boolean add(OrderStatusFlowFileEntity orderStatusFlowFile) {
        return this.save(orderStatusFlowFile);
    }

    @Override
    public boolean update(OrderStatusFlowFileEntity orderStatusFlowFile) {
        return this.updateById(orderStatusFlowFile);
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
