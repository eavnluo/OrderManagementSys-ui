/*
 * 项目名称:platform-plus
 * 类名称:BaseGoodsInfoService.java
 * 包名称:com.platform.modules.base.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-02 17:29:38        jk     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.base.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.base.entity.BaseGoodsInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品基础信息表 Service接口
 *
 * @author jk
 * @date 2019-09-02 17:29:38
 */
public interface BaseGoodsInfoService extends IService<BaseGoodsInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<BaseGoodsInfoEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询商品基础信息表 
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增商品基础信息表 
     *
     * @param baseGoodsInfo 商品基础信息表 
     * @return 新增结果
     */
    boolean add(BaseGoodsInfoEntity baseGoodsInfo);

    /**
     * 根据主键更新商品基础信息表 
     *
     * @param baseGoodsInfo 商品基础信息表 
     * @return 更新结果
     */
    boolean update(BaseGoodsInfoEntity baseGoodsInfo);

    /**
     * 根据主键删除商品基础信息表 
     *
     * @param goodsId goodsId
     * @return 删除结果
     */
    boolean delete(String goodsId);

    /**
     * 根据主键批量删除
     *
     * @param goodsIds goodsIds
     * @return 删除结果
     */
    boolean deleteBatch(String[] goodsIds);

    /**
     * 获取商品基础数据列表
     * @param params 查询参数
     * @return list
     */
    List<Map<String, Object>> findList(Map<String, Object> params);



    List<BaseGoodsInfoEntity> findGoodsList();

    JSONArray findFormatList();

    List<BaseGoodsInfoEntity> findByBarCode(String[] barCodeArray);
}
