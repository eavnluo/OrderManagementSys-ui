/*
 * 项目名称:platform-plus
 * 类名称:CooperationGuideDao.java
 * 包名称:com.platform.modules.cooperation.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-19 08:42:55        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.cooperation.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.cooperation.entity.CooperationGuideEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 合作指南Dao
 *
 * @author Mr.panbb
 * @date 2020-03-19 08:42:55
 */
@Mapper
public interface CooperationGuideDao extends BaseMapper<CooperationGuideEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<CooperationGuideEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<CooperationGuideEntity> selectCooperationGuidePage(IPage page, @Param("params") Map<String, Object> params);
}
