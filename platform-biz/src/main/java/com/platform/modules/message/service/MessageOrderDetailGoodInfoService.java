/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderDetailGoodInfoService.java
 * 包名称:com.platform.modules.message.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 14:53:26        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.message.entity.MessageOrderDetailGoodInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 订单详情表  Service接口
 *
 * @author 凯晟admin
 * @date 2019-09-10 14:53:26
 */
public interface MessageOrderDetailGoodInfoService extends IService<MessageOrderDetailGoodInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<MessageOrderDetailGoodInfoEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询订单详情表  
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增订单详情表  
     *
     * @param messageOrderDetailGoodInfo 订单详情表  
     * @return 新增结果
     */
    boolean add(MessageOrderDetailGoodInfoEntity messageOrderDetailGoodInfo);

    /**
     * 根据主键更新订单详情表  
     *
     * @param messageOrderDetailGoodInfo 订单详情表  
     * @return 更新结果
     */
    boolean update(MessageOrderDetailGoodInfoEntity messageOrderDetailGoodInfo);

    /**
     * 根据主键删除订单详情表  
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
