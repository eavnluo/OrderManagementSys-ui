/*
 * 项目名称:platform-plus
 * 类名称:OrderTrackInfoService.java
 * 包名称:com.platform.modules.order.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-11-05 08:52:24        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.order.entity.OrderTrackInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zlm
 * @date 2019-11-05 08:52:24
 */
public interface OrderTrackInfoService extends IService<OrderTrackInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderTrackInfoEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增
     *
     * @param orderTrackInfo 
     * @return 新增结果
     */
    boolean add(OrderTrackInfoEntity orderTrackInfo);

    /**
     * 根据主键更新
     *
     * @param orderTrackInfo 
     * @return 更新结果
     */
    boolean update(OrderTrackInfoEntity orderTrackInfo);

    /**
     * 根据主键删除
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
     * 根据工作单号获取追踪宝信息
     * @param orderItem 工作单号
     * @return 追踪宝信息
     */
    OrderTrackInfoEntity getByOrderItem(String orderItem);
}
