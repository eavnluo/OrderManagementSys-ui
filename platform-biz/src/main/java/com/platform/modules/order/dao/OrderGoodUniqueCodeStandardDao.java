/*
 * 项目名称:platform-plus
 * 类名称:OrderGoodUniqueCodeStandardDao.java
 * 包名称:com.platform.modules.order.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-12 13:22:59        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.order.entity.OrderGoodUniqueCodeStandardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品唯一编码标准表 Dao
 *
 * @author jk
 * @date 2019-09-12 13:22:59
 */
@Mapper
public interface OrderGoodUniqueCodeStandardDao extends BaseMapper<OrderGoodUniqueCodeStandardEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderGoodUniqueCodeStandardEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<OrderGoodUniqueCodeStandardEntity> selectOrderGoodUniqueCodeStandardPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 根据内部单号查找商品唯一编码标准表
     * @param orderItem 内部单号
     * @return
     */
    List<OrderGoodUniqueCodeStandardEntity> selectByOrderItem(@Param("orderItem") String orderItem);
}
