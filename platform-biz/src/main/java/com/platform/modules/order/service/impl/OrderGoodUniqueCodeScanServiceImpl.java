/*
 * 项目名称:platform-plus
 * 类名称:OrderGoodUniqueCodeScanServiceImpl.java
 * 包名称:com.platform.modules.order.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-12 13:22:59        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.order.dao.OrderGoodUniqueCodeScanDao;
import com.platform.modules.order.entity.OrderGoodUniqueCodeScanEntity;
import com.platform.modules.order.service.OrderGoodUniqueCodeScanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品唯一编码扫描表 Service实现类
 *
 * @author jk
 * @date 2019-09-12 13:22:59
 */
@Service("orderGoodUniqueCodeScanService")
public class OrderGoodUniqueCodeScanServiceImpl
        extends ServiceImpl<OrderGoodUniqueCodeScanDao, OrderGoodUniqueCodeScanEntity>
        implements OrderGoodUniqueCodeScanService {

    @Override
    public List<OrderGoodUniqueCodeScanEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<OrderGoodUniqueCodeScanEntity> page = new Query<OrderGoodUniqueCodeScanEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOrderGoodUniqueCodeScanPage(page, params));
    }

    @Override
    public boolean add(OrderGoodUniqueCodeScanEntity orderGoodUniqueCodeScan) {
        return this.save(orderGoodUniqueCodeScan);
    }

    @Override
    public boolean update(OrderGoodUniqueCodeScanEntity orderGoodUniqueCodeScan) {
        return this.updateById(orderGoodUniqueCodeScan);
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
    public boolean isExist(String orderItem, String uniqueCode, String operateType) {
        OrderGoodUniqueCodeScanEntity scanEntity = new OrderGoodUniqueCodeScanEntity();
        scanEntity.setOrderItem(orderItem);
        scanEntity.setUniqueCode(uniqueCode);
        scanEntity.setOperateType(operateType);
        scanEntity.setOperateResult(OrderGoodUniqueCodeScanEntity.OPERATE_RESULT_SUCCESS);
        int count = baseMapper.selectCount(new QueryWrapper<OrderGoodUniqueCodeScanEntity>(scanEntity));
        if (count == 0) {
            return false;
        }
        return true;
    }

    /**
     * @param orderItem
     * @return
     */
    @Override
    public List<Map<String,Object>> listByOrderItem(String orderItem, String operateType) {
        return baseMapper.listByOrderItem(orderItem, operateType);
    }

    @Override
    public List<OrderGoodUniqueCodeScanEntity> getInfo(String[] orderItems, String goodsCode, String storeCode) {
        return baseMapper.getInfo(orderItems, goodsCode, storeCode);
    }

    /**
     * 更新商品出库状态
     * @param orderItem  工作单号
     * @param barCode    商品条码
     * @param goodsCode  商品编码
     */
    @Override
    public void updateBarCode(String orderItem, String barCode, String goodsCode) {
        baseMapper.updateBarCode(orderItem, barCode, goodsCode);
    }

    /**
     * 获取库存列表
     *
     * @param time    时间
     * @param pattern 格式
     * @return list
     */
    @Override
    public List<Map<String, Object>> findList(String time, String pattern) {
        return baseMapper.findList(time, pattern);
    }
}
