/*
 * 项目名称:platform-plus
 * 类名称:PriceDetailController.java
 * 包名称:com.platform.modules.price.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 13:23:09        Mr.panbb     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.price;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.price.entity.PriceDetailEntity;
import com.platform.modules.price.service.PriceDetailService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysDictEntity;
import com.platform.modules.sys.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 报价单包含的报价项清单Controller
 *
 * @author Mr.panbb
 * @date 2020-03-24 13:23:09
 */
@RestController
@RequestMapping("price/detail")
public class PriceDetailController extends AbstractController {
    @Autowired
    private PriceDetailService priceDetailService;
    @Autowired
    private SysDictService sysDictService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<PriceDetailEntity> list = priceDetailService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询报价单包含的报价项清单
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = priceDetailService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/get")
    public RestResponse get(@RequestParam Map<String, Object> params) {
        PriceDetailEntity priceDetail = priceDetailService.get(params);

        return RestResponse.success().put("detail", priceDetail);
    }

    /**
     * 查询价格清单
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/findList")
    public RestResponse findList(@RequestParam Map<String, Object> params) {
        List<PriceDetailEntity> list = priceDetailService.findList(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    public RestResponse info(@PathVariable("id") String id) {
        PriceDetailEntity priceDetail = priceDetailService.getById(id);

        return RestResponse.success().put("detail", priceDetail);
    }

    /**
     * 新增报价单包含的报价项清单
     *
     * @param priceDetail priceDetail
     * @return RestResponse
     */
    @SysLog("新增报价单包含的报价项清单")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody PriceDetailEntity priceDetail) {

        priceDetailService.add(priceDetail);

        return RestResponse.success();
    }

    /**
     * 修改报价单包含的报价项清单
     *
     * @param priceDetail priceDetail
     * @return RestResponse
     */
    @SysLog("修改报价单包含的报价项清单")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody PriceDetailEntity priceDetail) {

        priceDetailService.update(priceDetail);

        return RestResponse.success();
    }

    /**
     * 根据主键删除报价单包含的报价项清单
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除报价单包含的报价项清单")
    @RequestMapping("/delete")
    public RestResponse delete(@RequestBody String[] ids) {
        priceDetailService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 获取费用项目分类
     * @param params
     * @return
     */
    @SysLog("分类查询费用项")
    @RequestMapping("/findTypeList")
    public RestResponse findTypeList(@RequestParam Map<String, Object> params) {
        List<PriceDetailEntity>  list = priceDetailService.findTypeList(params);
        if (list == null || list.size() == 0) {
            return RestResponse.error("未查到相关的报价信息，请先报价。");
        }
        Map<String, JSONArray> map = new HashMap<>(6);
        list.forEach(d -> {
            JSONArray array = map.get(d.getType());
            if (array == null) {
                array = new JSONArray();
            }
            JSONObject obj = new JSONObject();
            obj.put("code", d.getCode());
            if (StringUtils.ONE.equals(d.getIsTransport())) {
                obj.put("name", d.getName() +"( " + d.getUnit() + ", "+ d.getStartArea() + " - " + d.getEndArea() + " )");
            } else {
                obj.put("name", d.getName() +"( " + d.getUnit() + " )");
            }
            array.add(obj);
            map.put(d.getType(), array);
        });

        // 处理费用类型
        List<SysDictEntity> dictList = sysDictService.queryByCode(params);
        Iterator<String> iterator = map.keySet().iterator();
        List<SysDictEntity> sList = new ArrayList<>(map.keySet().size());
        while (iterator.hasNext()) {
            String value = iterator.next();
            for (int i = 0; i < dictList.size(); i++) {
                if (value.equals(dictList.get(i).getValue())) {
                    sList.add(dictList.get(i));
                    break;
                }
            }
        }
        return RestResponse.success().put("list", list).put("map", map).put("dictList", sList);
    }

}
