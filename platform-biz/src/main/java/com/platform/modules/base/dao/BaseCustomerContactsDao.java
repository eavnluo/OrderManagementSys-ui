/*
 * 项目名称:platform-plus
 * 类名称:BaseCustomerContactsDao.java
 * 包名称:com.platform.modules.base.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-04-14 16:12:52        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.base.entity.BaseCustomerContactsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author zlm
 * @date 2020-04-14 16:12:52
 */
@Mapper
public interface BaseCustomerContactsDao extends BaseMapper<BaseCustomerContactsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseCustomerContactsEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<BaseCustomerContactsEntity> selectBaseCustomerContactsPage(IPage page, @Param("params") Map<String, Object> params);
}
