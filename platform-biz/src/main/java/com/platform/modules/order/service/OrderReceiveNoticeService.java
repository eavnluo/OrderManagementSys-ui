/*
 * 项目名称:platform-plus
 * 类名称:OrderReceiveNoticeService.java
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
import com.platform.modules.order.entity.OrderReceiveNoticeEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单消息接收表 Service接口
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:31
 */
public interface OrderReceiveNoticeService extends IService<OrderReceiveNoticeEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderReceiveNoticeEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询订单消息接收表 
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增订单消息接收表 
     *
     * @param orderReceiveNotice 订单消息接收表 
     * @return 新增结果
     */
    boolean add(OrderReceiveNoticeEntity orderReceiveNotice);

    /**
     * 根据主键更新订单消息接收表 
     *
     * @param orderReceiveNotice 订单消息接收表 
     * @return 更新结果
     */
    boolean update(OrderReceiveNoticeEntity orderReceiveNotice);

    /**
     * 根据主键删除订单消息接收表 
     *
     * @param delFlag delFlag
     * @return 删除结果
     */
    boolean delete(String delFlag);

    /**
     * 根据主键批量删除
     *
     * @param delFlags delFlags
     * @return 删除结果
     */
    boolean deleteBatch(String[] delFlags);
}
