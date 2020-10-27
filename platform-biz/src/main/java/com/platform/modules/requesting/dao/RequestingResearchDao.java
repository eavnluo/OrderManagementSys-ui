/*
 * 项目名称:platform-plus
 * 类名称:RequestingResearchDao.java
 * 包名称:com.platform.modules.requesting.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-18 14:01:57        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.requesting.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.requesting.entity.RequestingResearchEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 需求调研Dao
 *
 * @author Mr.panbb
 * @date 2020-03-18 14:01:57
 */
@Mapper
public interface RequestingResearchDao extends BaseMapper<RequestingResearchEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<RequestingResearchEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<RequestingResearchEntity> selectRequestingResearchPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 根据ID批量删除
     * @param list
     * @param updateTime
     * @param status
     * @return
     */
    boolean changeStatusByIds(@Param("list") List<String> list, @Param("updateTime") Date updateTime, @Param("status") String status);

    /**
     * 通过手机号获取信息
     * @param phoneNumber 手机号
     * @return
     */
    RequestingResearchEntity getByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
