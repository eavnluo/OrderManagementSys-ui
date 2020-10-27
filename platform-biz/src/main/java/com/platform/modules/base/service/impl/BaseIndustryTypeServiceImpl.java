/*
 * 项目名称:platform-plus
 * 类名称:BaseIndustryTypeServiceImpl.java
 * 包名称:com.platform.modules.base.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 10:01:36        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.base.dao.BaseIndustryTypeDao;
import com.platform.modules.base.entity.BaseIndustryTypeEntity;
import com.platform.modules.base.service.BaseIndustryTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author zlm
 * @date 2020-03-18 10:01:36
 */
@Service("baseIndustryTypeService")
public class BaseIndustryTypeServiceImpl extends ServiceImpl<BaseIndustryTypeDao, BaseIndustryTypeEntity> implements BaseIndustryTypeService {

    @Override
    public List<BaseIndustryTypeEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<BaseIndustryTypeEntity> page = new Query<BaseIndustryTypeEntity>(params).getPage();
        return page.setRecords(baseMapper.selectBaseIndustryTypePage(page, params));
    }

    @Override
    public boolean add(BaseIndustryTypeEntity baseIndustryType) {
        return this.save(baseIndustryType);
    }

    @Override
    public boolean update(BaseIndustryTypeEntity baseIndustryType) {
        return this.updateById(baseIndustryType);
    }

    @Override
    public boolean delete(Integer id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(Integer[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
