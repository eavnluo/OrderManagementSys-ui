/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderHeaderInfoServiceImpl.java
 * 包名称:com.platform.modules.message.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 15:13:17        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.message.dao.MessageOrderHeaderInfoDao;
import com.platform.modules.message.entity.MessageOrderHeaderInfoEntity;
import com.platform.modules.message.service.MessageOrderHeaderInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 订单头表  Service实现类
 *
 * @author jk
 * @date 2019-09-10 15:13:17
 */
@Service("messageOrderHeaderInfoService")
public class MessageOrderHeaderInfoServiceImpl extends ServiceImpl<MessageOrderHeaderInfoDao, MessageOrderHeaderInfoEntity> implements MessageOrderHeaderInfoService {

    @Override
    public List<MessageOrderHeaderInfoEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.UPDATED_TIME");
        params.put("asc", false);
        Page<MessageOrderHeaderInfoEntity> page = new Query<MessageOrderHeaderInfoEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMessageOrderHeaderInfoPage(page, params));
    }

    @Override
    public boolean add(MessageOrderHeaderInfoEntity messageOrderHeaderInfo) {
        return this.save(messageOrderHeaderInfo);
    }

    @Override
    public boolean update(MessageOrderHeaderInfoEntity messageOrderHeaderInfo) {
        return this.updateById(messageOrderHeaderInfo);
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
}
