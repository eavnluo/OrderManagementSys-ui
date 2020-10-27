/*
 * 项目名称:platform-plus
 * 类名称:OrderGoodUniqueCodeStandardServiceImpl.java
 * 包名称:com.platform.modules.order.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-12 13:22:59        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.order.dao.OrderGoodUniqueCodeStandardDao;
import com.platform.modules.order.entity.OrderGoodUniqueCodeStandardEntity;
import com.platform.modules.order.service.OrderGoodUniqueCodeStandardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品唯一编码标准表 Service实现类
 *
 * @author jk
 * @date 2019-09-12 13:22:59
 */
@Service("orderGoodUniqueCodeStandardService")
public class OrderGoodUniqueCodeStandardServiceImpl extends ServiceImpl<OrderGoodUniqueCodeStandardDao, OrderGoodUniqueCodeStandardEntity> implements OrderGoodUniqueCodeStandardService {

    @Override
    public List<OrderGoodUniqueCodeStandardEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<OrderGoodUniqueCodeStandardEntity> page = new Query<OrderGoodUniqueCodeStandardEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOrderGoodUniqueCodeStandardPage(page, params));
    }

    @Override
    public boolean add(OrderGoodUniqueCodeStandardEntity orderGoodUniqueCodeStandard) {
        return this.save(orderGoodUniqueCodeStandard);
    }

    @Override
    public boolean update(OrderGoodUniqueCodeStandardEntity orderGoodUniqueCodeStandard) {
        return this.updateById(orderGoodUniqueCodeStandard);
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
    public List<OrderGoodUniqueCodeStandardEntity> findByOrderItem(String orderItem) {

        return baseMapper.selectByOrderItem(orderItem);
    }
}
