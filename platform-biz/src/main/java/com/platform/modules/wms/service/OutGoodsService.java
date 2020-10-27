/*
 * 项目名称:platform-plus
 * 类名称:OutGoodsService.java
 * 包名称:com.platform.modules.wms.out.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 10:22:41        stg     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.wms.entity.OutGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 出库商品列表信息 Service接口
 *
 * @author stg
 * @date 2019-09-05 10:22:41
 */
public interface OutGoodsService extends IService<OutGoodsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OutGoodsEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询出库商品列表信息 
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增出库商品列表信息 
     *
     * @param outGoods 出库商品列表信息 
     * @return 新增结果
     */
    boolean add(OutGoodsEntity outGoods);

    /**
     * 根据主键更新出库商品列表信息 
     *
     * @param outGoods 出库商品列表信息 
     * @return 更新结果
     */
    boolean update(OutGoodsEntity outGoods);

    /**
     * 根据主键删除出库商品列表信息 
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
