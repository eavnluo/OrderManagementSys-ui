/*
 * 项目名称:platform-plus
 * 类名称:OrderGoodUniqueCodeScanDao.java
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
import com.platform.modules.order.entity.OrderGoodUniqueCodeScanEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品唯一编码扫描表 Dao
 *
 * @author jk
 * @date 2019-09-12 13:22:59
 */
@Mapper
public interface OrderGoodUniqueCodeScanDao extends BaseMapper<OrderGoodUniqueCodeScanEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderGoodUniqueCodeScanEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<OrderGoodUniqueCodeScanEntity> selectOrderGoodUniqueCodeScanPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 根据订单信息获取扫描记录
     *
     * @param orderItem
     * @return
     */
    List<Map<String,Object>> listByOrderItem(@Param("orderItem") String orderItem, @Param("operateType") String operateType);

    /**
     * 根据工作单号、商品编号、仓库编码获取商品唯一扫描表中的信息
     *
     * @param orderItems 订单号数组
     * @param goodsCode  商品编号
     * @param storeCode  仓库编码
     * @return
     */
    List<OrderGoodUniqueCodeScanEntity> getInfo(@Param("orderItems") String[] orderItems, @Param("goodsCode") String goodsCode, @Param("storeCode") String storeCode);

    /**
     * 更新商品出库状态
     * @param orderItem  工作单号
     * @param barCode    商品条码
     * @param goodsCode  商品编码
     */
    void updateBarCode(@Param("orderItem") String orderItem, @Param("barCode") String barCode, @Param("goodsCode") String goodsCode);

    /**
     * 获取库存列表
     *
     * @param time    时间
     * @param pattern 格式
     * @return list
     */
    List<Map<String, Object>> findList(@Param("time") String time, @Param("pattern") String pattern);
}
