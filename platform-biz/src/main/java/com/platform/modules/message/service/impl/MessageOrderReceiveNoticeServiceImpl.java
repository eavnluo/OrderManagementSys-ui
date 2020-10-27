/*
 * 项目名称:platform-plus
 * 类名称:MessageOrderReceiveNoticeServiceImpl.java
 * 包名称:com.platform.modules.message.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-10 15:25:47        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.message.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.message.dao.MessageOrderReceiveNoticeDao;
import com.platform.modules.message.entity.MessageOrderReceiveNoticeEntity;
import com.platform.modules.message.service.MessageOrderReceiveNoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 订单消息接收表  Service实现类
 *
 * @author jk
 * @date 2019-09-10 15:25:47
 */
@Service("messageOrderReceiveNoticeService")
public class MessageOrderReceiveNoticeServiceImpl extends ServiceImpl<MessageOrderReceiveNoticeDao, MessageOrderReceiveNoticeEntity> implements MessageOrderReceiveNoticeService {

    @Override
    public List<MessageOrderReceiveNoticeEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.delFlag");
        params.put("asc", false);
        Page<MessageOrderReceiveNoticeEntity> page = new Query<MessageOrderReceiveNoticeEntity>(params).getPage();
        return page.setRecords(baseMapper.selectMessageOrderReceiveNoticePage(page, params));
    }

    @Override
    public boolean add(MessageOrderReceiveNoticeEntity messageOrderReceiveNotice) {
        return this.save(messageOrderReceiveNotice);
    }

    @Override
    public boolean update(MessageOrderReceiveNoticeEntity messageOrderReceiveNotice) {
        return this.updateById(messageOrderReceiveNotice);
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
