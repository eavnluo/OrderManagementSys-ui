/*
 * 项目名称:platform-plus
 * 类名称:RequestingResearchServiceImpl.java
 * 包名称:com.platform.modules.requesting.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 14:01:57        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.requesting.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Query;
import com.platform.common.utils.StringUtils;
import com.platform.modules.requesting.dao.RequestingResearchDao;
import com.platform.modules.requesting.entity.RequestingResearchEntity;
import com.platform.modules.requesting.service.RequestingResearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 需求调研Service实现类
 *
 * @author Mr.panbb
 * @date 2020-03-18 14:01:57
 */
@Service("requestingResearchService")
public class RequestingResearchServiceImpl extends ServiceImpl<RequestingResearchDao, RequestingResearchEntity> implements RequestingResearchService {

    @Override
    public List<RequestingResearchEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("asc", false);
        Page<RequestingResearchEntity> page = new Query<RequestingResearchEntity>(params).getPage();
        return page.setRecords(baseMapper.selectRequestingResearchPage(page, params));
    }

    @Override
    public boolean add(RequestingResearchEntity requestingResearch) {
        if (StringUtils.isBlank(requestingResearch.getPhoneNumber())) {
            throw new BusinessException("手机号不能为空");
        }
        RequestingResearchEntity researchEntity = baseMapper.getByPhoneNumber(requestingResearch.getPhoneNumber());
        if (researchEntity != null) {
            throw new BusinessException("该手机号已经被注册");
        }
        requestingResearch.setCreateTime(new Date());
        return this.save(requestingResearch);
    }

    @Override
    public boolean update(RequestingResearchEntity requestingResearch) {
        requestingResearch.setUpdateTime(new Date());
        return this.updateById(requestingResearch);
    }

    @Override
    public boolean delete(String id) {
        String[] ids = {id};
        return this.deleteBatch(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return baseMapper.changeStatusByIds(Arrays.asList(ids), new Date(), "1");
    }
}
