/*
 * 项目名称:platform-plus
 * 类名称:BaseSupplyInfoService.java
 * 包名称:com.platform.modules.base.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-03 17:21:59        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 供应商信息管理 Service接口
 *
 * @author jk
 * @date 2019-09-03 17:21:59
 */
public interface BaseSupplyInfoService extends IService<BaseSupplyInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseSupplyInfoEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询供应商信息管理
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增供应商信息管理
     *
     * @param baseSupplyInfo 供应商信息管理
     * @return 新增结果
     */
    boolean add(BaseSupplyInfoEntity baseSupplyInfo);

    /**
     * 根据主键更新供应商信息管理
     *
     * @param baseSupplyInfo 供应商信息管理
     * @return 更新结果
     */
    boolean update(BaseSupplyInfoEntity baseSupplyInfo);

    /**
     * 根据主键删除供应商信息管理
     *
     * @param supplyId supplyId
     * @return 删除结果
     */
    boolean delete(String supplyId);

    /**
     * 根据主键批量删除
     *
     * @param supplyIds supplyIds
     * @return 删除结果
     */
    boolean deleteBatch(String[] supplyIds);

    /**
     * 查询工厂信息列表
     *
     * @param params 查询条件
     * @return list
     */
    List<Map<String, Object>> findList(Map<String, Object> params);

    /**
     * 获取结构化数据列表
     *
     * @return
     */
    JSONArray findFormatList();

    /**
     * 获取格式化后的工厂列表
     *
     * @return
     */
    JSONArray findFormatStoreList();

    /**
     * 查询供应商
     */
    List<BaseSupplyInfoEntity> getBySupply();

    List<BaseSupplyInfoEntity> getBySupplyType();

    List<Map<String, String>> getSupplyInfo();

    /**
     * 查询库房数量
     *
     * @param isYesterday 是否昨天
     * @return
     */
    int findNumberForChart(boolean isYesterday);

    /**
     * 生成供应商代码
     *
     * @param supplyName 供应商名称
     * @param city       城市名称
     * @return
     */
    String generateSupplyCode(String supplyName, String city);

    /**
     * 根据供应商类型获取已经报价的供应商列表
     *
     *
     * @param companyCode 公司代码
     * @param type 供应商类型
     * @return 供应商列表
     */
    List<BaseSupplyInfoEntity> findListByType(String companyCode, String type);

    /**
     * 根据注册用户id查找供应商信息
     *
     * @param id
     * @return
     */
    BaseSupplyInfoEntity findByRegistryUserId(String id);

    /**
     * 根据编码获取信息
     *
     * @param supplyCode
     * @return
     */
    BaseSupplyInfoEntity findByCode(String supplyCode);
}
