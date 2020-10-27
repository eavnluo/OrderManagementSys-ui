/*
 * 项目名称:platform-plus
 * 类名称:OrderHeaderInfoDao.java
 * 包名称:com.platform.modules.order.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-04 08:21:30        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单头表 Dao
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:30
 */
@Mapper
public interface OrderHeaderInfoDao extends BaseMapper<OrderHeaderInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderHeaderInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<OrderHeaderInfoEntity> selectOrderHeaderInfoPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 查询用于工作单号
     *
     * @return
     */
    List<String> queryOrderItem();

    /**
     * 通过工作单号查询订单中运输信息
     *
     * @param orderItem
     * @return
     */
    Map<String, Object> queryTransportOrder(@Param("orderItem") String orderItem);

    /**
     * 通过工作单号查询收货方信息
     *
     * @param orderItem
     * @return
     */
    Map<String, Object> queryTransportReceive(@Param("orderItem") String orderItem);

    /**
     * 通过工作单号查询配货方信息
     *
     * @param orderItem
     * @return
     */
    Map<String, Object> queryTransportDelivery(@Param("orderItem") String orderItem);

    /**
     * 通过工作单号查询承运商信息
     *
     * @param orderItem
     * @return
     */
    Map<String, Object> queryTransportCarrier(@Param("orderItem") String orderItem);

    /**
     * 通过工作单号查询运输信息
     *
     * @param orderItem
     * @return
     */
    Map<String, Object> queryTransport(@Param("orderItem") String orderItem);

    /***
     *
     * @param customerOrderNo 订单号
     * @param statusCode 客户状态编码
     * @param statusName 状态编码名称
     */
    void updatByOrderNo(@Param("customerOrderNo") String customerOrderNo, @Param("statusCode") String statusCode,
                        @Param("statusName") String statusName);


    /**
     * 获取订单图表所需要的数量
     *
     * @param orderInfo   查询条件实体
     * @param isYesterday
     * @return number
     */
    int findNumberForChart(@Param("orderInfo") OrderHeaderInfoEntity orderInfo,
                           @Param("isYesterday") boolean isYesterday);

    /**
     * 获取当前月份监控图表数据
     *
     * @param orderInfo 查询条件集合
     * @return list
     */
    List<Map<String, Object>> findListForChart(@Param("orderInfo") OrderHeaderInfoEntity orderInfo);

    /**
     * 一年中每个月的订单数量
     *
     * @param orderInfo
     * @return list
     */
    List<Map<String, Object>> findListForChartByMonth(@Param("orderInfo") OrderHeaderInfoEntity orderInfo);

    /**
     * 获取指定月份的上一个月的统计数量
     *
     * @param time      指定月份，例如：2019-01
     * @param orderInfo 查询条件
     * @return map
     */
    Map<String, Object> getLastMonth(@Param("time") String time, @Param("orderInfo") OrderHeaderInfoEntity orderInfo);

    /**
     * 查询在途的订单数量
     *
     * @param orderInfo   查询条件
     * @param isYesterday 是否查询昨天的数据
     * @return number 数量
     */
    int findNumberOnWay(@Param("orderInfo") OrderHeaderInfoEntity orderInfo, @Param("isYesterday") boolean isYesterday);

    /**
     * 更新订单状态
     *
     * @param orderHeaderInfo
     */
    int updateStatusByOrderNo(OrderHeaderInfoEntity orderHeaderInfo);

    /**
     * 获取当天的内部订单数
     *
     * @return
     */
    int getOrderCountToday();

    /**
     * 根据内部订单编号获取订单信息
     *
     * @param orderItem 内部订单编号
     * @return order
     */
    OrderHeaderInfoEntity getByOrderItem(@Param("orderItem") String orderItem);

    /**
     * 根据内部订单编号获取订单类型
     *
     * @param orderItem
     * @return
     */
    Map<String, String> queryOrder(@Param("orderItem") String orderItem);

    /**
     * 根据内部订单编号获取订单号
     *
     * @param orderItem
     * @return
     */
    String getByOrderNo(@Param("orderItem") String orderItem);

    /**
     * 根据内部订单编号获取入库信息
     *
     * @param orderItem   工作单号
     * @param operateType 操作类型
     * @return
     */
    List<Map<String, Object>> queryScanInAll(@Param("orderItem") String orderItem, @Param("operateType") String operateType);

    /**
     * 根据内部订单编号获取出库信息
     *
     * @param orderItem   工作单号
     * @param operateType 操作类型
     * @return
     */
    List<Map<String, Object>> queryScanOutAll(@Param("orderItem") String orderItem, @Param("operateType") String operateType);

    Map<String, Object> outInfo(@Param("orderItem") String orderItem);

    Map<String, Object> inInfo(@Param("orderItem") String orderItem);

    /**
     * 查询所有订单订单状态为“订单创建”且DYNMIC不为空的订单
     *
     * @return list
     */
    List<OrderHeaderInfoEntity> getNewOrderList();

    /**
     * 统计本月各仓库的订单数
     *
     * @return
     */
    List<Map<String, Object>> countStoreOrderEveryMonth();

    /**
     * 查询每天的订单数量
     *
     * @param orderType 订单类型（出库/入库）
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<Map<String, Object>> getByOrderCount(@Param("orderType") String orderType,
                                              @Param("startTime") String startTime,
                                              @Param("endTime") String endTime);

    /**
     * 获取订单数量
     *
     * @param time      时间
     * @param pattern   格式
     * @param orderType 订单类型
     * @return 数量
     */
    Integer getNum(@Param("time") String time, @Param("pattern") String pattern, @Param("orderType") String orderType);

    List<String> getOrderItemList(@Param("customerId") String customerId, @Param("storageSupplyId") String storageSupplyId);

    /**
     * 获取账单头部信息
     *
     * @param orderItem
     * @return
     */
    Map<String, Object> findByOrderItem(@Param("orderItem") String orderItem);

    /**
     * 根据订单类型获取当前登录用户近12个月的订单数量
     *
     * @param type
     * @param params
     * @return
     */
    List<Map<String, Object>> getOrderNumByType(@Param("type") String type, @Param("params") Map<String,Object> params);
}
