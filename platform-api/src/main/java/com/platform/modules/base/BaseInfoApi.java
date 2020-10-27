package com.platform.modules.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.utils.RestResponse;
import com.platform.modules.web.ApiBaseController;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseGoodsInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseGoodsInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 监控数据接口
 *
 * @author Mr.panbb
 * @date 2019-09-06
 */
@Api(tags = "BaseInfoApi|基础数据接口")
@RestController
@RequestMapping(value = "/base")
public class BaseInfoApi extends ApiBaseController {

    @Autowired
    private BaseGoodsInfoService baseGoodsInfoService;
    @Autowired
    private BaseSupplyInfoService baseSupplyInfoService;
    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;




    /**
     * 查询工厂信息列表
     *
     * @param params 查询参数
     * @return Object
     */
    @ApiOperation(value = "获取工厂基础数据", notes = "")
    @GetMapping(value = "/findFactoryList")
    public RestResponse findFactoryList(@RequestBody Map<String, Object> params) {
        List<Map<String, Object>> list = baseSupplyInfoService.findList(params);
        return RestResponse.success().put("data", list);
    }

    /**
     * 查询客户信息列表
     * @param type 客户类型
     * @return Object
     */
    @ApiOperation(value = "根据客户类型获取客户数据", notes = "")
    @GetMapping(value = "/findCustomerListByType")
    public RestResponse findCustomerListByType(String type) {
        List<BaseCustomerInfoEntity> list = baseCustomerInfoService.getBaseMapper().selectList(new QueryWrapper<BaseCustomerInfoEntity>().eq("TYPE", type));
        return RestResponse.success().put("data", list);
    }

    /**
     * 获取客户列表
     * @return
     */
    @ApiOperation(value = "获取客户列表", notes = "")
    @GetMapping(value = "/findCustomerList")
    public RestResponse findCustomerList () {
        // 1、查出要组装的数据
        List<BaseCustomerInfoEntity> list=baseCustomerInfoService.findCustomerLists();
        // 2、组装
        JSONArray array = new JSONArray();
        for (BaseCustomerInfoEntity customer : list) {
            JSONObject item = new JSONObject();
            item.put("code", customer.getCustomerCode());
            item.put("name", customer.getCustomerName());
            int cust=0;
            if("F".equals(customer.getType())){
                cust=0;
            }else if("C".equals(customer.getType())){
                cust++;
            }else{
                cust=2;
            }
            item.put("orgType", cust);
            array.add(item);
        }
        return RestResponse.success().put("data", array);
    }
    /**
     * 获取供应商信息
     * @return
     */
    @GetMapping(value = "/findSupplyList")
    public RestResponse findSupplyList(){
        List<BaseSupplyInfoEntity> list = baseSupplyInfoService.getBaseMapper().selectList(new QueryWrapper<BaseSupplyInfoEntity>().eq("TYPE","K"));
        JSONArray array=new JSONArray();
        for (BaseSupplyInfoEntity supply:list) {
            JSONObject item=new JSONObject();
            item.put("username",supply.getSupplyName());
            item.put("name",supply.getLinkName());
            item.put("userType",500);
            item.put("orgCode",supply.getPostcode());
            array.add(item);
        }
        return  RestResponse.success().put("list",array);
    }
    /**
     * 获取库房信息
     */
    @GetMapping(value = "/findStoreList")
    public  RestResponse findStoreList(){
        List<BaseSupplyInfoEntity> list = baseSupplyInfoService.getBaseMapper().selectList(new QueryWrapper<BaseSupplyInfoEntity>().eq("TYPE","F"));
        JSONArray array=new JSONArray();
        for (BaseSupplyInfoEntity supply:list) {
            JSONObject item=new JSONObject();
            item.put("code",supply.getSupplyCode());
            item.put("name",supply.getSupplyName());
            item.put("address",supply.getSupplyAddress());
            item.put("lnglatType","");
            item.put("lng","");
            item.put("lat","");
            item.put("orgCode","");
            array.add(item);
        }
        return  RestResponse.success().put("list",array);
    }

    /**
     * 查询规格信息
     */
    @GetMapping(value = "/findGoodsList")
    public RestResponse findGoodsList(){
        List<BaseGoodsInfoEntity> list=baseGoodsInfoService.findGoodsList();
        JSONArray array=new JSONArray();
        for (BaseGoodsInfoEntity goods:list) {
            JSONObject data=new JSONObject();
            JSONArray  array1=new JSONArray();
            JSONObject item=new JSONObject();
            data.put("code",goods.getGoodsCode());
            data.put("name",goods.getGoodsName());
            item.put("brand",goods.getBrand());
            item.put("specification",goods.getSpecification());
            item.put("unnit",goods.getUnit());
            item.put("origin",goods.getOrigin());
            item.put("price",goods.getPrice());
            item.put("goodsLong",goods.getGoodsLong());
            item.put("goodshigh",goods.getGoodsHigh());
            item.put("goodsWidth",goods.getGoodsWidth());
            item.put("goodsWeight",goods.getGoodsWeight());
            item.put("color",goods.getColor());
            array1.add(item);
            data.put("extAttr",array1);
            array.add(data);
        }
        return  RestResponse.success().put("list",array);
    }


}
