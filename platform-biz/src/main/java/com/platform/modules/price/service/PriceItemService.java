/*
 * 项目名称:platform-plus
 * 类名称:PriceItemService.java
 * 包名称:com.platform.modules.price.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 09:48:39        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.price.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.price.entity.PriceItemEntity;

import java.util.List;
import java.util.Map;

/**
 * 价格项，基础数据Service接口
 *
 * @author Mr.panbb
 * @date 2020-03-24 09:48:39
 */
public interface PriceItemService extends IService<PriceItemEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<PriceItemEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询价格项，基础数据
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增价格项，基础数据
     *
     * @param priceItem 价格项，基础数据
     * @return 新增结果
     */
    boolean add(PriceItemEntity priceItem);

    /**
     * 根据主键更新价格项，基础数据
     *
     * @param priceItem 价格项，基础数据
     * @return 更新结果
     */
    boolean update(PriceItemEntity priceItem);

    /**
     * 根据主键删除价格项，基础数据
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
