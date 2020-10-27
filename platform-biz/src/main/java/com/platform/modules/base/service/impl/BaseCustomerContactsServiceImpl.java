/*
 * 项目名称:platform-plus
 * 类名称:BaseCustomerContactsServiceImpl.java
 * 包名称:com.platform.modules.base.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-04-14 16:12:52        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.common.utils.StringUtils;
import com.platform.modules.base.dao.BaseCustomerContactsDao;
import com.platform.modules.base.entity.BaseCustomerContactsEntity;
import com.platform.modules.base.service.BaseCustomerContactsService;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author zlm
 * @date 2020-04-14 16:12:52
 */
@Service("baseCustomerContactsService")
public class BaseCustomerContactsServiceImpl extends ServiceImpl<BaseCustomerContactsDao, BaseCustomerContactsEntity> implements BaseCustomerContactsService {

    @Override
    public List<BaseCustomerContactsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<BaseCustomerContactsEntity> page = new Query<BaseCustomerContactsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectBaseCustomerContactsPage(page, params));
    }

    @Override
    public boolean add(BaseCustomerContactsEntity baseCustomerContacts) {
        return this.save(baseCustomerContacts);
    }

    @Override
    public boolean update(BaseCustomerContactsEntity baseCustomerContacts) {
        return this.updateById(baseCustomerContacts);
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
    public BaseCustomerContactsEntity getByPhone(String phone, String registerUserId) {
        return getOne(new QueryWrapper<BaseCustomerContactsEntity>().eq("PHONE", phone).eq("REGISTER_USER_ID", registerUserId));
    }

    /**
     * 获取订单收货人发货人列表
     *
     * @param orderHeaderInfo
     * @return
     */
    @Override
    public List<BaseCustomerContactsEntity> getOrderContactsList(OrderHeaderInfoEntity orderHeaderInfo) {
        String receiveGoodsPeopleId = orderHeaderInfo.getReceiveGoodsPeopleId();
        String sendGoodsPeopleId = orderHeaderInfo.getSendGoodsPeopleId();
        List<String> ids  = new ArrayList<>(Constant.TWO);
        if(StringUtils.isNotBlank(receiveGoodsPeopleId)){
            ids.add(receiveGoodsPeopleId);
        }
        if(StringUtils.isNotBlank(sendGoodsPeopleId)){
            ids.add(sendGoodsPeopleId);
        }
        if(ids.size() == 0){
            return new ArrayList<>();
        }
        Map<String,Object> params = new HashMap<>();
        params.put("ids",ids);

        return this.queryAll(params);
    }
}
