/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderHeaderInfoDao.java
 * 包名称:com.platform.modules.message.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 15:13:17        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.message.entity.MessageOrderHeaderInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单头表  Dao
 *
 * @author jk
 * @date 2019-09-10 15:13:17
 */
@Mapper
public interface MessageOrderHeaderInfoDao extends BaseMapper<MessageOrderHeaderInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<MessageOrderHeaderInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<MessageOrderHeaderInfoEntity> selectMessageOrderHeaderInfoPage(IPage page, @Param("params") Map<String, Object> params);
}
