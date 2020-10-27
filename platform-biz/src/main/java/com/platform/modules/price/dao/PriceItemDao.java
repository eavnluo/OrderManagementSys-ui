/*
 * 项目名称:platform-plus
 * 类名称:PriceItemDao.java
 * 包名称:com.platform.modules.price.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 09:48:39        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.price.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.price.entity.PriceItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 价格项，基础数据Dao
 *
 * @author Mr.panbb
 * @date 2020-03-24 09:48:39
 */
@Mapper
public interface PriceItemDao extends BaseMapper<PriceItemEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<PriceItemEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<PriceItemEntity> selectPriceItemPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 更新状态
     * @param idList 主键列表
     * @param status 状态值
     * @param updateTime 更新时间
     * @return
     */
    boolean updateStatus(@Param("idList") List<String> idList,
                         @Param("status") String status,
                         @Param("updateTime") Date updateTime);
}
