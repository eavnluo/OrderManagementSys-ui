/*
 * 项目名称:platform-plus
 * 类名称:PriceInfoDao.java
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
import com.platform.modules.price.entity.PriceInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 价格信息Dao
 *
 * @author Mr.panbb
 * @date 2020-03-24 13:23:09
 */
@Mapper
public interface PriceInfoDao extends BaseMapper<PriceInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<PriceInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<PriceInfoEntity> selectPriceInfoPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 作废报价单
     *
     * @param id         信息ID
     * @param userId     更新人
     * @param updateTime 更新时间
     * @param status     更新状态
     */
    void updateStatus(@Param("id") String id,
                 @Param("userId") String userId,
                 @Param("updateTime") Date updateTime,
                 @Param("status") String status);

    /**
     * 将过了有效期的订单作废
     */
    void toInvalid();

    /**
     * 统计今天的报价单数
     * @return
     */
    int getCountForToday();

    /**
     * 根据费用项，查询相关报价供应商列表
     *
     * @param params 查询参数
     * @return List
     */
    List<Map<String, Object>> findSupplyList(@Param("params") Map<String, Object> params);
}
