/*
 * 项目名称:platform-plus
 * 类名称:OrderRelationshipService.java
 * 包名称:com.platform.modules.order.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-11-05 10:34:40        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.order.entity.OrderRelationshipEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 凯晟admin
 * @date 2019-11-05 10:34:40
 */
public interface OrderRelationshipService extends IService<OrderRelationshipEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderRelationshipEntity> queryAll(Map<String, Object> params);

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
     * @param orderRelationship 
     * @return 新增结果
     */
    boolean add(OrderRelationshipEntity orderRelationship);

    /**
     * 根据主键更新
     *
     * @param orderRelationship 
     * @return 更新结果
     */
    boolean update(OrderRelationshipEntity orderRelationship);

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
     * 保存一条数据
     * @param orderItem 工作单号
     * @param wayBillNo 运输单号
     * @param createdUserID 创建人ID
     * @param updatedUserID 修改人ID
     * @return
     */
    boolean saveRelationship(String orderItem,String wayBillNo,String createdUserID,String updatedUserID);

    /**
     * 根据工作单号获取集合
     * @param orderItem 工作单号
     * @return
     */
    List<OrderRelationshipEntity> findByOrderItem(String orderItem);
}
