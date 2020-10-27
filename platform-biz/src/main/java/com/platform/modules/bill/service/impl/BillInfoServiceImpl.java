/*
 * 项目名称:platform-plus
 * 类名称:StatementInfoServiceImpl.java
 * 包名称:com.platform.modules.statement.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-30 14:33:17        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.bill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.Query;
import com.platform.common.utils.StringUtils;
import com.platform.modules.bill.dao.BillInfoDao;
import com.platform.modules.bill.entity.BillInfoEntity;
import com.platform.modules.bill.entity.BillItemEntity;
import com.platform.modules.bill.service.BillInfoService;
import com.platform.modules.bill.service.BillItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 结算单Service实现类
 *
 * @author Mr.panbb
 * @date 2020-03-30 14:33:17
 */
@Service("billInfoService")
public class BillInfoServiceImpl extends ServiceImpl<BillInfoDao, BillInfoEntity>
        implements BillInfoService {
    @Autowired
    private BillItemService billItemService;

    @Override
    public List<BillInfoEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("asc", false);
        Page<BillInfoEntity> page = new Query<BillInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectStatementInfoPage(page, params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(BillInfoEntity billInfo) {
        // 保存费用明细
        List<BillItemEntity> itemList = billInfo.getItemList();
        Map<String, List<BillItemEntity>> map = separateItemList(itemList);
        // 客户账单
        billInfo.setType("01");
        this.doSave(billInfo);

        for (String key : map.keySet()) {
            BillInfoEntity info = new BillInfoEntity();
            BeanUtils.copyProperties(billInfo, info, "id", "type", "customerCode", "itemList");
            info.setParentId(billInfo.getId());
            info.setType("02");
            info.setItemList(map.get(key));
            info.setSupplyCode(key);
            this.doSave(info);
        }
        return true;
    }

    /**
     * 分离账单明细
     *
     * @param itemList
     * @return
     */
    private Map<String, List<BillItemEntity>> separateItemList(List<BillItemEntity> itemList) {
        Map<String, List<BillItemEntity>> map = new HashMap<>(4);
        for (int i = 0; i < itemList.size(); i++) {
            List<BillItemEntity> list = map.get(itemList.get(i).getSupplyCode());
            if (list == null) {
                list = new ArrayList<>();
            }
            BillItemEntity item = new BillItemEntity(itemList.get(i));
            list.add(item);
            map.put(itemList.get(i).getSupplyCode(), list);
        }
        return map;
    }

    /**
     * 保存账单
     *
     * @param billInfoEntity 账单信息
     */
    public void doSave(BillInfoEntity billInfoEntity) {
        synchronized (this) {
            boolean haveId = StringUtils.isBlank(billInfoEntity.getId());
            if (haveId) {
                billInfoEntity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                billInfoEntity.setBillNo(createBillNo());
            }
            // 保存费用明细
            List<BillItemEntity> itemList = billInfoEntity.getItemList();
            BigDecimal allCost = new BigDecimal(0);
            for (int i = 0; i < itemList.size(); i++) {
                itemList.get(i).setId(UUID.randomUUID().toString().replaceAll("-", ""));
                allCost = allCost.add(itemList.get(i).getPrice());
                itemList.get(i).setInfoId(billInfoEntity.getId());
                itemList.get(i).setCreateBy(billInfoEntity.getCreateBy());
                itemList.get(i).setCreateTime(billInfoEntity.getCreateTime());
                billItemService.add(itemList.get(i));
            }

            // 计算一些值后保存
            billInfoEntity.setBeforeTax(allCost);
            // 计算增值税 税率 = 税前 * 6%
            billInfoEntity.setVatRate(allCost.multiply(new BigDecimal(0.06)).setScale(2, BigDecimal.ROUND_HALF_UP));
            // 税后 = 税前 - 税
            billInfoEntity.setAfterTax(allCost.subtract(billInfoEntity.getVatRate()));
            billInfoEntity.setConfirmedMoney(new BigDecimal(0));
            billInfoEntity.setUnconfirmedMoney(allCost);
            if (haveId) {
                this.save(billInfoEntity);
            } else {
                this.updateById(billInfoEntity);
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(BillInfoEntity billInfo) {
        List<BillItemEntity> itemList = billInfo.getItemList();
        BillInfoEntity billInfoEntity = getById(billInfo.getId());
        Map<String, List<BillItemEntity>> map = separateItemList(itemList);
        // 删除原先的客户账单费用明细
        billItemService.deleteByInfoId(billInfoEntity.getId());
        // 保存最新费用明细
        billInfoEntity.setItemList(itemList);
        this.doSave(billInfoEntity);

        for (String key : map.keySet()) {
            List<BillItemEntity> list = map.get(key);
            updateBillItem(list, billInfoEntity, key);
        }
        return true;
    }

    /**
     * 更新账单明细
     *
     * @param itemList
     * @param billInfo
     * @param supplyCode
     */
    private void updateBillItem(List<BillItemEntity> itemList, BillInfoEntity billInfo, String supplyCode) {
        // 根据PARENT_ID和SUPPLY_CODE查找
        BillInfoEntity info = getOne(new QueryWrapper<BillInfoEntity>()
                .eq("PARENT_ID", billInfo.getId())
                .eq("SUPPLY_CODE", supplyCode)
                .eq("STATUS", "0"));
        if (info == null) {
            info = new BillInfoEntity();
            BeanUtils.copyProperties(billInfo, info, "id", "type", "customerCode", "itemList");
            info.setParentId(billInfo.getId());
            info.setType("02");
            info.setSupplyCode(supplyCode);
        } else {
            billItemService.deleteByInfoId(info.getId());
        }
        info.setItemList(itemList);
        // 保存费用明细
        this.doSave(info);
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
     * 生成结算单编号
     *
     * @return
     */
    @Override
    public String createBillNo() {
        // 获取序列号
        int count = baseMapper.getCountForToday() + 1;
        return "DB" + DateUtils.format(new Date(), "yyyyMMdd") + String.format("%03d", count);
    }

    /**
     * 按月份汇总
     *
     * @param params 查询条件
     * @return
     */
    @Override
    public Page getListGroupMonth(Map<String, Object> params) {

        Page page = new Query<>(params).getPage();

        List<Map<String, Object>> list = baseMapper.getListGroupMonth(page, params);
        return page.setRecords(list);
    }

    /**
     * 获取指定年月列表
     *
     * @param params 查询条件
     * @return
     */
    @Override
    public Page getListByMonth(Map<String, Object> params) {

        Page page = new Query<>(params).getPage();

        List<BillInfoEntity> list = baseMapper.getListByMonth(page, params);
        return page.setRecords(list);
    }

    /**
     * 获取指定订单号列表
     *
     * @param params 查询条件
     * @return
     */
    @Override
    public List<BillInfoEntity> getListByOrderNo(Map<String, Object> params) {
        return baseMapper.getListByOrderNo(params);
    }

    /**
     * 根据工作单号获取账单信息
     *
     * @param params
     * @return
     */
    @Override
    public BillInfoEntity getByOrderItem(Map<String, Object> params) {
        return baseMapper.getByOrderItem(params);
    }

    /**
     * 通过ID删除结算单信息
     *
     * @param id     结算单ID
     * @param userId 用户ID
     */
    @Override
    public void deleteById(String id, String userId) {
        baseMapper.deleteById(id, userId, new Date());
    }

    /**
     * 确认账单
     *
     * @param billNo  账单编号
     * @param itemIds 费用项ID
     * @param userId  操作用户
     * @param remark  备注
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmItem(String billNo, String itemIds, String userId, String remark) {
        if (StringUtils.isBlank(billNo) || StringUtils.isBlank(itemIds)) {
            throw new BusinessException("参数不完整");
        }
        List<String> idList = Arrays.asList(itemIds.split(","));
        // 查询费用项
        Collection<BillItemEntity> itemList = billItemService.listByIds(idList);
        Iterator<BillItemEntity> iterator = itemList.iterator();
        Date updateTime = new Date();
        // 确认金额
        BigDecimal confirmMoney = new BigDecimal(0);
        // 修改数据状态
        while (iterator.hasNext()) {
            BillItemEntity bill = iterator.next();
            // 防止已确认的费用项重复确认
            if (StringUtils.ZERO.equals(bill.getIsConfirm())) {
                bill.setIsConfirm("1");
                bill.setUpdateBy(userId);
                bill.setUpdateTime(updateTime);
                confirmMoney = confirmMoney.add(bill.getPrice());
            }
        }
        billItemService.updateBatchById(itemList);
        // 修改账单确认金额
        baseMapper.updateMoney(billNo, confirmMoney, userId, updateTime, remark);
    }

    /**
     * 确认结算单
     *
     * @param orderItem 工作单号
     * @param userId    操作人ID
     */
    @Override
    public void doConfirm(String orderItem, String userId) {
        baseMapper.doConfirm(orderItem, userId, new Date());
    }

    /**
     * 根据月份分组统计
     *
     * @param params 查询参数
     * @return list
     */
    @Override
    public List<Map<String, Object>> findListByGroupMonth(Map<String, Object> params) {
        return baseMapper.getListGroupMonth(null, params);
    }

    /**
     * 统计单月账单费用，根据订单分组
     *
     * @param params 查询参数
     * @return List
     */
    @Override
    public List<BillInfoEntity> findListByGroupOrder(Map<String, Object> params) {
        return baseMapper.getListByMonth(null, params);
    }
}
