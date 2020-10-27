/*
 * 项目名称:platform-plus
 * 类名称:CooperationGuideServiceImpl.java
 * 包名称:com.platform.modules.cooperation.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-19 08:42:55        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.cooperation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.cooperation.dao.CooperationGuideDao;
import com.platform.modules.cooperation.entity.CooperationGuideEntity;
import com.platform.modules.cooperation.service.CooperationGuideService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 合作指南Service实现类
 *
 * @author Mr.panbb
 * @date 2020-03-19 08:42:55
 */
@Service("cooperationGuideService")
public class CooperationGuideServiceImpl extends ServiceImpl<CooperationGuideDao, CooperationGuideEntity> implements CooperationGuideService {

    @Override
    public List<CooperationGuideEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<CooperationGuideEntity> page = new Query<CooperationGuideEntity>(params).getPage();
        return page.setRecords(baseMapper.selectCooperationGuidePage(page, params));
    }

    @Override
    public boolean add(CooperationGuideEntity cooperationGuide) {
        return this.save(cooperationGuide);
    }

    @Override
    public boolean update(CooperationGuideEntity cooperationGuide) {
        return this.updateById(cooperationGuide);
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
