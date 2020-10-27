package com.platform.modules.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.OrderStatus;
import com.platform.common.OrderType;
import com.platform.common.RegisterUserType;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.Query;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.order.dao.OrderHeaderInfoDao;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import com.platform.modules.order.entity.OrderStatusFlowEntity;
import com.platform.modules.order.service.OrderDetailGoodsInfoService;
import com.platform.modules.order.service.OrderHeaderInfoService;
import com.platform.modules.order.service.OrderStatusFlowService;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.wms.entity.StoreInventoryInfoEntity;
import com.platform.modules.wms.service.StoreInventoryInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 订单头表 Service实现类
 *
 * @author 凯晟admin
 * @date 2019-09-04 08:21:30
 */
@Service("orderHeaderInfoService")
public class OrderHeaderInfoServiceImpl extends ServiceImpl<OrderHeaderInfoDao, OrderHeaderInfoEntity>
        implements OrderHeaderInfoService {

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;
    @Autowired
    private OrderDetailGoodsInfoService orderDetailGoodsInfoService;
    @Autowired
    private StoreInventoryInfoService storeInventoryInfoService;
    @Autowired
    private BaseSupplyInfoService baseSupplyInfoService;
    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;


    @Override
    public List<OrderHeaderInfoEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.ORDER_DATE");
        params.put("asc", false);
        Page<OrderHeaderInfoEntity> page = new Query<OrderHeaderInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectOrderHeaderInfoPage(page, params));
    }

    /**
     * 分页查询订单头表，查询对应用户类型的订单列表
     *
     * @param params 查询参数
     * @return Page
     */
    @Override
    public Page queryPage(Map<String, Object> params, RegisterUserEntity user) {
        //排序
        params.put("sidx", "T.ORDER_DATE");
        params.put("asc", false);
        Page<OrderHeaderInfoEntity> page = new Query<OrderHeaderInfoEntity>(params).getPage();

        // 获取用户类型
        String type = user.getType();
        // 判断是否是客户类型
        if (type.equals(RegisterUserType.CUSTOMER)) {
            // 客户
            params.put("customerId", user.getId());
            page.setRecords(baseMapper.selectOrderHeaderInfoPage(page, params));
        } else {
            // 供应商
            BaseSupplyInfoEntity supplyInfo = baseSupplyInfoService.findByRegistryUserId(user.getId());
            params.put("supplyId", supplyInfo.getId());
            page.setRecords(baseMapper.selectOrderHeaderInfoPage(page, params));
        }
        return page;
    }

    @Override
    public boolean add(OrderHeaderInfoEntity orderHeaderInfo) {
        return this.save(orderHeaderInfo);
    }

    @Override
    public boolean update(OrderHeaderInfoEntity orderHeaderInfo) {
        return this.updateById(orderHeaderInfo);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String getByOrderNo(String orderItem) {
        return baseMapper.getByOrderNo(orderItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    /**
     * 获取订单图表所需要的数量
     *
     * @param orderInfo   查询条件实体
     * @param isYesterday
     * @return number
     */
    @Override
    public int findNumberForChart(OrderHeaderInfoEntity orderInfo, boolean isYesterday) {
        return baseMapper.findNumberForChart(orderInfo, isYesterday);
    }

    /**
     * 获取当前月份监控图表数据
     *
     * @param orderInfo 查询条件集合
     * @return object
     */
    @Override
    public JSONObject findChartData(OrderHeaderInfoEntity orderInfo) {
        JSONObject data = new JSONObject();
        List<Map<String, Object>> list = baseMapper.findListForChart(orderInfo);
        if (!CollectionUtils.isEmpty(list)) {
            List<String> dayList = Lists.newArrayList();
            List<Integer> numberList = Lists.newArrayList();
            list.forEach(m -> {
                dayList.add(m.get("time").toString());
                numberList.add(Integer.parseInt(m.get("num").toString()));
            });
            data.put("xList", dayList);
            data.put("vList", numberList);
        }
        return data;
    }

    /**
     * 月度订单环比总数量变化
     *
     * @param orderInfo 查询条件集合
     * @return object
     */
    @Override
    public JSONObject findChartDataHb(OrderHeaderInfoEntity orderInfo) {
        JSONObject data = new JSONObject();
        List<Map<String, Object>> list = baseMapper.findListForChartByMonth(orderInfo);
        List<String> monthList = Lists.newArrayList();
        List<Integer> numberList = Lists.newArrayList();
        if (list.size() > 1) {
            // 1、计算第一个月的环比
            String time = list.get(0).get("time").toString();
            // 上一个月
            String lastMonth = getLastMonth(time);
            Map<String, Object> map = baseMapper.getLastMonth(lastMonth, orderInfo);
            monthList.add(time);
            if (map == null) {
                numberList.add(0);
            } else {
                int num = Integer.parseInt(list.get(0).get("num").toString()) - Integer.parseInt(map.get("num").toString());
                numberList.add(num);
            }
            // 2、开始计算第一个月之后的每个月的环比数量
            for (int i = 1; i < list.size(); i++) {
                monthList.add(list.get(i).get("time").toString());
                // 与上一个月的差值
                int num = Integer.parseInt(list.get(i).get("num").toString())
                        - Integer.parseInt(list.get(i - 1).get("num").toString());
                numberList.add(num);
            }
        } else if (list.size() == 1) {
            // TODO 只有一个月的数据，如何计算同比
            monthList.add(list.get(0).get("time").toString());
            numberList.add(0);
        }
        data.put("xList", monthList);
        data.put("yList", numberList);
        return data;
    }

    /**
     * 获取指定月份的上一个月，例如：2019-09
     *
     * @return
     */
    public static String getLastMonth(String time) {
        String lastMonth = "";
        String[] arr = time.split("-");
        int month = Integer.parseInt(arr[1]);
        if (month == 1) {
            lastMonth = (Integer.parseInt(arr[0]) - 1) + "-12";
        } else {
            lastMonth = arr[0] + "-" + formatNum(month - 1);
        }
        return lastMonth;
    }

    /**
     * 格式化数字
     *
     * @param num
     * @return
     */
    public static String formatNum(int num) {
        return num > 9 ? String.valueOf(num) : new StringBuilder("0").append(num).toString();
    }

    /**
     * 查询在途的订单数量
     *
     * @param orderInfo   查询条件
     * @param isYesterday 是否查询昨天的数据
     * @return number 数量
     */
    @Override
    public int findNumberOnWay(OrderHeaderInfoEntity orderInfo, boolean isYesterday) {
        return baseMapper.findNumberOnWay(orderInfo, isYesterday);
    }

    /**
     * 新增订单（订单头和商品信息）
     *
     * @param orderHeaderInfo
     * @return orderItem 工作单号
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addCompleteOrder(OrderHeaderInfoEntity orderHeaderInfo) {
        synchronized (this) {
            List<OrderDetailGoodsInfoEntity> goods = orderHeaderInfo.getGoods();
            // 销售订单，校验库存是否满足
            if (orderHeaderInfo.getOrderType().equals(OrderType.I_)) {
                checkStock(orderHeaderInfo, goods);
            }
            Date now = new Date();
            //生成工作单号，并封装到对象
            String orderItem = getOrderItem(orderHeaderInfo.getOrderType());
            orderHeaderInfo.setOrderItem(orderItem);
            orderHeaderInfo.setOrderDate(now);
            orderHeaderInfo.setCreatedTime(now);
            orderHeaderInfo.setUpdatedTime(now);
            // 默认下单时间为当前时间
            orderHeaderInfo.setOrderDate(now);
            for (OrderDetailGoodsInfoEntity good : goods) {
                good.setOrderItem(orderItem);
                good.setCreatedTime(now);
                good.setUpdatedTime(now);
            }
            // 设置订单状态为订单创建状态
            orderHeaderInfo.setStatus(OrderStatus.ConfirmOder.getCode());
            orderHeaderInfo.setStatusName(OrderStatus.ConfirmOder.getStatusName());
            orderHeaderInfo.setStatusUpdte(now);

            List<OrderStatusFlowEntity> statusList = new ArrayList<>();
            statusList.add(new OrderStatusFlowEntity(orderItem, OrderStatus.OrderCreate));
            statusList.add(new OrderStatusFlowEntity(orderItem, OrderStatus.ConfirmOder));

            // 保存订单头
            baseMapper.insert(orderHeaderInfo);
            // 保存商品信息
            orderDetailGoodsInfoService.saveBatch(goods);
            // 保存订单状态信息
            orderStatusFlowService.saveBatch(statusList);
            return orderItem;
        }
    }

    /**
     * 销售订单下单校验库存
     *
     * @param orderHeaderInfo
     * @param goods
     */
    private void checkStock(OrderHeaderInfoEntity orderHeaderInfo, List<OrderDetailGoodsInfoEntity> goods) {
        // 获取客户信息
        BaseCustomerInfoEntity customerInfo = baseCustomerInfoService.findByRegistryUserId(orderHeaderInfo.getCustomerId());
        // 获取供应商信息
        BaseSupplyInfoEntity supplyInfo = baseSupplyInfoService.getById(orderHeaderInfo.getStorageSupplyId());
        // 获取该客户的所有库存商品信息
        List<StoreInventoryInfoEntity> list = storeInventoryInfoService.getGoodsInfo(supplyInfo.getSupplyCode(), null, customerInfo.getCustomerCode());
        for (OrderDetailGoodsInfoEntity item : goods) {
            // 库存满足标识
            boolean flag = false;
            // 商品存在标识
            boolean isHave = false;
            for (StoreInventoryInfoEntity storeGoods : list) {
                if (item.getGoodsId().equals(storeGoods.getItemId())) {
                    // 商品存在
                    isHave = true;
                    if (storeGoods.getItemQuantity() - item.getGoodsNum() >= 0) {
                        // 满足库存
                        flag = true;
                        break;
                    }

                }
            }
            if (!isHave) {
                // 商品信息不存在
                throw new BusinessException(item.getGoodsName() + ":库存中不存在此商品");
            }
            if (!flag) {
                // 库存不满足
                throw new BusinessException(item.getGoodsName() + ":库存不满足出货要求");
            }

        }
    }

    /**
     * 生成工作单号，将方法改为同步方法，防止生成重复的工作单号
     *
     * @param orderType 订单类型
     * @return orderItem 工作单号
     */
    @Override
    public String getOrderItem(String orderType) {
        // 获取当天的订单数
        int count = baseMapper.getOrderCountToday() + 1;
        // 返回订单编号
        return "JW" + orderType + DateUtils.format(new Date(), "yyMMdd") + String.format("%03d", count);
    }

    /**
     * 根据内部订单编号获取订单信息
     *
     * @param orderItem 内部订单编号
     * @return order
     */
    @Override
    public OrderHeaderInfoEntity getByOrderItem(String orderItem) {
        OrderHeaderInfoEntity entity = baseMapper.getByOrderItem(orderItem);
        if (entity == null) {
            throw new BusinessException("订单信息不存在");
        }
        return entity;
    }

    @Override
    public Map<String, Object> queryTransportOrder(String orderItem) {
        return baseMapper.queryTransportOrder(orderItem);
    }

    @Override
    public Map<String, Object> queryTransportReceive(String orderItem) {
        return baseMapper.queryTransportReceive(orderItem);
    }

    @Override
    public Map<String, Object> queryTransportDelivery(String orderItem) {
        return baseMapper.queryTransportDelivery(orderItem);
    }

    @Override
    public Map<String, Object> queryTransportCarrier(String orderItem) {
        return baseMapper.queryTransportCarrier(orderItem);
    }

    /**
     * 根据工作单号获取运输信息
     *
     * @param orderItem 内部订单编号
     * @return order
     */
    @Override
    public Map<String, Object> queryTransport(String orderItem) {
        return baseMapper.queryTransport(orderItem);
    }

    @Override
    public boolean isOrderNoExist(String orderNo) {
        OrderHeaderInfoEntity orderHeaderInfo = new OrderHeaderInfoEntity();
        orderHeaderInfo.setOrderNo(orderNo);
        int count = baseMapper.selectCount(new QueryWrapper<OrderHeaderInfoEntity>(orderHeaderInfo));
        if (count == 0) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, String> queryOrder(String orderItem) {
        return baseMapper.queryOrder(orderItem);
    }

    @Override
    public List<Map<String, Object>> queryScanInAll(String orderItem, String operateType) {
        return baseMapper.queryScanInAll(orderItem, operateType);
    }

    @Override
    public List<Map<String, Object>> queryScanOutAll(String orderItem, String operateType) {
        return baseMapper.queryScanOutAll(orderItem, operateType);
    }

    @Override
    public List<String> queryOrderItem() {
        return baseMapper.queryOrderItem();
    }

    /**
     * 确认订单
     *
     * @param orderItem 工作单号
     * @param orderType 订单类型，出库、入库
     * @param userId    当前用户ID
     * @return Object
     */
    @Override
    public RestResponse confirmOrder(String orderItem, String orderType, String userId) {
        // 订单信息
        Date nowDate = new Date();
        OrderHeaderInfoEntity order = new OrderHeaderInfoEntity();
        order.setOrderItem(orderItem);
        order.setStatusUpdte(nowDate);
        // 状态记录
        OrderStatusFlowEntity status = new OrderStatusFlowEntity();
        status.setCreatedBy(userId);
        status.setCreatedTime(nowDate);
        status.setOrderItem(orderItem);
        status.setStatusDate(nowDate);
        if (OrderType.I_.equals(orderType)) {
            // TODO 出库订单校验库存
            boolean checkResult = storeInventoryInfoService.confirmNum(orderItem);
            if (!checkResult) {
                // 库存数量不满足出库单要求
                return RestResponse.error("库存数量不满足出库单要求");
            }
            order.setStatus(OrderStatus.OutConfirmOder.getCode());
            order.setStatusName(OrderStatus.OutConfirmOder.getStatusName());
            status.setStatusCode(OrderStatus.OutConfirmOder.getCode());
            status.setOrderStatus(OrderStatus.OutConfirmOder.getStatusName());
        } else {
            order.setStatus(OrderStatus.InConfirmOder.getCode());
            order.setStatusName(OrderStatus.InConfirmOder.getStatusName());
            status.setStatusCode(OrderStatus.InConfirmOder.getCode());
            status.setOrderStatus(OrderStatus.InConfirmOder.getStatusName());
        }
        // 修改订单表状态信息
        baseMapper.updateStatusByOrderNo(order);
        // 添加状态信息记录
        orderStatusFlowService.add(status);
        return RestResponse.success();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeStatus(OrderHeaderInfoEntity orderHeaderInfo) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("orderItem", orderHeaderInfo.getOrderItem());
        List<OrderHeaderInfoEntity> orderHeaderInfoList = baseMapper.queryAll(params);
        if (orderHeaderInfoList.size() <= 0) {
            throw new RuntimeException("更改失败，工作单号不存在！");
        }
        if (orderHeaderInfo.getUpdatedTime() == null) {
            // 状态更新时间
            orderHeaderInfo.setUpdatedTime(new Date());
        }
        baseMapper.updateStatusByOrderNo(orderHeaderInfo);
        OrderStatusFlowEntity orderStatusFlowEntity = new OrderStatusFlowEntity();
        orderStatusFlowEntity.setId(UUID.randomUUID().toString());
        orderStatusFlowEntity.setOrderItem(orderHeaderInfo.getOrderItem());
        orderStatusFlowEntity.setStatusCode(orderHeaderInfo.getStatus());
        orderStatusFlowEntity.setOrderStatus(orderHeaderInfo.getStatusName());
        orderStatusFlowEntity.setStatusDate(orderHeaderInfo.getUpdatedTime());
        orderStatusFlowService.save(orderStatusFlowEntity);
    }

    @Override
    public List<OrderHeaderInfoEntity> findByOwnerCode(String ownerCode) {
        List<OrderHeaderInfoEntity> list = baseMapper.selectList(new QueryWrapper<OrderHeaderInfoEntity>().eq("BUYER_CODE", ownerCode));
        return list;
    }

    @Override
    public List<OrderHeaderInfoEntity> findNewOrderList() {
        return baseMapper.getNewOrderList();
    }

    @Override
    public Map<String, Object> OutInfo(String orderItem) {
        return baseMapper.outInfo(orderItem);
    }

    @Override
    public Map<String, Object> InInfo(String orderItem) {
        return baseMapper.inInfo(orderItem);
    }

    @Override
    public boolean updateStatusByOrderNo(OrderHeaderInfoEntity orderHeaderInfo) {
        int rows = baseMapper.updateStatusByOrderNo(orderHeaderInfo);
        if (rows > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> countStoreOrderEveryMonth() {
        return baseMapper.countStoreOrderEveryMonth();
    }

    /**
     * 查询每天订单数量
     *
     * @param orderType 订单类型（出库/入库）
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Override
    public List<Map<String, Object>> getByOrderCount(String orderType, String startTime, String endTime) {
        return baseMapper.getByOrderCount(orderType, startTime, endTime);
    }

    /**
     * 获取订单数量
     *
     * @param time      时间
     * @param pattern   格式
     * @param orderType 订单类型
     * @return 数量
     */
    @Override
    public Integer getNum(String time, String pattern, String orderType) {
        return baseMapper.getNum(time, pattern, orderType);
    }

    @Override
    public List<String> getOrderItemList(String registerUserId, String storageSupplyId) {
        return baseMapper.getOrderItemList(registerUserId, storageSupplyId);
    }

    /**
     * 获取账单头部信息
     *
     * @param orderItem
     * @return
     */
    @Override
    public Map<String, Object> findByOrderItem(String orderItem) {
        return baseMapper.findByOrderItem(orderItem);
    }

    /**
     * 根据订单类型获取当前登录用户近12个月的订单数量
     * @param type
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> getOrderNumByType(String type, Map<String,Object> params) {

        return baseMapper.getOrderNumByType(type,params);
    }
}
