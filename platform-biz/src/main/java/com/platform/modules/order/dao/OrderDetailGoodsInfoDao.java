/*
 * 项目名称:platform-plus
 * 类名称:OrderDetailGoodInfoDao.java
 * 包名称:com.platform.modules.order.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:30        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单详情表 Dao
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:30
 */
@Mapper
public interface OrderDetailGoodsInfoDao extends BaseMapper<OrderDetailGoodsInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderDetailGoodsInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<OrderDetailGoodsInfoEntity> selectOrderDetailGoodInfoPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 根据工作单号获取订单包含的商品列表
     *
     * @param orderItem 工作单号
     * @return list
     */
    List<OrderDetailGoodsInfoEntity> findByOrderItem(@Param("orderItem") String orderItem);

    /**
     * 通过工作单号查询对应的
     * @param orderItem
     * @return
     */
    List<OrderDetailGoodsInfoEntity> listByOrderItem(@Param("orderItem") String orderItem);
    
    /**
     * 根据内部订单编号获取商品数量
     * @param orderItem
     * @return
     */
    String queryGoodCount(String orderItem);

    /**
     *
     */
}
