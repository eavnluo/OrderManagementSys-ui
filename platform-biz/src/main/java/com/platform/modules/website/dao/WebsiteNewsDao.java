/*
 * 项目名称:platform-plus
 * 类名称:WebsiteNewsDao.java
 * 包名称:com.platform.modules.website.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-04-10 15:19:02        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.website.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.website.entity.WebsiteNewsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author zlm
 * @date 2020-04-10 15:19:02
 */
@Mapper
public interface WebsiteNewsDao extends BaseMapper<WebsiteNewsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<WebsiteNewsEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<WebsiteNewsEntity> selectWebsiteNewsPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    WebsiteNewsEntity getNewsInfo(@Param("id") String id);
}
