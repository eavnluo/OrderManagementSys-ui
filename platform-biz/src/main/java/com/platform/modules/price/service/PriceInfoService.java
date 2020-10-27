/*
 * 项目名称:platform-plus
 * 类名称:PriceInfoService.java
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
import com.platform.modules.price.entity.PriceInfoEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 价格信息Service接口
 *
 * @author Mr.panbb
 * @date 2020-03-24 13:23:09
 */
public interface PriceInfoService extends IService<PriceInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<PriceInfoEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询价格信息
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增价格信息
     *
     * @param priceInfo 价格信息
     * @return 新增结果
     */
    boolean add(PriceInfoEntity priceInfo);

    /**
     * 根据主键更新价格信息
     *
     * @param priceInfo 价格信息
     * @return 更新结果
     */
    boolean update(PriceInfoEntity priceInfo);

    /**
     * 根据主键删除价格信息
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
     * 创建报价单编号
     * @return priceNo
     */
    String createPriceNo();

    /**
     * 作废报价单
     * @param id         信息ID
     * @param userId     更新人
     * @param updateTime 更新时间
     * @param status     状态
     */
    void updateStatus(String id, String userId, Date updateTime, String status);

    /**
     * 将过了有效期的订单作废
     */
    void toInvalid();

    /**
     * 根据费用项，查询相关报价供应商列表
     * @param params 查询参数
     * @return List
     */
    List<Map<String, Object>> findSupplyList(Map<String, Object> params);
}
