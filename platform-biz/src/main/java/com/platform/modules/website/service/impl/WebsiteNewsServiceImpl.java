/*
 * 项目名称:platform-plus
 * 类名称:WebsiteNewsServiceImpl.java
 * 包名称:com.platform.modules.website.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-04-10 15:19:02        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.website.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.website.dao.WebsiteNewsDao;
import com.platform.modules.website.entity.WebsiteNewsEntity;
import com.platform.modules.website.service.WebsiteNewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author zlm
 * @date 2020-04-10 15:19:02
 */
@Service("websiteNewsService")
public class WebsiteNewsServiceImpl extends ServiceImpl<WebsiteNewsDao, WebsiteNewsEntity> implements WebsiteNewsService {

    @Override
    public List<WebsiteNewsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<WebsiteNewsEntity> page = new Query<WebsiteNewsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectWebsiteNewsPage(page, params));
    }

    @Override
    public WebsiteNewsEntity add(WebsiteNewsEntity websiteNews) {
        this.save(websiteNews);
        return websiteNews;
    }

    @Override
    public WebsiteNewsEntity update(WebsiteNewsEntity websiteNews) {
        this.updateById(websiteNews);
        return websiteNews;
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

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    @Override
    public WebsiteNewsEntity getNewsInfo(String id) {
        return baseMapper.getNewsInfo(id);
    }
}
