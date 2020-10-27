/*
 * 项目名称:platform-plus
 * 类名称:PriceItemServiceImpl.java
 * 包名称:com.platform.modules.price.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 09:48:39        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.price.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.price.dao.PriceItemDao;
import com.platform.modules.price.entity.PriceItemEntity;
import com.platform.modules.price.service.PriceItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 价格项，基础数据Service实现类
 *
 * @author Mr.panbb
 * @date 2020-03-24 09:48:39
 */
@Service("priceItemService")
public class PriceItemServiceImpl extends ServiceImpl<PriceItemDao, PriceItemEntity> implements PriceItemService {

    @Override
    public List<PriceItemEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<PriceItemEntity> page = new Query<PriceItemEntity>(params).getPage();
        return page.setRecords(baseMapper.selectPriceItemPage(page, params));
    }

    @Override
    public boolean add(PriceItemEntity priceItem) {
        return this.save(priceItem);
    }

    @Override
    public boolean update(PriceItemEntity priceItem) {
        return this.updateById(priceItem);
    }

    @Override
    public boolean delete(String id) {
        String[] ids = {id};
        return deleteBatch(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return baseMapper.updateStatus(Arrays.asList(ids), "1", new Date());
    }
}
