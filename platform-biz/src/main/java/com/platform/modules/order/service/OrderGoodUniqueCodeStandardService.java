/*
 * 项目名称:platform-plus
 * 类名称:OrderGoodUniqueCodeStandardService.java
 * 包名称:com.platform.modules.order.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-12 13:22:59        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.order.entity.OrderGoodUniqueCodeStandardEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品唯一编码标准表 Service接口
 *
 * @author jk
 * @date 2019-09-12 13:22:59
 */
public interface OrderGoodUniqueCodeStandardService extends IService<OrderGoodUniqueCodeStandardEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderGoodUniqueCodeStandardEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询商品唯一编码标准表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增商品唯一编码标准表
     *
     * @param orderGoodUniqueCodeStandard 商品唯一编码标准表
     * @return 新增结果
     */
    boolean add(OrderGoodUniqueCodeStandardEntity orderGoodUniqueCodeStandard);

    /**
     * 根据主键更新商品唯一编码标准表
     *
     * @param orderGoodUniqueCodeStandard 商品唯一编码标准表
     * @return 更新结果
     */
    boolean update(OrderGoodUniqueCodeStandardEntity orderGoodUniqueCodeStandard);

    /**
     * 根据主键删除商品唯一编码标准表
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);

    /**
     *  根据内部单号查找商品唯一编码标准表
     * @param orderItem 内部单号
     * @return
     */
    List<OrderGoodUniqueCodeStandardEntity> findByOrderItem(String orderItem);
}
