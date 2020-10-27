package com.platform.modules.wms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.OrderType;
import com.platform.common.utils.Query;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import com.platform.modules.order.entity.OrderGoodUniqueCodeScanEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.service.OrderDetailGoodsInfoService;
import com.platform.modules.order.service.OrderGoodUniqueCodeScanService;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.wms.dao.StoreInventoryInfoDao;
import com.platform.modules.wms.entity.StoreInventoryInfoEntity;
import com.platform.modules.wms.entity.StoreInventoryRecordEntity;
import com.platform.modules.wms.service.StoreInventoryGoodsService;
import com.platform.modules.wms.service.StoreInventoryInfoService;
import com.platform.modules.wms.service.StoreInventoryRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 库存管理 Service实现类
 *
 * @author jk
 * @date 2019-09-05 14:14:39
 */
@Service("storeInventoryInfoService")
public class StoreInventoryInfoServiceImpl extends ServiceImpl<StoreInventoryInfoDao, StoreInventoryInfoEntity>
        implements StoreInventoryInfoService {

    @Autowired
    private StoreInventoryInfoDao storeInventoryInfoDao;
    @Autowired
    private OrderHeaderInfoService orderHeaderInfoService;
    @Autowired
    private OrderDetailGoodsInfoService orderDetailGoodsInfoService;
    @Autowired
    private StoreInventoryRecordService storeInventoryRecordService;
    @Autowired
    private OrderGoodUniqueCodeScanService orderGoodUniqueCodeScanService;
    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;
    @Autowired
    private BaseSupplyInfoService baseSupplyInfoService;
    @Autowired
    private StoreInventoryGoodsService storeInventoryGoodsService;

    @Override
    public List<StoreInventoryInfoEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.UPDATED_TIME");
        params.put("asc", false);
        Page<StoreInventoryInfoEntity> page = new Query<StoreInventoryInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectStoreInventoryInfoPage(page, params));
    }

    @Override
    public boolean add(StoreInventoryInfoEntity storeInventoryInfo) {
        Date now = new Date();
        storeInventoryInfo.setCreatedTime(now);
        storeInventoryInfo.setUpdatedTime(now);
        return this.save(storeInventoryInfo);
    }

    @Override
    public boolean update(StoreInventoryInfoEntity storeInventoryInfo) {
        storeInventoryInfo.setUpdatedTime(new Date());
        return this.updateById(storeInventoryInfo);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    /**
     * 获取月度库存变化
     *
     * @param stock 查询条件
     * @return Object
     */
    @Override
    public JSONObject findMonthStock(StoreInventoryInfoEntity stock) {
        JSONObject data = new JSONObject();
        // X轴数据
        List<String> xList = Lists.newArrayList();
        // Y轴数据
        List<Double> yList = Lists.newArrayList();
        List<Map<String, Object>> list = storeInventoryInfoDao.findMonthStock(stock);
        list.forEach(s -> {
            xList.add(s.get("time").toString());
            yList.add(Double.parseDouble(s.get("num").toString()));
        });
        data.put("xList", xList);
        data.put("yList", yList);
        return data;
    }

    /**
     * 刷新库存
     *
     * @param orderItem 工作单号
     * @return Object
     */
    @Override
    public RestResponse fresh(String orderItem) {
        try {
            if (StringUtils.isEmpty(orderItem)) {
                return RestResponse.error("工作单号为空");
            }
            // 获取订单信息
            OrderHeaderInfoEntity order = orderHeaderInfoService.getByOrderItem(orderItem);
            // 获取产品列表
            List<OrderDetailGoodsInfoEntity> goodsList = orderDetailGoodsInfoService.findByOrderItem(orderItem);

            if ("R".equals(order.getOrderType())) {
                // 入库单
                for (OrderDetailGoodsInfoEntity goods : goodsList) {
                    // 根据仓库编号、商品编号、货主编号获取库存信息
                    List<StoreInventoryInfoEntity> list = storeInventoryInfoDao.getInfo(order.getStoreHouseCode(),
                            goods.getGoodsCode(), order.getBuyerCode());
                    StoreInventoryInfoEntity info;
                    if (CollectionUtils.isEmpty(list)) {
                        info = new StoreInventoryInfoEntity();
                        info.setItemCode(goods.getGoodsCode());
                        info.setItemName(goods.getGoodsName());
                        info.setStoreCode(order.getStoreHouseCode());
                        info.setStoreName(order.getStoreHouseName());
                        info.setOwnerCode(order.getBuyerCode());
                        info.setOwnerName(order.getBuyerName());
                        info.setItemQuantity(goods.getGoodsNum());
                        save(info);
                        storeInventoryRecordService.add(new StoreInventoryRecordEntity(0D,
                                Double.valueOf(goods.getGoodsNum()),
                                info.getItemQuantity(),
                                orderItem,
                                info.getId()));
                    } else if (list.size() == 1) {
                        info = list.get(0);
                        // 原始值
                        double oldValue = info.getItemQuantity();
                        // 库存数量累积
                        info.setItemQuantity(info.getItemQuantity() + goods.getGoodsNum());
                        update(info);
                        // 添加记录
                        storeInventoryRecordService.add(new StoreInventoryRecordEntity(oldValue,
                                Double.valueOf(goods.getGoodsNum()),
                                info.getItemQuantity(),
                                orderItem,
                                info.getId()));
                    } else {
                        // TODO 异常处理
                        return RestResponse.error("货主[" + order.getBuyerCode()
                                + "]，同一仓库[" + order.getStoreHouseCode()
                                + "], 同一类型产品[" + goods.getGoodsCode()
                                + "]库存多余一条");
                    }
                }
            } else {
                // 出库单
                // 根据仓库编号、货主编号（卖方）获取所有品类的库存信息
                List<StoreInventoryInfoEntity> list = storeInventoryInfoDao.getInfo(order.getStoreHouseCode(),
                        null, order.getSellerCode());
                String errorMsg = null;
                // 1、校验库存是否满足出库要求
                for (OrderDetailGoodsInfoEntity goods : goodsList) {
                    // 库存是否满足要求
                    boolean flag = false;
                    for (StoreInventoryInfoEntity i : list) {
                        if (goods.getGoodsCode().equals(i.getItemCode())
                                && i.getItemQuantity() - goods.getGoodsNum() >= 0) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        errorMsg = goods.getGoodsName() + "[" + goods.getGoodsCode() + "]: 库存不满足出货要求";
                        break;
                    }
                }
                if (StringUtils.isNotBlank(errorMsg)) {
                    return RestResponse.error(errorMsg);
                }
                // 2、满足出货要求
                for (OrderDetailGoodsInfoEntity goods : goodsList) {
                    // 库存是否满足要求
                    for (StoreInventoryInfoEntity i : list) {
                        if (goods.getGoodsCode().equals(i.getItemCode())) {
                            double oldValue = i.getItemQuantity();
                            // 扣除库存
                            i.setItemQuantity(i.getItemQuantity() - goods.getGoodsNum());
                            update(i);
                            // 添加记录
                            storeInventoryRecordService.add(new StoreInventoryRecordEntity(oldValue,
                                    Double.valueOf(goods.getGoodsNum()),
                                    i.getItemQuantity(),
                                    orderItem,
                                    i.getId()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestResponse.success();
    }

    @Override
    public RestResponse fresh(OrderHeaderInfoEntity order, BaseSupplyInfoEntity supplyinfo, BaseCustomerInfoEntity customerInfo) {
        String orderItem = order.getOrderItem();
        try {
            if (StringUtils.isEmpty(orderItem)) {
                return RestResponse.error("工作单号为空");
            }
            // 获取产品列表
            List<OrderDetailGoodsInfoEntity> goodsList = orderDetailGoodsInfoService.findByOrderItem(orderItem);

            if (OrderType.R_.equals(order.getOrderType())) {
                // 入库单
                for (OrderDetailGoodsInfoEntity goods : goodsList) {
                    // 根据仓储供应商编号、商品ID、客户编号获取库存信息
                    List<StoreInventoryInfoEntity> list = storeInventoryInfoDao.getGoodsInfo(supplyinfo.getSupplyCode(), goods.getGoodsId(), customerInfo.getCustomerCode());
                    StoreInventoryInfoEntity info;
                    if (CollectionUtils.isEmpty(list)) {
                        info = new StoreInventoryInfoEntity();
                        info.setItemId(goods.getGoodsId());
                        info.setItemName(goods.getGoodsName());
                        info.setStoreCode(supplyinfo.getSupplyCode());
                        info.setStoreName(supplyinfo.getSupplyName());
                        info.setOwnerCode(customerInfo.getCustomerCode());
                        info.setOwnerName(customerInfo.getCustomerName());
                        info.setItemQuantity(goods.getGoodsNum());
                        save(info);
                        // 添加更新记录
                        storeInventoryRecordService.add(new StoreInventoryRecordEntity(0D, Double.valueOf(goods.getGoodsNum()), info.getItemQuantity(), orderItem, info.getId()));
                    } else if (list.size() == 1) {
                        info = list.get(0);
                        // 原始值
                        double oldValue = info.getItemQuantity();
                        // 库存数量累积
                        info.setItemQuantity(info.getItemQuantity() + goods.getGoodsNum());
                        update(info);
                        // 添加记录
                        storeInventoryRecordService.add(new StoreInventoryRecordEntity(oldValue, Double.valueOf(goods.getGoodsNum()), info.getItemQuantity(), orderItem, info.getId()));
                    } else {
                        // TODO 异常处理
                        return RestResponse.error("货主[" + order.getBuyerCode()
                                + "]，同一仓库[" + order.getStoreHouseCode()
                                + "], 同一类型产品[" + goods.getGoodsCode()
                                + "]库存多余一条");
                    }
                }
            } else {
                // 出库单
                // 根据仓储编号、客户编号（卖方）获取所有品类的库存信息
                List<StoreInventoryInfoEntity> list = storeInventoryInfoDao.getGoodsInfo(supplyinfo.getSupplyCode(), null, customerInfo.getCustomerCode());
                String errorMsg = null;
                // 1、校验库存是否满足出库要求
                for (OrderDetailGoodsInfoEntity goods : goodsList) {
                    // 库存是否满足要求
                    boolean flag = false;
                    for (StoreInventoryInfoEntity i : list) {
                        if (goods.getGoodsId().equals(i.getItemId())
                                && i.getItemQuantity() - goods.getGoodsNum() >= 0) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        errorMsg = goods.getGoodsName() + ": 库存不满足出货要求";
                        break;
                    }
                }
                if (StringUtils.isNotBlank(errorMsg)) {
                    return RestResponse.error(errorMsg);
                }
                // 2、满足出货要求
                for (OrderDetailGoodsInfoEntity goods : goodsList) {
                    // 库存是否满足要求
                    for (StoreInventoryInfoEntity i : list) {
                        if (goods.getGoodsId().equals(i.getItemId())) {
                            double oldValue = i.getItemQuantity();
                            // 扣除库存
                            i.setItemQuantity(i.getItemQuantity() - goods.getGoodsNum());
                            update(i);
                            // 添加记录
                            storeInventoryRecordService.add(new StoreInventoryRecordEntity(oldValue, Double.valueOf(goods.getGoodsNum()), i.getItemQuantity(), orderItem, i.getId()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RestResponse.success();
    }

    /**
     * 确认库存量是否满足出库的要去
     *
     * @param orderItem 工作单号
     * @return Object
     */
    @Override
    public boolean confirmNum(String orderItem) {
        // 校验结果
        boolean checkResult = true;
        // 获取订单信息
        OrderHeaderInfoEntity order = orderHeaderInfoService.getByOrderItem(orderItem);
        // 获取产品列表
        List<OrderDetailGoodsInfoEntity> goodsList = orderDetailGoodsInfoService.findByOrderItem(orderItem);
        for (OrderDetailGoodsInfoEntity goods : goodsList) {
            // 根据仓库编号、商品编号、货主编号(订单卖方)获取库存信息
            List<StoreInventoryInfoEntity> list = storeInventoryInfoDao.getInfo(order.getStoreHouseCode(),
                    goods.getGoodsCode(), order.getSellerCode());
            double allNum = 0;
            for (StoreInventoryInfoEntity info : list) {
                allNum += info.getItemQuantity();
            }
            if (allNum < goods.getGoodsNum()) {
                checkResult = false;
                break;
            }
        }
        return checkResult;
    }

    /**
     * 查询商品列表
     *
     * @param params
     * @return
     */
    @Override
    public List<OrderGoodUniqueCodeScanEntity> findGoodUniqueList(Map<String, Object> params) {

        //1.获取数据
        //货主编码
        String ownerCode = params.get("ownerCode").toString();
        //商品编码
        String goodsCode = params.get("goodsCode").toString();
        //仓库编码
        String storeCode = params.get("storeCode").toString();
        //2.查询数据
        //查询指定货主的工作单号
        List<OrderHeaderInfoEntity> orderHeaderInfoEntityList = orderHeaderInfoService.findByOwnerCode(ownerCode);
        //获取工作单号数组
        String[] orderItems = new String[orderHeaderInfoEntityList.size()];
        for (int i = 0; i < orderItems.length; i++) {
            orderItems[i] = orderHeaderInfoEntityList.get(i).getOrderItem();
        }
        return orderGoodUniqueCodeScanService.getInfo(orderItems, goodsCode, storeCode);
    }

    /**
     * 查询库存分页
     *
     * @param params
     * @return
     */
    @Override
    public Page queryStorePage(Map<String, Object> params) {
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
        return page.setRecords(baseMapper.findStoreList(page, params));
    }

    /**
     * 查询库存数据
     *
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findStoreList(Map<String, Object> params) {
        return baseMapper.findStoreList(null, params);
    }

    /**
     * 分页查询入库信息
     *
     * @param params
     * @return
     */
    @Override
    public Page queryInStorePage(Map<String, Object> params) {
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
        return page.setRecords(baseMapper.findInStoreList(page, params));
    }

    /**
     * 查询入库信息
     *
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findInStoreList(Map<String, Object> params) {
        return baseMapper.findInStoreList(null, params);
    }

    /**
     * 分页查询出库信息
     *
     * @param params
     * @return
     */
    @Override
    public Page queryOutStorePage(Map<String, Object> params) {
        Page<Map<String, Object>> page = new Query<Map<String, Object>>(params).getPage();
        return page.setRecords(baseMapper.findOutStoreList(page, params));
    }

    /**
     * 查询出库信息
     *
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findOutStoreList(Map<String, Object> params) {
        return baseMapper.findOutStoreList(null, params);
    }

    /**
     * 获取出入库记录
     *
     * @param params 请求参数
     * @return 出入库记录
     */
    @Override
    public Page<Map<String, Object>> getOutInRecords(Map<String, Object> params) {
        // 1、获取参数
        // 客户编码
        String ownerCode = "";
        if(!StringUtils.isEmpty(params.get("ownerCode"))) {
            ownerCode = params.get("ownerCode").toString();
        }
        // 商品名称
        String goodsId = "";
        if (!StringUtils.isEmpty(params.get("goodsId"))) {
            goodsId = params.get("goodsId").toString();
        }
        // 仓储供应商编码
        String storeCode = "";
        if (!StringUtils.isEmpty(params.get("storeCode"))) {
            storeCode = params.get("storeCode").toString();
        }
        // 2、获取注册客户ID和仓储供应商ID
        BaseCustomerInfoEntity customerInfo = baseCustomerInfoService.findByCode(ownerCode);
        BaseSupplyInfoEntity storageSupplyInfo = baseSupplyInfoService.findByCode(storeCode);
        // 3、根据注册客户ID和仓储供应商ID获取工作单号列表
        List<String> orderItems = orderHeaderInfoService.getOrderItemList(customerInfo.getRegisterUserId(), storageSupplyInfo.getId());
        // 4、获取出入库记录
        Page<Map<String, Object>> outInRecords = storeInventoryGoodsService.getOutInRecords(params, goodsId, orderItems);
        return outInRecords;
    }

    /**
     * 根据仓储供应商编号、商品ID、客户编号获取库存信息
     *
     * @param storeCode 仓库编号
     * @param itemId    商品编号
     * @param ownerCode 货主编号
     * @return
     */
    @Override
    public List<StoreInventoryInfoEntity> getGoodsInfo(String storeCode, String itemId, String ownerCode) {
        return baseMapper.getGoodsInfo(storeCode, itemId, ownerCode);
    }

    @Override
    public List<StoreInventoryInfoEntity> statisticsStore(Map<String, Object> params) {

        return baseMapper.statisticsStore(params);
    }
}
