/*
 * 项目名称:platform-plus
 * 类名称:InStorageFlowServiceImpl.java
 * 包名称:com.platform.modules.in.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 14:28:34        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.OrderStatus;
import com.platform.common.utils.Query;
import com.platform.modules.order.dao.OrderHeaderInfoDao;
import com.platform.modules.wms.dao.InStorageFlowDao;
import com.platform.modules.wms.entity.InStorageFlowEntity;
import com.platform.modules.wms.service.InStorageFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 入库订单数据 Service实现类
 *
 * @author jk
 * @date 2019-09-04 14:28:34
 */
@Service("inStorageFlowService")
public class InStorageFlowServiceImpl extends ServiceImpl<InStorageFlowDao, InStorageFlowEntity> implements InStorageFlowService {
    @Autowired
    private OrderHeaderInfoDao orderHeaderInfoDao;
    @Override
    public List<InStorageFlowEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.UPDATE_STATUS_DATE");
        params.put("asc", false);
        Page<InStorageFlowEntity> page = new Query<InStorageFlowEntity>(params).getPage();
        return page.setRecords(baseMapper.selectInStorageFlowPage(page, params));
    }

    @Override
    public boolean add(InStorageFlowEntity inStorageFlow) {
        Date now = new Date();
        inStorageFlow.setCreatedTime(now);
        inStorageFlow.setUpdatedTime(now);
        return this.save(inStorageFlow);
    }

    @Override
    public boolean update(InStorageFlowEntity inStorageFlow) {
        inStorageFlow.setUpdatedTime(new Date());
        return this.updateById(inStorageFlow);
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
     *  更新InStorageFlowEntity和OrderHeaderInfoEntity的下单状态
     * @param customerOrderNo 客户订单号
     * @param data wms 订单号
     * @param inPlanOder 枚举类型
     */
    @Override
    public void updateByOrderNo(String customerOrderNo,String data, OrderStatus inPlanOder) {

        baseMapper.updatByOrderNo(customerOrderNo,data,inPlanOder.getCode(),inPlanOder.getStatusName());
        orderHeaderInfoDao.updatByOrderNo(customerOrderNo,inPlanOder.getCode(),inPlanOder.getStatusName());

    }
}
