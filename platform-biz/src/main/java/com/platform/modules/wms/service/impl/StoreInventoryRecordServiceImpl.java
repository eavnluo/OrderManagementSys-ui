/*
 * 项目名称:platform-plus
 * 类名称:StoreInventoryRecordServiceImpl.java
 * 包名称:com.platform.modules.store.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-23 11:52:28        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.wms.dao.StoreInventoryRecordDao;
import com.platform.modules.wms.entity.StoreInventoryRecordEntity;
import com.platform.modules.wms.service.StoreInventoryRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author Mr.panbb
 * @date 2019-09-23 11:52:28
 */
@Service("storeInventoryRecordService")
public class StoreInventoryRecordServiceImpl extends ServiceImpl<StoreInventoryRecordDao, StoreInventoryRecordEntity>
        implements StoreInventoryRecordService {

    @Override
    public List<StoreInventoryRecordEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("asc", false);
        Page<StoreInventoryRecordEntity> page = new Query<StoreInventoryRecordEntity>(params).getPage();
        return page.setRecords(baseMapper.selectStoreInventoryRecordPage(page, params));
    }

    @Override
    public boolean add(StoreInventoryRecordEntity storeInventoryRecord) {
        return this.save(storeInventoryRecord);
    }

    @Override
    public boolean update(StoreInventoryRecordEntity storeInventoryRecord) {
        return this.updateById(storeInventoryRecord);
    }

    @Override
    public boolean delete(Double oldValue) {
        return this.removeById(oldValue);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(Double[] oldValues) {
        return this.removeByIds(Arrays.asList(oldValues));
    }
}
