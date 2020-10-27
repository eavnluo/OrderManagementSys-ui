/*
 * 项目名称:platform-plus
 * 类名称:BaseStatusCodeServiceImpl.java
 * 包名称:com.platform.modules.base.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-09 08:24:39        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.base.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.base.dao.BaseStatusCodeDao;
import com.platform.modules.base.entity.BaseStatusCodeEntity;
import com.platform.modules.base.entity.BaseStatusGroupEntity;
import com.platform.modules.base.service.BaseStatusCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 状态维护表 Service实现类
 *
 * @author 凯晟admin
 * @date 2019-09-09 08:24:39
 */
@Service("baseStatusCodeService")
public class BaseStatusCodeServiceImpl extends ServiceImpl<BaseStatusCodeDao, BaseStatusCodeEntity> implements BaseStatusCodeService {

    @Override
    public List<BaseStatusCodeEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.SORT");
        params.put("asc", true);
        Page<BaseStatusCodeEntity> page = new Query<BaseStatusCodeEntity>(params).getPage();
        return page.setRecords(baseMapper.selectBaseStatusCodePage(page, params));
    }

    @Override
    public boolean add(BaseStatusCodeEntity baseStatusCode) {
        return this.save(baseStatusCode);
    }

    @Override
    public boolean update(BaseStatusCodeEntity baseStatusCode) {
        return this.updateById(baseStatusCode);
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
    public List<BaseStatusGroupEntity> getStatusGroup() {
        List<BaseStatusGroupEntity> groupList = baseMapper.getGroups();
        List<BaseStatusCodeEntity> statusList = baseMapper.getStatusGroup();
        for (BaseStatusGroupEntity baseStatusGroupEntity : groupList) {
            for (BaseStatusCodeEntity baseStatusCodeEntity : statusList) {
                if (baseStatusGroupEntity.getValue().equals(baseStatusCodeEntity.getStatusType())) {
                    List<BaseStatusCodeEntity> list = null == baseStatusGroupEntity.getStatusList() ? new ArrayList<>(15) : baseStatusGroupEntity.getStatusList();
                    list.add(baseStatusCodeEntity);
                    baseStatusGroupEntity.setStatusList(list);
                }
            }
        }
        return groupList;
    }
}
