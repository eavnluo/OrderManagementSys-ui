/*
 * 项目名称:platform-plus
 * 类名称:StoreOutFlowStatusServiceImpl.java
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

import com.platform.modules.wms.dao.StoreOutFlowStatusDao;
import com.platform.modules.wms.entity.StoreOutFlowStatusEntity;
import com.platform.modules.wms.service.StoreOutFlowStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *  出库订单状态流水表Service实现类
 *
 * @author 凯晟admin
 * @date 2019-09-07 16:36:16
 */
@Service("storeOutFlowStatusService")
public class StoreOutFlowStatusServiceImpl extends ServiceImpl<StoreOutFlowStatusDao, StoreOutFlowStatusEntity> implements StoreOutFlowStatusService {

    @Override
    public List<StoreOutFlowStatusEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<StoreOutFlowStatusEntity> page = new Query<StoreOutFlowStatusEntity>(params).getPage();
        return page.setRecords(baseMapper.selectStoreOutFlowStatusPage(page, params));
    }

    @Override
    public boolean add(StoreOutFlowStatusEntity storeOutFlowStatus) {
        return this.save(storeOutFlowStatus);
    }

    @Override
    public boolean update(StoreOutFlowStatusEntity storeOutFlowStatus) {
        return this.updateById(storeOutFlowStatus);
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
