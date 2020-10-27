/*
 * 项目名称:platform-plus
 * 类名称:StatementItemService.java
 * 包名称:com.platform.modules.statement.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-30 14:33:17        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.bill.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.bill.entity.BillItemEntity;

import java.util.List;
import java.util.Map;

/**
 * 结算项Service接口
 *
 * @author Mr.panbb
 * @date 2020-03-30 14:33:17
 */
public interface BillItemService extends IService<BillItemEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BillItemEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询结算项
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增结算项
     *
     * @param statementItem 结算项
     * @return 新增结果
     */
    boolean add(BillItemEntity statementItem);

    /**
     * 根据主键更新结算项
     *
     * @param statementItem 结算项
     * @return 更新结果
     */
    boolean update(BillItemEntity statementItem);

    /**
     * 根据主键删除结算项
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
     * 根据infoId删除
     * @param billInfoId
     * @return
     */
    boolean deleteByInfoId(String billInfoId);
}
