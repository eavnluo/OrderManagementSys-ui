/*
 * 项目名称:platform-plus
 * 类名称:BaseAreaDao.java
 * 包名称:com.platform.modules.base.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-30 09:33:03        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.base.entity.BaseAreaEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 凯晟admin
 * @date 2019-09-30 09:33:03
 */
@Mapper
public interface BaseAreaDao extends BaseMapper<BaseAreaEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseAreaEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<BaseAreaEntity> selectBaseAreaPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 通过名称查询省市编号
     * @param name
     * @return
     */
    String queryCode(@Param("NAME") String name);
}
