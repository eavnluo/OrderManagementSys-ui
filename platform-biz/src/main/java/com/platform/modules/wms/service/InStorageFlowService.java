/*
 * 项目名称:platform-plus
 * 类名称:InStorageFlowService.java
 * 包名称:com.platform.modules.in.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 14:28:34        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.common.OrderStatus;
import com.platform.modules.wms.entity.InStorageFlowEntity;

import java.util.List;
import java.util.Map;

/**
 * 入库订单数据 Service接口
 *
 * @author jk
 * @date 2019-09-04 14:28:34
 */
public interface InStorageFlowService extends IService<InStorageFlowEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<InStorageFlowEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询入库订单数据
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增入库订单数据
     *
     * @param inStorageFlow 入库订单数据
     * @return 新增结果
     */
    boolean add(InStorageFlowEntity inStorageFlow);

    /**
     * 根据主键更新入库订单数据
     *
     * @param inStorageFlow 入库订单数据
     * @return 更新结果
     */
    boolean update(InStorageFlowEntity inStorageFlow);

    /**
     * 根据主键删除入库订单数据
     *
     * @param bizCode bizCode
     * @return 删除结果
     */
    boolean delete(String bizCode);

    /**
     * 根据主键批量删除
     *
     * @param bizCodes bizCodes
     * @return 删除结果
     */
    boolean deleteBatch(String[] bizCodes);

    /***
     * @param customerOrderNo 客户订单号
     * @param data wms 订单号
     * @param inPlanOder 枚举类型
     */
    void updateByOrderNo(String customerOrderNo,String data, OrderStatus inPlanOder);
}
