/*
 * 项目名称:platform-plus
 * 类名称:StatementInfoDao.java
 * 包名称:com.platform.modules.statement.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-30 14:33:17        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.bill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.bill.entity.BillInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 结算单Dao
 *
 * @author Mr.panbb
 * @date 2020-03-30 14:33:17
 */
@Mapper
public interface BillInfoDao extends BaseMapper<BillInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BillInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<BillInfoEntity> selectStatementInfoPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 按月份统计
     *
     * @param params 查询条件
     * @return
     */
    List<Map<String, Object>> getListGroupMonth(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 获取指定年月列表
     *
     *
     * @param page
     * @param params 查询条件
     * @return
     */
    List<BillInfoEntity> getListByMonth(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 获取指定订单号列表
     *
     * @param params 查询条件
     * @return
     */
    List<BillInfoEntity> getListByOrderNo(@Param("params") Map<String, Object> params);

    /**
     * 删除信息
     *
     * @param id
     * @param userId
     * @param updateTime
     */
    void deleteById(@Param("id") String id, @Param("userId") String userId, @Param("updateTime") Date updateTime);

    /**
     * 更新确认金额
     *
     * @param billNo       账单编号
     * @param confirmMoney 确认金额
     * @param userId       更新人
     * @param updateTime   更新时间
     * @param remark       备注
     */
    void updateMoney(@Param("billNo") String billNo,
                     @Param("confirmMoney") BigDecimal confirmMoney,
                     @Param("userId") String userId,
                     @Param("updateTime") Date updateTime,
                     @Param("remark") String remark);

    /**
     * 确认账单
     *
     * @param orderItem  工作单号
     * @param userId     更新人
     * @param updateTime 更新时间
     */
    void doConfirm(@Param("orderItem") String orderItem, @Param("userId") String userId, @Param("updateTime") Date updateTime);

    /**
     * 根据工作单号获取账单信息
     *
     * @param params
     * @return
     */
    BillInfoEntity getByOrderItem(@Param("params") Map<String, Object> params);

    /**
     * 统计今天账单生成的数量
     * @return count
     */
    int getCountForToday();
}
