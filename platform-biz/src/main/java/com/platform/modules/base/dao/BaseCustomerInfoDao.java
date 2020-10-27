/*
 * 项目名称:platform-plus
 * 类名称:BaseCustomerInfoDao.java
 * 包名称:com.platform.modules.base.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-20 15:00:22        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 客户信息表Dao
 *
 * @author jk
 * @date 2019-09-20 15:00:22
 */
@Mapper
public interface BaseCustomerInfoDao extends BaseMapper<BaseCustomerInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseCustomerInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<BaseCustomerInfoEntity> selectBaseCustomerInfoPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 获取买方客户列表
     * @return
     */
    List<BaseCustomerInfoEntity> findBuyList();

    /**
     * 获取卖方客户列表
     * @return
     */
    List<BaseCustomerInfoEntity> findSellList();


    List<BaseCustomerInfoEntity> findCustomerLists();

    /**
     * 根据id查找客户信息
     * @param id
     * @return
     */
    BaseCustomerInfoEntity findById(String id);


    /**
     * 根据客户类型查货主
     * @return
     */
    List<BaseCustomerInfoEntity> getByOwner();
    /**
     * 根据注册用户ID查询信息
     *
     * @param registerUserId 注册用户ID
     * @return
     */
    BaseCustomerInfoEntity findByRegistryUserId(@Param("registerUserId") String registerUserId);

    /**
     * 根据订单编工作单号查询客户信息
     *
     * @param orderItem 工作单号
     * @return 客户信息
     */
    BaseCustomerInfoEntity getByOrderItem(@Param("orderItem") String orderItem);
}
