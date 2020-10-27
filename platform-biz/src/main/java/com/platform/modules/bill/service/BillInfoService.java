/*
 * 项目名称:platform-plus
 * 类名称:billInfoEntityService.java
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
import com.platform.modules.bill.entity.BillInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 结算单Service接口
 *
 * @author Mr.panbb
 * @date 2020-03-30 14:33:17
 */
public interface BillInfoService extends IService<BillInfoEntity> {

	/**
	 * 查询所有列表
	 *
	 * @param params 查询参数
	 * @return List
	 */
	List<BillInfoEntity> queryAll(Map<String, Object> params);

	/**
	 * 分页查询结算单
	 *
	 * @param params 查询参数
	 * @return Page
	 */
	Page queryPage(Map<String, Object> params);

	/**
	 * 新增结算单
	 *
	 * @param billInfoEntity 结算单
	 * @return 新增结果
	 */
	boolean add(BillInfoEntity billInfoEntity);

	/**
	 * 根据主键更新结算单
	 *
	 * @param billInfoEntity 结算单
	 * @return 更新结果
	 */
	boolean update(BillInfoEntity billInfoEntity);

	/**
	 * 根据主键删除结算单
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
	 * 生成结算单编号
	 *
	 * @return
	 */
	String createBillNo();

	/**
	 * 按月份汇总
	 *
	 * @param params 查询条件
	 * @return
	 */
	Page getListGroupMonth(Map<String, Object> params);

	/**
	 * 获取指定年月列表
	 *
	 * @param params 查询条件
	 * @return
	 */
	Page getListByMonth(Map<String, Object> params);

	/**
	 * 获取指定订单号列表
	 *
	 * @param params 查询条件
	 * @return
	 */
	List<BillInfoEntity> getListByOrderNo(Map<String, Object> params);

	/**
	 * 根据工作单号获取账单信息
	 *
	 * @param params
	 * @return
	 */
	BillInfoEntity getByOrderItem(Map<String, Object> params);

	/**
	 * 通过ID删除结算单信息
	 * @param id 结算单ID
	 * @param userId 用户ID
	 */
	void deleteById(String id, String userId);

	/**
	 * 确认账单
	 * @param billNo   账单编号
	 * @param itemIds  费用项ID
	 * @param userId   操作用户
	 * @param remark   备注
	 */
    void confirmItem(String billNo, String itemIds, String userId, String remark);

	/**
	 * 确认结算单
	 * @param orderItem 工作单号
	 * @param userId 操作人ID
	 */
	void doConfirm(String orderItem, String userId);

	/**
	 * 根据月份分组统计
	 * @param params 查询参数
	 * @return list
	 */
	List<Map<String, Object>> findListByGroupMonth(Map<String, Object> params);

	/**
	 * 统计单月账单费用，根据订单分组
	 * @param params 查询参数
	 * @return List
	 */
    List<BillInfoEntity> findListByGroupOrder(Map<String, Object> params);
}
