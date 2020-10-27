/*
 * 项目名称:platform-plus
 * 类名称:BaseGoodsInfoDao.java
 * 包名称:com.platform.modules.base.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-02 17:29:38        jk     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.base.entity.BaseGoodsInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品基础信息表 Dao
 *
 * @author jk
 * @date 2019-09-02 17:29:38
 */
@Mapper
public interface BaseGoodsInfoDao extends BaseMapper<BaseGoodsInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseGoodsInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<BaseGoodsInfoEntity> selectBaseGoodsInfoPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 获取商品基础数据列表
     *
     * @param params 查询参数
     * @return list
     */
    List<Map<String, Object>> findList(@Param("params") Map<String, Object> params);


    List<BaseGoodsInfoEntity> findGoodsList();

    /**
     * 根据条形码查找商品信息
     * @param barCodeArray 条形码数组
     * @return List
     */
    List<BaseGoodsInfoEntity> selectByBarCode(String[] barCodeArray);
}
