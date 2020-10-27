/*
 * 项目名称:platform-plus
 * 类名称:OrderStatusFlowFileService.java
 * 包名称:com.platform.modules.order.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-07 11:23:38        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.order.entity.OrderStatusFlowFileEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zlm
 * @date 2020-03-07 11:23:38
 */
public interface OrderStatusFlowFileService extends IService<OrderStatusFlowFileEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderStatusFlowFileEntity> queryAll(Map<String, Object> params);

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
     * @param orderStatusFlowFile 
     * @return 新增结果
     */
    boolean add(OrderStatusFlowFileEntity orderStatusFlowFile);

    /**
     * 根据主键更新
     *
     * @param orderStatusFlowFile 
     * @return 更新结果
     */
    boolean update(OrderStatusFlowFileEntity orderStatusFlowFile);

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
}
