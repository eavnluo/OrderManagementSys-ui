/*
 * 项目名称:platform-plus
 * 类名称:BaseCarInfoService.java
 * 包名称:com.platform.modules.base.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-29 09:32:01        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.base.entity.BaseCarInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zlm
 * @date 2019-09-29 09:32:01
 */
public interface BaseCarInfoService extends IService<BaseCarInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseCarInfoEntity> queryAll(Map<String, Object> params);

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
     * @param baseCarInfo 
     * @return 新增结果
     */
    boolean add(BaseCarInfoEntity baseCarInfo);

    /**
     * 根据主键更新
     *
     * @param baseCarInfo 
     * @return 更新结果
     */
    boolean update(BaseCarInfoEntity baseCarInfo);

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

    /**
     * 根据归属修改对应数据的删除标识
     * @param ids 归属ids
     * @return 受影响的行数
     */
    boolean updateDelFlagByBelongTo(String[] ids);

    /**
     * 根据归属查找对应的卡车信息列表
     * @param belongTo
     * @return
     */
    List<BaseCarInfoEntity> findByBelongTo(String belongTo);

    /**
     * 获取格式化的卡车司机信息
     * @return
     */
    JSONArray findFormatList();

    /**
     * 分页关联查询
     * @param params 查询参数
     * @return Page
     */
    Page findPage(Map<String, Object> params);
}
