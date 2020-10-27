/*
 * 项目名称:platform-plus
 * 类名称:InPlanGoodsDao.java
 * 包名称:com.platform.modules.in.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 11:00:36        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.wms.entity.InPlanGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品集合信息 Dao
 *
 * @author jk
 * @date 2019-09-05 11:00:36
 */
@Mapper
public interface InPlanGoodsDao extends BaseMapper<InPlanGoodsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<InPlanGoodsEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<InPlanGoodsEntity> selectInPlanGoodsPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 通过商品编码查询商品规格
     * @param goodsCode
     * @return
     */
    String queryPackUnit(@Param("goodsCode") String goodsCode);
}
