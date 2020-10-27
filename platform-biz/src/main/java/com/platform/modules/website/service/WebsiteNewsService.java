/*
 * 项目名称:platform-plus
 * 类名称:WebsiteNewsService.java
 * 包名称:com.platform.modules.website.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-04-10 15:19:02        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.website.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.website.entity.WebsiteNewsEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zlm
 * @date 2020-04-10 15:19:02
 */
public interface WebsiteNewsService extends IService<WebsiteNewsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<WebsiteNewsEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增
     *
     * @param websiteNews 
     * @return 新增结果
     */
    WebsiteNewsEntity add(WebsiteNewsEntity websiteNews);

    /**
     * 根据主键更新
     *
     * @param websiteNews 
     * @return 更新结果
     */
    WebsiteNewsEntity update(WebsiteNewsEntity websiteNews);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    WebsiteNewsEntity getNewsInfo(String id);
}
