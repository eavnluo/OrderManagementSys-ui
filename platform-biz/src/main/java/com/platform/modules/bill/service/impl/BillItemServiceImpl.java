/*
 * 项目名称:platform-plus
 * 类名称:StatementItemServiceImpl.java
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
import com.platform.common.utils.Query;
import com.platform.modules.bill.dao.BillItemDao;
import com.platform.modules.bill.entity.BillItemEntity;
import com.platform.modules.bill.service.BillItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 结算项Service实现类
 *
 * @author Mr.panbb
 * @date 2020-03-30 14:33:17
 */
@Service("billItemService")
public class BillItemServiceImpl extends ServiceImpl<BillItemDao, BillItemEntity> implements BillItemService {

    @Override
    public List<BillItemEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<BillItemEntity> page = new Query<BillItemEntity>(params).getPage();
        return page.setRecords(baseMapper.selectStatementItemPage(page, params));
    }

    @Override
    public boolean add(BillItemEntity statementItem) {
        return this.save(statementItem);
    }

    @Override
    public boolean update(BillItemEntity statementItem) {
        return this.updateById(statementItem);
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

    @Override
    public boolean deleteByInfoId(String billInfoId) {
        if (baseMapper.delete(new QueryWrapper<BillItemEntity>().eq("INFO_ID", billInfoId)) > 0) {
            return true;
        }
        return false;
    }
}
