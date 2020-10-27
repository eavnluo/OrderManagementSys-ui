/*
 * 项目名称:platform-plus
 * 类名称:BaseStatusCodeDao.java
 * 包名称:com.platform.modules.base.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-09 08:24:39        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.base.entity.BaseStatusCodeEntity;
import com.platform.modules.base.entity.BaseStatusGroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 状态维护表 Dao
 *
 * @author 凯晟admin
 * @date 2019-09-09 08:24:39
 */
@Mapper
public interface BaseStatusCodeDao extends BaseMapper<BaseStatusCodeEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseStatusCodeEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<BaseStatusCodeEntity> selectBaseStatusCodePage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 获取状态列表
     * @return
     */
    List<BaseStatusCodeEntity> getStatusGroup();

    /**
     * 获取分组
     * @return
     */
    List<BaseStatusGroupEntity> getGroups();
}
