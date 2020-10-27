/*
 * 项目名称:platform-plus
 * 类名称:BaseLinkmanInfoServiceImpl.java
 * 包名称:com.platform.modules.base.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-26 11:30:23        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.base.dao.BaseLinkmanInfoDao;
import com.platform.modules.base.entity.BaseLinkmanInfoEntity;
import com.platform.modules.base.service.BaseLinkmanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author zlm
 * @date 2019-09-26 11:30:23
 */
@Service("baseLinkmanInfoService")
public class BaseLinkmanInfoServiceImpl extends ServiceImpl<BaseLinkmanInfoDao, BaseLinkmanInfoEntity> implements BaseLinkmanInfoService {

    @Autowired
    private BaseLinkmanInfoDao baseLinkmanInfoDao;

    @Override
    public List<BaseLinkmanInfoEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<BaseLinkmanInfoEntity> page = new Query<BaseLinkmanInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectBaseLinkmanInfoPage(page, params));
    }

    @Override
    public boolean add(BaseLinkmanInfoEntity baseLinkmanInfo) {
        return this.save(baseLinkmanInfo);
    }

    @Override
    public boolean update(BaseLinkmanInfoEntity baseLinkmanInfo) {
        return this.updateById(baseLinkmanInfo);
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
    public boolean updateDelFlagByBelongTo(String[] ids) {
        baseLinkmanInfoDao.updateDelFlagByBelongTo(ids);

        return true;
    }

    @Override
    public List<BaseLinkmanInfoEntity> findByBelongTo(String belongTo){
        return baseMapper.selectList(new QueryWrapper<BaseLinkmanInfoEntity>().eq("BELONG_TO", belongTo).eq("DEL_FLAG",0));
    }
}
