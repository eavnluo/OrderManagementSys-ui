/*
 * 项目名称:platform-plus
 * 类名称:StatementItemDao.java
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
import com.platform.modules.bill.entity.BillItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 结算项Dao
 *
 * @author Mr.panbb
 * @date 2020-03-30 14:33:17
 */
@Mapper
public interface BillItemDao extends BaseMapper<BillItemEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BillItemEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<BillItemEntity> selectStatementItemPage(IPage page, @Param("params") Map<String, Object> params);
}
