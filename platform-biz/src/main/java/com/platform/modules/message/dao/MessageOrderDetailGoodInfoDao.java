/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderDetailGoodInfoDao.java
 * 包名称:com.platform.modules.message.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 14:53:26        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.message.entity.MessageOrderDetailGoodInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单详情表  Dao
 *
 * @author 凯晟admin
 * @date 2019-09-10 14:53:26
 */
@Mapper
public interface MessageOrderDetailGoodInfoDao extends BaseMapper<MessageOrderDetailGoodInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<MessageOrderDetailGoodInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<MessageOrderDetailGoodInfoEntity> selectMessageOrderDetailGoodInfoPage(IPage page, @Param("params") Map<String, Object> params);
}
