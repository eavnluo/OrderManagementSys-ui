/*
 * 项目名称:platform-plus
 * 类名称:InPlanGoodsServiceImpl.java
 * 包名称:com.platform.modules.in.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-09-05 11:00:36        jk     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.wms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.wms.dao.InPlanGoodsDao;
import com.platform.modules.wms.entity.InPlanGoodsEntity;
import com.platform.modules.wms.service.InPlanGoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品集合信息 Service实现类
 *
 * @author jk
 * @date 2019-09-05 11:00:36
 */
@Service("inPlanGoodsService")
public class InPlanGoodsServiceImpl extends ServiceImpl<InPlanGoodsDao, InPlanGoodsEntity> implements InPlanGoodsService {

    @Override
    public List<InPlanGoodsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.UPDATED_TIME");
        params.put("asc", false);
        Page<InPlanGoodsEntity> page = new Query<InPlanGoodsEntity>(params).getPage();
        return page.setRecords(baseMapper.selectInPlanGoodsPage(page, params));
    }

    @Override
    public boolean add(InPlanGoodsEntity inPlanGoods) {
        Date now = new Date();
        inPlanGoods.setCreatedTime(now);
        inPlanGoods.setUpdatedTime(now);
        return this.save(inPlanGoods);
    }

    @Override
    public boolean update(InPlanGoodsEntity inPlanGoods) {
        inPlanGoods.setUpdatedTime(new Date());
        return this.updateById(inPlanGoods);
    }

    @Override
    public boolean delete(String outerGoodsCode) {
        return this.removeById(outerGoodsCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] outerGoodsCodes) {
        return this.removeByIds(Arrays.asList(outerGoodsCodes));
    }

    @Override
    public String queryPackUnit(String goodsCode) {
        return baseMapper.queryPackUnit(goodsCode);
    }
}
