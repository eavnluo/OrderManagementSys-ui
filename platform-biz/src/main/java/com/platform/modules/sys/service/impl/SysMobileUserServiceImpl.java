/*
 * 项目名称:platform-plus
 * 类名称:SysMobileUserServiceImpl.java
 * 包名称:com.platform.modules.sys.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-16 16:32:44        凯晟admin     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Query;
import com.platform.common.validator.AbstractAssert;
import com.platform.modules.sys.dao.SysMobileUserDao;
import com.platform.modules.sys.entity.SysMobileUserEntity;
import com.platform.modules.sys.service.SysMobileUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 手持用户Service实现类
 *
 * @author 凯晟admin
 * @date 2019-09-16 16:32:44
 */
@Service("sysMobileUserService")
public class SysMobileUserServiceImpl extends ServiceImpl<SysMobileUserDao, SysMobileUserEntity> implements SysMobileUserService {

    @Override
    public List<SysMobileUserEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<SysMobileUserEntity> page = new Query<SysMobileUserEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSysMobileUserPage(page, params));
    }

    @Override
    public boolean add(SysMobileUserEntity sysMobileUser) {
        return this.save(sysMobileUser);
    }

    @Override
    public boolean update(SysMobileUserEntity sysMobileUser) {
        return this.updateById(sysMobileUser);
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
    public SysMobileUserEntity loginByMobile(String mobile, String password) {
        SysMobileUserEntity user = queryByMobile(mobile);
        AbstractAssert.isNull(user, "该手机暂未绑定用户");

        //密码错误
        if (!user.getPassword().equals(DigestUtils.sha256Hex(password))) {
            throw new BusinessException("手机号或密码错误");
        }

        return user;
    }

    public SysMobileUserEntity queryByMobile(String mobile) {
        return this.getOne(new QueryWrapper<SysMobileUserEntity>().eq("MOBILE", mobile), false);
    }
}
