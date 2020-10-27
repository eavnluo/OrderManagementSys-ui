/*
 * 项目名称:platform-plus
 * 类名称:PriceDetailService.java
 * 包名称:com.platform.modules.price.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 13:23:09        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.price.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.price.entity.PriceDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * 报价单包含的报价项清单Service接口
 *
 * @author Mr.panbb
 * @date 2020-03-24 13:23:09
 */
public interface PriceDetailService extends IService<PriceDetailEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<PriceDetailEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询报价单包含的报价项清单
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增报价单包含的报价项清单
     *
     * @param priceDetail 报价单包含的报价项清单
     * @return 新增结果
     */
    boolean add(PriceDetailEntity priceDetail);

    /**
     * 根据主键更新报价单包含的报价项清单
     *
     * @param priceDetail 报价单包含的报价项清单
     * @return 更新结果
     */
    boolean update(PriceDetailEntity priceDetail);

    /**
     * 根据主键删除报价单包含的报价项清单
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
     * 获取价格明细
     * @param params 参数
     * @return 明细
     */
    PriceDetailEntity get(Map<String, Object> params);

    /**
     * 获取项目分类列表
     * @param params 查询参数
     * @return list
     */
   List<PriceDetailEntity> findTypeList(Map<String, Object> params);

    /**
     * 查询费用清单列表
     * @param params 查询参数
     * @return list
     */
    List<PriceDetailEntity> findList(Map<String, Object> params);
}
