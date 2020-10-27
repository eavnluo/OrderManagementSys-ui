/*
 * 项目名称:platform-plus
 * 类名称:OrderReceiveNoticeServiceImpl.java
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
import com.platform.modules.order.dao.OrderReceiveNoticeDao;
import com.platform.modules.order.entity.OrderReceiveNoticeEntity;
import com.platform.modules.order.service.OrderReceiveNoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 订单消息接收表 Service实现类
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:31
 */
@Service("orderReceiveNoticeService")
public class OrderReceiveNoticeServiceImpl extends ServiceImpl<OrderReceiveNoticeDao, OrderReceiveNoticeEntity> implements OrderReceiveNoticeService {

    @Override
    public List<OrderReceiveNoticeEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.delFlag");
        params.put("asc", false);
        Page<OrderReceiveNoticeEntity> page = new Query<OrderReceiveNoticeEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOrderReceiveNoticePage(page, params));
    }

    @Override
    public boolean add(OrderReceiveNoticeEntity orderReceiveNotice) {
        return this.save(orderReceiveNotice);
    }

    @Override
    public boolean update(OrderReceiveNoticeEntity orderReceiveNotice) {
        return this.updateById(orderReceiveNotice);
    }

    @Override
    public boolean delete(String delFlag) {
        return this.removeById(delFlag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] delFlags) {
        return this.removeByIds(Arrays.asList(delFlags));
    }
}
