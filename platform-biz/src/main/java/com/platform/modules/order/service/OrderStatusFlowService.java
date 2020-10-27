/*
 * 项目名称:platform-plus
 * 类名称:OrderStatusFlowService.java
 * 包名称:com.platform.modules.order.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:31        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.order.entity.OrderStatusFlowEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单状态流水表 Service接口
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:31
 */
public interface OrderStatusFlowService extends IService<OrderStatusFlowEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderStatusFlowEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询订单状态流水表 
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增订单状态流水表 
     *
     * @param orderStatusFlow 订单状态流水表 
     * @return 新增结果
     */
    boolean add(OrderStatusFlowEntity orderStatusFlow);

    /**
     * 根据主键更新订单状态流水表 
     *
     * @param orderStatusFlow 订单状态流水表 
     * @return 更新结果
     */
    boolean update(OrderStatusFlowEntity orderStatusFlow);

    /**
     * 根据主键删除订单状态流水表 
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
}
