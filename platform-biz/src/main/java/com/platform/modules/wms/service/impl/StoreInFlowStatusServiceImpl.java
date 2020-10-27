/*
 * 项目名称:platform-plus
 * 类名称:StoreInFlowStatusServiceImpl.java
 * 包名称:com.platform.modules.store.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-07 16:36:16        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.wms.dao.StoreInFlowStatusDao;
import com.platform.modules.wms.entity.StoreInFlowStatusEntity;
import com.platform.modules.wms.service.StoreInFlowStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 入库订单状态 Service实现类
 *
 * @author 凯晟admin
 * @date 2019-09-07 16:36:16
 */
@Service("storeInFlowStatusService")
public class StoreInFlowStatusServiceImpl extends ServiceImpl<StoreInFlowStatusDao, StoreInFlowStatusEntity> implements StoreInFlowStatusService {

    @Override
    public List<StoreInFlowStatusEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<StoreInFlowStatusEntity> page = new Query<StoreInFlowStatusEntity>(params).getPage();
        return page.setRecords(baseMapper.selectStoreInFlowStatusPage(page, params));
    }

    @Override
    public boolean add(StoreInFlowStatusEntity storeInFlowStatus) {
        return this.save(storeInFlowStatus);
    }

    @Override
    public boolean update(StoreInFlowStatusEntity storeInFlowStatus) {
        return this.updateById(storeInFlowStatus);
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
