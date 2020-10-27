/*
 * 项目名称:platform-plus
 * 类名称:OutStorageFlowServiceImpl.java
 * 包名称:com.platform.modules.wms.out.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 16:19:19        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.OrderStatus;
import com.platform.common.utils.Query;
import com.platform.modules.order.dao.OrderHeaderInfoDao;
import com.platform.modules.wms.dao.OutStorageFlowDao;
import com.platform.modules.wms.entity.OutStorageFlowEntity;
import com.platform.modules.wms.service.OutStorageFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 出库流水计划信息 Service实现类
 *
 * @author stg
 * @date 2019-09-04 16:19:19
 */
@Service("outStorageFlowService")
public class OutStorageFlowServiceImpl extends ServiceImpl<OutStorageFlowDao, OutStorageFlowEntity> implements OutStorageFlowService {

    @Autowired
    private OrderHeaderInfoDao orderHeaderInfoDao;

    @Override
    public List<OutStorageFlowEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.UPDATED_TIME");
        params.put("asc", false);
        Page<OutStorageFlowEntity> page = new Query<OutStorageFlowEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOutStorageFlowPage(page, params));
    }

    @Override
    public boolean add(OutStorageFlowEntity outStorageFlow) {
        return this.save(outStorageFlow);
    }

    @Override
    public boolean update(OutStorageFlowEntity outStorageFlow) {
        return this.updateById(outStorageFlow);
    }

    @Override
    public boolean delete(String bizCode) {
        return this.removeById(bizCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] bizCodes) {
        return this.removeByIds(Arrays.asList(bizCodes));
    }

    /***
     *  更新OutStorageFlowEntity和OrderHeaderInfoEntity的下单状态
     * @param customerOrderNo 客户订单号
     * @param data wms 订单号
     * @param inPlanOder 枚举类型
     */
    @Override
    public void updateByOrderNo(String customerOrderNo, String data, OrderStatus inPlanOder) {
        baseMapper.updatByOrderNo(customerOrderNo,data,inPlanOder.getCode(),inPlanOder.getStatusName());
        orderHeaderInfoDao.updatByOrderNo(customerOrderNo,inPlanOder.getCode(),inPlanOder.getStatusName());
    }
}
