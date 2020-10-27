package com.platform.modules.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.order.entity.OrderGoodUniqueCodeScanEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品唯一编码扫描表 Service接口
 *
 * @author jk
 * @date 2019-09-12 13:22:59
 */
public interface OrderGoodUniqueCodeScanService extends IService<OrderGoodUniqueCodeScanEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<OrderGoodUniqueCodeScanEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询商品唯一编码扫描表
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增商品唯一编码扫描表
     *
     * @param orderGoodUniqueCodeScan 商品唯一编码扫描表
     * @return 新增结果
     */
    boolean add(OrderGoodUniqueCodeScanEntity orderGoodUniqueCodeScan);

    /**
     * 根据主键更新商品唯一编码扫描表
     *
     * @param orderGoodUniqueCodeScan 商品唯一编码扫描表
     * @return 更新结果
     */
    boolean update(OrderGoodUniqueCodeScanEntity orderGoodUniqueCodeScan);

    /**
     * 根据主键删除商品唯一编码扫描表
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
     * 查询扫码表是否存在该记录
     *
     * @param orderItem   工作单号
     * @param uniqueCode  产品唯一码
     * @param operateType 操作类型
     * @return
     */
    boolean isExist(String orderItem, String uniqueCode, String operateType);

    /***
     * 根据订单信息获取实际的扫描列表
     * @param orderItem 工作单号
     * @param operateType 操作类型
     * @return
     */
    List<Map<String,Object>> listByOrderItem(String orderItem, String operateType);


    /**
     * 根据工作单号、商品编号、仓库编码获取商品唯一扫描表中的信息
     *
     * @param orderItems 订单号数组
     * @param goodsCode  商品编号
     * @param storeCode  仓库编码
     * @return
     */
    List<OrderGoodUniqueCodeScanEntity> getInfo(String[] orderItems, String goodsCode, String storeCode);

    /**
     * 更新商品出库状态
     * @param orderItem  工作单号
     * @param barCode    商品条码
     * @param goodsCode  商品编码
     */
    void updateBarCode(String orderItem, String barCode, String goodsCode);

    /**
     * 获取库存列表
     * @param time    时间
     * @param pattern 格式
     * @return list
     */
    List<Map<String, Object>> findList(String time, String pattern);
}
