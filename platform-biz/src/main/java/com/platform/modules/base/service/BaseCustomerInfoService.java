/*
 * 项目名称:platform-plus
 * 类名称:BaseCustomerInfoService.java
 * 包名称:com.platform.modules.base.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-20 15:00:22        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 客户信息表Service接口
 *
 * @author jk
 * @date 2019-09-20 15:00:22
 */
public interface BaseCustomerInfoService extends IService<BaseCustomerInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseCustomerInfoEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询客户信息表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增客户信息表
     *
     * @param baseCustomerInfo 客户信息表
     * @return 新增结果
     */
    boolean add(BaseCustomerInfoEntity baseCustomerInfo);

    /**
     * 根据主键更新客户信息表
     *
     * @param baseCustomerInfo 客户信息表
     * @return 更新结果
     */
    boolean update(BaseCustomerInfoEntity baseCustomerInfo);

    /**
     * 根据主键删除客户信息表
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
     * 获取客户列表，买方、卖方
     * @return Object
     */

    JSONObject findCustomerList();

    List<BaseCustomerInfoEntity> findCustomerLists();

    /**
     * 根据id查找客户信息
     * @param id id
     * @return 实体
     */
    BaseCustomerInfoEntity queryById(String id);

    /**
     * 获取格式化的数据：采购商、分销商、工厂
     * @return
     */
    JSONArray findFormatList();

    List<BaseCustomerInfoEntity> getByOwner();

    /**
     * 根据客户编码查找客户信息
     * @param customerCode 客户编码
     * @return 实体
     */
    BaseCustomerInfoEntity findByCode(String customerCode);

    /**
     * 转化成仓库的格式
     * @return
     */
    JSONArray findFormatStoreList();

    /**
     * 生成客户代码
     * @param customerName 公司名称
     * @param city 城市名称
     * @return
     */
    String generateCustomerCode(String customerName,String city);

    /**
     * 根据注册用户ID查询信息
     * @param registerUserId 注册用户ID
     * @return
     */
    BaseCustomerInfoEntity findByRegistryUserId(String registerUserId);

    /**
     * 根据订单编工作单号查询客户信息
     * @param orderItem 工作单号
     * @return 客户信息
     */
    BaseCustomerInfoEntity getByOrderItem(String orderItem);
}
