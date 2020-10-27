/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderDetailGoodInfoServiceImpl.java
 * 包名称:com.platform.modules.message.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 14:53:26        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.message.dao.MessageOrderDetailGoodInfoDao;
import com.platform.modules.message.entity.MessageOrderDetailGoodInfoEntity;
import com.platform.modules.message.service.MessageOrderDetailGoodInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 订单详情表  Service实现类
 *
 * @author 凯晟admin
 * @date 2019-09-10 14:53:26
 */
@Service("messageOrderDetailGoodInfoService")
public class MessageOrderDetailGoodInfoServiceImpl extends ServiceImpl<MessageOrderDetailGoodInfoDao, MessageOrderDetailGoodInfoEntity> implements MessageOrderDetailGoodInfoService {

    @Override
    public List<MessageOrderDetailGoodInfoEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.delFlag");
        params.put("asc", false);
        Page<MessageOrderDetailGoodInfoEntity> page = new Query<MessageOrderDetailGoodInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMessageOrderDetailGoodInfoPage(page, params));
    }

    @Override
    public boolean add(MessageOrderDetailGoodInfoEntity messageOrderDetailGoodInfo) {
        return this.save(messageOrderDetailGoodInfo);
    }

    @Override
    public boolean update(MessageOrderDetailGoodInfoEntity messageOrderDetailGoodInfo) {
        return this.updateById(messageOrderDetailGoodInfo);
    }

    @Override
    public boolean delete(String delFlag) {
        return this.removeById(delFlag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] delFlags) {
        return this.removeByIds(Arrays.asList(delFlags));
    }
}
