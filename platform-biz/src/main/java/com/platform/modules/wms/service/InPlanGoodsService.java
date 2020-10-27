/*
 * 项目名称:platform-plus
 * 类名称:InPlanGoodsService.java
 * 包名称:com.platform.modules.in.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 11:00:36        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.wms.entity.InPlanGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品集合信息 Service接口
 *
 * @author jk
 * @date 2019-09-05 11:00:36
 */
public interface InPlanGoodsService extends IService<InPlanGoodsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<InPlanGoodsEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询商品集合信息
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增商品集合信息
     *
     * @param inPlanGoods 商品集合信息
     * @return 新增结果
     */
    boolean add(InPlanGoodsEntity inPlanGoods);

    /**
     * 根据主键更新商品集合信息
     *
     * @param inPlanGoods 商品集合信息
     * @return 更新结果
     */
    boolean update(InPlanGoodsEntity inPlanGoods);

    /**
     * 根据主键删除商品集合信息
     *
     * @param outerGoodsCode outerGoodsCode
     * @return 删除结果
     */
    boolean delete(String outerGoodsCode);

    /**
     * 根据主键批量删除
     *
     * @param outerGoodsCodes outerGoodsCodes
     * @return 删除结果
     */
    boolean deleteBatch(String[] outerGoodsCodes);

    /**
     * 通过商品编码查询商品规格
     * @param goodsCode
     * @return
     */
    String queryPackUnit(String goodsCode);
}
