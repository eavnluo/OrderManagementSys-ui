/*
 * 项目名称:platform-plus
 * 类名称:OrderInterfaceLogService.java
 * 包名称:com.platform.modules.order.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-10-23 09:23:54        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.order.entity.OrderInterfaceLogEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 凯晟admin
 * @date 2019-10-23 09:23:54
 */
public interface OrderInterfaceLogService extends IService<OrderInterfaceLogEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderInterfaceLogEntity> queryAll(Map<String, Object> params);

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
     * @param orderInterfaceLog 
     * @return 新增结果
     */
    boolean add(OrderInterfaceLogEntity orderInterfaceLog);

    /**
     * 根据主键更新
     *
     * @param orderInterfaceLog 
     * @return 更新结果
     */
    boolean update(OrderInterfaceLogEntity orderInterfaceLog);

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
     * 保存日志
     * @param orderItem 工作单号
     * @param url 请求地址
     * @param params 请求参数
     * @param result 响应结果
     * @param type 操作类型
     * @return
     */
    boolean saveLog(String orderItem,String url,String params,String result,String type);
}
