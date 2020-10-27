/*
 * 项目名称:platform-plus
 * 类名称:OutGoodsServiceImpl.java
 * 包名称:com.platform.modules.wms.out.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 10:22:41        stg     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.wms.dao.OutGoodsDao;
import com.platform.modules.wms.entity.OutGoodsEntity;
import com.platform.modules.wms.service.OutGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 出库商品列表信息 Service实现类
 *
 * @author stg
 * @date 2019-09-05 10:22:41
 */
@Service("outGoodsService")
public class OutGoodsServiceImpl extends ServiceImpl<OutGoodsDao, OutGoodsEntity> implements OutGoodsService {

    @Override
    public List<OutGoodsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<OutGoodsEntity> page = new Query<OutGoodsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOutGoodsPage(page, params));
    }

    @Override
    public boolean add(OutGoodsEntity outGoods) {
        return this.save(outGoods);
    }

    @Override
    public boolean update(OutGoodsEntity outGoods) {
        return this.updateById(outGoods);
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
