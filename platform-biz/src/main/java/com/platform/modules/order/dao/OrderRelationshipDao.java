/*
 * 项目名称:platform-plus
 * 类名称:OrderRelationshipDao.java
 * 包名称:com.platform.modules.order.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-11-05 10:34:40        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.order.entity.OrderRelationshipEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 凯晟admin
 * @date 2019-11-05 10:34:40
 */
@Mapper
public interface OrderRelationshipDao extends BaseMapper<OrderRelationshipEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderRelationshipEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<OrderRelationshipEntity> selectOrderRelationshipPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 根据工作单号获取集合
     * @param orderItem 工作单号
     * @return
     */
    List<OrderRelationshipEntity> findByOrderItem(String orderItem);
}
