/*
 * 项目名称:platform-plus
 * 类名称:BaseCustomerContactsService.java
 * 包名称:com.platform.modules.base.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-04-14 16:12:52        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.base.entity.BaseCustomerContactsEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zlm
 * @date 2020-04-14 16:12:52
 */
public interface BaseCustomerContactsService extends IService<BaseCustomerContactsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseCustomerContactsEntity> queryAll(Map<String, Object> params);

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
     * @param baseCustomerContacts
     * @return 新增结果
     */
    boolean add(BaseCustomerContactsEntity baseCustomerContacts);

    /**
     * 根据主键更新
     *
     * @param baseCustomerContacts
     * @return 更新结果
     */
    boolean update(BaseCustomerContactsEntity baseCustomerContacts);

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

    BaseCustomerContactsEntity getByPhone(String phone, String registerUserId);

    /**
     * 获取订单收货人发货人列表
     *
     * @param orderHeaderInfo
     * @return
     */
    List<BaseCustomerContactsEntity> getOrderContactsList(OrderHeaderInfoEntity orderHeaderInfo);
}
