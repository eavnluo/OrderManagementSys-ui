/*
 * 项目名称:platform-plus
 * 类名称:BaseCarInfoDao.java
 * 包名称:com.platform.modules.base.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-29 09:32:01        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.modules.base.entity.BaseCarInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author zlm
 * @date 2019-09-29 09:32:01
 */
@Mapper
public interface BaseCarInfoDao extends BaseMapper<BaseCarInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseCarInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<BaseCarInfoEntity> selectBaseCarInfoPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 根据归属修改对应数据的删除标识
     * @param ids 归属ids
     * @return 受影响的行数
     */
    Integer updateDelFlagByBelongTo(String[] ids);

    /**
     * 关联供应商分页查询
     * @param page 分页参数
     * @param params 查询参数
     * @return List
     */
    List<BaseCarInfoEntity> findPage(IPage page, @Param("params") Map<String, Object> params);
}
