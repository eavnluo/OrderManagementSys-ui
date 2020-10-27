/*
 * 项目名称:platform-plus
 * 类名称:PriceDetailDao.java
 * 包名称:com.platform.modules.price.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 13:23:09        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.price.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.price.entity.PriceDetailEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 报价单包含的报价项清单Dao
 *
 * @author Mr.panbb
 * @date 2020-03-24 13:23:09
 */
@Mapper
public interface PriceDetailDao extends BaseMapper<PriceDetailEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<PriceDetailEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<PriceDetailEntity> selectPriceDetailPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 获取价格明细
     *
     * @param params 参数
     * @return 明细
     */
    PriceDetailEntity get(@Param("params") Map<String, Object> params);

    /**
     * 获取项目分类列表
     * @param companyCode
     * @param list
     * @return
     */
    List<PriceDetailEntity> findTypeList(@Param("companyCode") String companyCode, @Param("list") List<String> list);

    /**
     * 查询费用清单列表
     *
     * @param params 查询参数
     * @return list
     */
    List<PriceDetailEntity> findList(@Param("params") Map<String, Object> params);
}
