/*
 * 项目名称:platform-plus
 * 类名称:BaseSupplyInfoDao.java
 * 包名称:com.platform.modules.base.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-03 17:21:59        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 供应商信息管理 Dao
 *
 * @author jk
 * @date 2019-09-03 17:21:59
 */
@Mapper
public interface BaseSupplyInfoDao extends BaseMapper<BaseSupplyInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseSupplyInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<BaseSupplyInfoEntity> selectBaseSupplyInfoPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 查询工厂信息列表
     *
     * @param params 查询条件
     * @return list
     */
    List<Map<String, Object>> findeList(Map<String, Object> params);


    /**
     * 查询供应商信息
     */
    List<BaseSupplyInfoEntity> getBySupply();

    /**
     * 查询仓库信息
     */
    List<BaseSupplyInfoEntity> getBySupplyType();

    /**
     * 查询仓库联系人信息
     */
    List<Map<String, String>> getSupplyInfo();

    /**
     * 查询库房数量
     *
     * @param isYesterday 昨天的
     * @return
     */
    int findNumberForChart(@Param("isYesterday") boolean isYesterday);

    /**
     * 根据供应商类型获取已经报价的供应商列表
     *
     * @param companyCode 公司代码
     * @param type        供应商类型
     * @return 供应商列表
     */
    List<BaseSupplyInfoEntity> findListByType(@Param("companyCode") String companyCode, @Param("type") String type);
}
