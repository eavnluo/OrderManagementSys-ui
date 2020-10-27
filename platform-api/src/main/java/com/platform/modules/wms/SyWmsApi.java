package com.platform.modules.wms;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.common.utils.HttpUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.web.ApiBaseController;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseGoodsInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseGoodsInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SY-WMS仓库管理系统平台接口
 * @author Mr.panbb
 * @date 2019-09-20
 */
@Api(tags = "SyWmsApi|SY-WMS仓库管理系统平台接口")
@RestController
@RequestMapping(value = "/sy/wms")
public class SyWmsApi extends ApiBaseController {
    private Logger logger = LoggerFactory.getLogger(SyWmsApi.class);

    @Value("${sy-wms.base-url}")
    private String baseUrl;
    @Value("${sy-wms.name}")
    private String name;
    @Value("${sy-wms.token}")
    private String token;
    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;
    @Autowired
    private BaseGoodsInfoService baseGoodsInfoService;
    @Autowired
    private BaseSupplyInfoService baseSupplyInfoService;

    /**
     * 入库订单下发
     * @return Object
     */
    @GetMapping(value = "/sendIn")
    public RestResponse sendInOrder() {
        // 接口地址
        String api = "/api/Purchase_Order/Add";
        // 请求地址
        String requestUrl = baseUrl + api + "?name=" + name + "&token=" + token;
        // 请求参数
        JSONObject params = mockData0();
        String rData = HttpUtils.sendPost(requestUrl, params.toJSONString(), null);
        return RestResponse.success().put("data", JSONObject.parse(rData));
    }
    /**
     * 出库订单下发
     * @return Object
     */
    @GetMapping(value = "/sendOut")
    public RestResponse sendOutOrder() {
        // 接口地址
        String api = "/api/Sale_Order/Add";
        // 请求地址
        String requestUrl = baseUrl + api + "?name=" + name + "&token=" + token;
        // 请求参数
        JSONObject params = mockData1();
        String rData = HttpUtils.sendPost(requestUrl, params.toJSONString(), null);
        return RestResponse.success().put("data", JSONObject.parse(rData));
    }

    /**
     * 获取仓库列表
     * @return Object
     */
    @GetMapping(value = "/findStorageList")
    public RestResponse findStorageList() {
        // 接口地址
        String api = "/api/Base_Storage/GetStorage";
        // 请求地址
        String requestUrl = baseUrl + api + "?name=" + name + "&token=" + token;
        // 请求参数
        JSONObject params = new JSONObject();
        String rData = HttpUtils.sendPost(requestUrl, params.toJSONString(), null);
        return RestResponse.success().put("data", JSONObject.parse(rData));
    }
    /**
     * 查询货单信息
     * @return Object
     */
    @GetMapping(value = "/getPurchaseOrder")
    public RestResponse getPurchaseOrder() {
        // 接口地址
        String api = "/api/Purchase_Order/GetByCode";
        // 请求地址
        String requestUrl = baseUrl + api + "?OrderCode=SO201910250002";
        // 请求参数
        JSONObject params = new JSONObject();
        params.put("name", name);
        params.put("token", token);
        String rData = HttpUtils.sendPost(requestUrl, "", params);
        return RestResponse.success().put("data", JSONObject.parse(rData));
    }

    /**
     * 查询商品规格
     * @return Object
     */
    @GetMapping(value = "/getGoodsSpec")
    public RestResponse getGoodsSpec() {
        // 接口地址
        String api = "/api/Base_ProductInfo_SpecValue/GetById";
        // 请求地址
        String requestUrl = baseUrl + api + "/1";
        // 请求参数
        JSONObject params = new JSONObject();
        params.put("name", name);
        params.put("token", token);
        String rData = HttpUtils.sendPost(requestUrl, "", params);
        return RestResponse.success().put("data", JSONObject.parse(rData));
    }

    /**
     * 查询商品类别
     * @return Object
     */
    @GetMapping(value = "/findGoodsType")
    public RestResponse findGoodsType() {
        // 接口地址
        String api = "/api/Base_ProductType/Search";
        // 请求地址
        String requestUrl = baseUrl + api + "?name=" + name + "&token=" + token;
        String rData = HttpUtils.sendPost(requestUrl, "", null);
        return RestResponse.success().put("data", JSONObject.parse(rData));
    }

    /**
     * 查询货主列表
     * @return Object
     */
    @GetMapping(value = "/findConsignorList")
    public RestResponse findConsignorList() {
        // 接口地址
        String api = "/api/Base_Consignor/GetConsignor";
        // 请求地址
        String requestUrl = baseUrl + api + "?name=" + name + "&token=" + token;
        String rData = HttpUtils.sendPost(requestUrl, "", null);
        return RestResponse.success().put("data", JSONObject.parse(rData));
    }

    /**
     * 添加货主
     * @return Object
     */
    @GetMapping(value = "/addConsignor")
    public RestResponse addConsignor() {
        // 接口地址
        String api = "/api/Base_Consignor/Add";
        // 请求地址
        String requestUrl = baseUrl + api + "?name=" + name + "&token=" + token;
        // 请求参数
        JSONArray params = mockData3();
        for(int i=0;i<params.size();i++){
            String rData = HttpUtils.sendPost(requestUrl, params.getJSONObject(i).toJSONString(), null);
            logger.error("[添加货主] " + rData);
        }


        return RestResponse.success().put("data", null);
    }

    /**
     * 添加商品信息
     * @return
     */
    @GetMapping(value = "/addproductinformation")
    public RestResponse addproductinformation(){
        // 接口地址
        String api = "/api/Base_ProductInfo/Add";
        // 请求地址
        String requestUrl = baseUrl + api + "?name=" + name + "&token=" + token;
        // 请求参数
        JSONArray params = productInformation();
        for(int i=0;i<params.size();i++){
            String rData = HttpUtils.sendPost(requestUrl, params.getJSONObject(i).toJSONString(), null);
            logger.error("[添加商品信息] " + rData);
        }

        return RestResponse.success().put("data", null);
    }


    /**
     * 添加仓库信息
     * @return
     */
    @GetMapping(value = "/addwarehouseInformation")
    public RestResponse addwarehouseInformation(){
        // 接口地址
        String api = "/api/Base_Storage/Add";
        // 请求地址
        String requestUrl = baseUrl + api + "?name=" + name + "&token=" + token;
        // 请求参数
        JSONArray params = warehouseInformation();
        for(int i=0;i<params.size();i++){
            String rData = HttpUtils.sendPost(requestUrl, params.getJSONObject(i).toJSONString(), null);
            logger.error("[添加仓库信息] " + rData);
        }
        return RestResponse.success().put("data", null);
    }

    /**
     * 添加供应商信息
     * @return
     */
    @GetMapping("/addSupplyInfomation")
    public RestResponse addSupplayInformation(){
        // 接口地址
        String api = "/api/Base_Provider/Add";
        // 请求地址
        String requestUrl = baseUrl + api + "?name=" + name + "&token=" + token;
        // 请求参数
        JSONArray params = supplyInformation();
        JSONObject data=new JSONObject();
        for(int i=0;i<params.size();i++){
            String rData = HttpUtils.sendPost(requestUrl, params.getJSONObject(i).toJSONString(), null);
            logger.error("[添加供应商信息] " + rData);
        }
        return RestResponse.success().put("data", null);
    }
    /**
     * 查询供应商列表
     * @return Object
     */
    @GetMapping(value = "/findProviderList")
    public RestResponse findProviderList() {
        // 接口地址
        String api = "/api/Base_Provider/GetProvider";
        // 请求地址
        String requestUrl = baseUrl + api + "?name=" + name + "&token=" + token;
        String rData = HttpUtils.sendPost(requestUrl, "", null);
        return RestResponse.success().put("data", JSONObject.parse(rData));
    }
    /**
     * 根据商品名称查询库存信息
     */
    @GetMapping(value = "/getInventory")
    public RestResponse getInventory(){
        // 接口地址
        String api = "/api/Base_ProductInfo_Storage/GetByCode";
        // 请求地址
        String requestUrl = baseUrl + api + "?ProductCode=6937110339390";
        // 请求参数
        JSONObject params = new JSONObject();
        params.put("name", name);
        params.put("token", token);
        String rData = HttpUtils.sendPost(requestUrl, "", params);
        return RestResponse.success().put("data", JSONObject.parse(rData));
    }
    /**
     * 根据商品ID查询库存信息
     */
    @GetMapping(value = "/getCommodityIdInventory")
    public RestResponse getCommodityIdInventory(){
        // 接口地址
        String api = "api/Base_ProductInfo_Storage/GetId";
        String requestUrl = baseUrl + api + "/28702";
        // 请求参数
        JSONObject params = new JSONObject();
        params.put("name", name);
        params.put("token", token);
        String rData = HttpUtils.sendPost(requestUrl, "", params);
        return RestResponse.success().put("data", JSONObject.parse(rData));
    }
    /**
     * 根据商品库存ID查询库存信息
     */
    @GetMapping(value = "/getInStockIdInventory")
    public RestResponse getInStockIdInventory(){
        // 接口地址
        String api = "api/Base_ProductInfo_Storage/GetById";
        String requestUrl = baseUrl + api + "/40";
        // 请求参数
        JSONObject params = new JSONObject();
        params.put("name", name);
        params.put("token", token);
        String rData = HttpUtils.sendPost(requestUrl, "", params);
        return RestResponse.success().put("data", JSONObject.parse(rData));
    }
    /**
     * 模拟入库订单
     * @return Object
     */
    public JSONObject mockData0() {
        JSONObject in = new JSONObject();
        JSONArray array=new JSONArray();
        in.put("Order_Id",1);
        in.put("OrderCode","sample string 2");
        in.put("ReturnOrderCode","");
        in.put("Consignor_Id",43);
        in.put("ConsignorCode","HZ20190005");
        in.put("ConsignorName","山东多路驰橡胶股份有限公司");
        in.put("Storage_Id",40);
        in.put("StorageName","武汉物流中心中裕仓库");
        in.put("User_Id",8);
        in.put("UserTrueName","sample string 9");
        in.put("Dept_Id",1);
        in.put("DeptName","仓储部");
        in.put("OrderType","常规预到货");
        in.put("ApplyDate","2019-09-20T10:03:02.3531387+08:00");
        in.put("Provider_Id",1923);
        in.put("ProviderCode","PV201700001");
        in.put("ProviderShortName","滨洲中裕食品有限公司");
        in.put("TrackingNumber","");
        in.put("TotalQuantity",1);
        in.put("ProductionStatus","sample string 17");
        in.put("StatusText","完成");
        in.put("Rate",1.0);
        in.put("ExpressFee",1.0);
        in.put("TotalMoney",1.0);
        in.put("TotalPaidMoney",1.0);
        in.put("Refund",1.0);
        in.put("FinStatusID",64);
        in.put("FinStatusText","sample string 19");
        in.put("TotalRateMoney",1.0);
        in.put("StatusID",64);
        in.put("IsChecking",20);
        in.put("IsArrivalProcess",1);
        in.put("Auditor","sample string 21");
        in.put("Auditing","64");
        in.put("AuditDate","2019-09-20T10:03:02.3531387+08:00");
        in.put("Remark","");
        in.put("CorpURL","sample string 23");
        in.put("IsCiqDeclare","64");
        in.put("ExternalNo","sample string 24");
        in.put("ExternalNo2","sample string 25");
        in.put("SourceId",0);
        in.put("Creator","sample string 27");
        in.put("CreateDate","2019-09-20T10:03:02.3531387+08:00");
        in.put("Modifier","sample string 28");
        in.put("ModifyDate","2019-09-20T10:03:02.3531387+08:00");
        in.put("ReturnStatusText","sample string 29");
        in.put("ReturnStatusID","64");
        in.put("Enable",64);
        in.put("CreateID",1);
        in.put("ModifyID",1);
        JSONObject item = new JSONObject();
        item.put("OrderList_Id",1);
        item.put("Order_Id",2);
        item.put("SourceType",1);
        item.put("SourceMainID",1);
        item.put("SourceListID",1);
        item.put("Product_Id",3);
        item.put("ProductCode","LT0001");
        item.put("ProductName","轮胎A");
        item.put("ProductModel","LT0001");
        item.put("ProductSpec","商品规格");
        item.put("SpecAlias","sample string 8");
        item.put("ProductBarCode","sample string 9");
        item.put("Quantity",10);
        item.put("EnterQuantity",1);
        item.put("Overcharges",1.0);
        item.put("PurchasePrice",1.0);
        item.put("PurchaseMoney",1.0);
        item.put("RelationCode","sample string 11");
        item.put("BatchNumber","sample string 12");
        item.put("ProduceDate","2019-09-20T10:03:02.3531387+08:00");
        item.put("ShelfLifeDate","sample string 13");
        item.put("NoReceivingDate","2019-09-20T10:03:02.3531387+08:00");
        item.put("PlateCode","sample string 14");
        item.put("Rate",1.0);
        item.put("RatePrice",1.0);
        item.put("RateMoney",1.0);
        item.put("ReturnQuantity",1);
        item.put("CustomProduct",64);
        item.put("SingleSignCode","sample string 15");
        item.put("AppendParameter","sample string 16");
        item.put("DeliveryDate","2019-09-20T10:03:02.3531387+08:00");
        item.put("SmallUnit","sample string 17");
        item.put("URL","sample string 18");
        item.put("DeclareNo","sample string 19");
        item.put("FromOrderIds","[]");
        item.put("BigUnit","sample string 21");
        item.put("UnitConvert",1.0);
        item.put("UnitConvertText","sample string 22");
        item.put("Enable",64);
        item.put("Remark","商品备注");
        item.put("CreateID",1);
        item.put("Creator","sample string 24");
        item.put("CreateDate","2019-09-20T10:03:02.3531387+08:00");
        item.put("ModifyID",1);
        item.put("Modifier","sample string 25");
        item.put("ModifyDate","2019-09-20T10:03:02.3531387+08:00");
        item.put("SourceId",0);
        item.put("RecordAction",0);
        array.add(item);
        in.put("Purchase_OrderList",array);
        in.put("RecordAction",0);
        return in;
    }

    /**
     * 模拟出库订单
     * @return Object
     */
    public JSONObject mockData1() {
        JSONObject out=new JSONObject();
        JSONArray array=new JSONArray();
        out.put("Order_Id",1);
        out.put("OrderCode","sample string 2");
        out.put("StoreOrderCode","sample string 3");
        out.put("OrderChannel",64);
        out.put("Storage_Id",40);
        out.put("StorageName","武汉物流中心中裕仓库");
        out.put("GiftMessage","sample string 5");
        out.put("Remark","sample string 6");
        out.put("ApplyDate","2019-09-25T14:31:53.3374004+08:00");
        out.put("TotalQuantityOrder",1);
        out.put("ShippingAmount",1.0);
        out.put("DiscountAmount",1.0);
        out.put("GrandTotal",1.0);
        out.put("TotalPaid",1.0);
        out.put("Email","sample string 7");
        out.put("Mobile","sample string 8");
        out.put("Telephone","sample string 9");
        out.put("PostCode","PV201700001");
        out.put("ProvinceName","滨洲中裕食品有限公司");
        out.put("CityName","sample string 12");
        out.put("RegionName","sample string 13");
        out.put("CountryName","sample string 14");
        out.put("ShippingName","sample string 15");
        out.put("ShippingAddress","sample string 16");
        out.put("StoreOrderId","sample string 17");
        JSONObject item=new JSONObject();
        item.put("OrderList_Id",1);
        item.put("Order_Id",1);
        item.put("Product_Id",2);
        item.put("ProductCode","LT0001");
        item.put("ProductName","轮胎A");
        item.put("ProductModel","LT0001");
        item.put("ProductSpec","商品规格");

        item.put("QuantityOrder",7);
        item.put("SalePrice",1.0);
        item.put("DiscountAmount",1.0);
        item.put("DiscountRate",1.0);
        item.put("SubTotal",1.0);
        item.put("RowTotal",1.0);
        item.put("RowPaid",1.0);
        item.put("ShippingAmount",1.0);
        item.put("Weight",1.0);
        item.put("SmallUnit","sample string 8");
        item.put("Remark","商品备注");
        item.put("ConsignorCode","sample string 10");
        item.put("ConsignorName","sample string 11");
        item.put("StoreOrderId","sample string 12");
        array.add(item);
        out.put("Sale_OrderList",array);
        out.put("ExpressCorpType",64);
        out.put("ExpressCorp_Id",1);
        out.put("ExpressCorpName","sample string 18");
        out.put("ExpressCode","sample string 19");
        out.put("ConsignorCode","sample string 2");
        out.put("ConsignorName","sample string 3");
        out.put("BillingName","sample string 22");
        out.put("BillingZip","sample string 23");
        out.put("BillingAddress","sample string 24");
        out.put("BillingTel","sample string 25");
        out.put("BillingCountry","sample string 26");
        return out;
    }

    /**
     * 货主信息
     * @return Object
     */
    public JSONArray mockData3() {
        List<BaseCustomerInfoEntity> list = baseCustomerInfoService.getByOwner();
        JSONArray array = new JSONArray();
        for (BaseCustomerInfoEntity goods : list) {
            JSONObject owner = new JSONObject();
            JSONArray array1 = new JSONArray();
            owner.put("Consignor_Id", goods.getInfoNum());
            owner.put("ConsignorCode", goods.getCustomerCode());
            owner.put("ConsignorName", goods.getCustomerName());
            owner.put("Password", "");
            owner.put("Linker", goods.getLinkName());
            owner.put("Address", goods.getCustomerAddress());
            owner.put("Zip", "sample string 7");
            owner.put("Mobile", goods.getLinkPhone());
            owner.put("Tel", "sample string 9");
            owner.put("Fax", "sample string 10");
            owner.put("QQ", "sample string 11");
            owner.put("Bank", "sample string 12");
            owner.put("BankCode", "sample string 13");
            owner.put("RaxCode", "sample string 14");
            owner.put("CorpURL", "sample string 15");
            owner.put("Email", "sample string 16");
            owner.put("ArtificiaPerson", "sample string 17");
            owner.put("Overcharges", 1.0);
            owner.put("QualityPlan", "sample string 18");
            owner.put("QualityProportion", 1.0);
            owner.put("IsNeedPeriod", 1);
            owner.put("ShelfLifeDay", 1.0);
            owner.put("NoReceivingRate", 1.0);
            owner.put("StopSaleday", 1);
            owner.put("Enable", 1);
            owner.put("Token", goods.getCustomerCode());
            owner.put("Remark", "sample string 20");
            owner.put("CreateID", 1);
            owner.put("Creator", "sample string 21");
            owner.put("CreateDate", "2019-09-20T10:48:32.6543989+08:00");
            owner.put("ModifyID", 1);
            owner.put("Modifier", "sample string 22");
            owner.put("ModifyDate", "2019-09-20T10:48:32.6543989+08:00");
            owner.put("AreaName", "sample string 23");
            JSONObject item = new JSONObject();
            item.put("Id", 3);
            item.put("Consignor_Id", 1);
            item.put("Storage_Id", 2);
            item.put("StorageName", "");
            item.put("CreateID", 4);
            item.put("Creator", "");
            item.put("CreateDate", "2019-09-20T10:48:32.6563729+08:00");
            item.put("ModifyID", 1);
            item.put("Modifier", "");
            item.put("ModifyDate", "2019-09-20T10:48:32.6563729+08:00");
            item.put("RecordAction", 0);
            array1.add(item);
            owner.put("Base_ConsignorList", array1);
            owner.put("RecordAction", 0);
            array.add(owner);
        }
        return array;
    }

    /**
     * 商品信息
     */
    public JSONArray productInformation(){
        List<BaseGoodsInfoEntity> list=baseGoodsInfoService.findGoodsList();
        JSONArray array=new JSONArray();
        int num = 0;
        for(BaseGoodsInfoEntity goods:list){
            num++;
            JSONObject data=new JSONObject();
            data.put("Product_Id", goods.getInfoNum());
            data.put("Consignor_Id", 50);
            data.put("ConsignorCode","KSWBJS");
            data.put("ConsignorName","凯晟物联科技有限公司");
            data.put("ProductCode", goods.getGoodsCode());
            data.put("ProductName", goods.getGoodsName());
            data.put("ProductModel",goods.getBarCode());
            data.put("Brand_Id", 1);
            data.put("BrandName", goods.getBrand());
            data.put("Type_Id", 1);
            data.put("TypeName", "轮胎");
            data.put("Provider_Id", formatNum(num));
            data.put("ProviderCode", "GYS"+formatNum(num));
            data.put("ProviderShortName", "");
            data.put("ProductSpec", goods.getSpecification());
            data.put("SmallUnit", "");
            data.put("BigUnit", "");
            data.put("CIQName", "");
            data.put("MiddleUnit", "");
            data.put("MiddleUnitConvert", 1.0);
            data.put("UnitConvert", 1.0);
            data.put("RelatedCode", "");
            data.put("ProductBarCode", "");
            data.put("BatchAttribute", "");
            data.put("ShelfLifeYear", 1.0);
            data.put("ShelfLifeApril", 1.0);
            data.put("ShelfLifeDay", 1.0);
            data.put("Long", goods.getGoodsLong());
            data.put("Wide", goods.getGoodsWidth());
            data.put("High", goods.getGoodsHigh());
            data.put("PositionName", "");
            data.put("NetWeight", goods.getGoodsWeight());
            data.put("Weight", "");
            data.put("QualityPlan", "");
            data.put("QualityProportion", 1.0);
            data.put("PackageNumber",1);
            data.put("IsNeedPeriod", "");
            data.put("StopSaleday", "");
            data.put("CustomsCode","");
            data.put("TrafficLimitation", "");
            data.put("Remark", "");
            data.put("PurchasePrice", 1.0);
            data.put("DayPrice", 1.0);
            data.put("SalePrice", 1.0);
            data.put("VipPrice", 1.0);
            data.put("ActivityPrice", 1.0);
            data.put("ProductDesc", "");
            data.put("IsFullContainerLoad", 64);
            data.put("StorageUpper", 1.0);
            data.put("StorageLower", 1.0);
            data.put("RelationCode","");
            data.put("Enable", "");
            data.put("TypeId", 31);
            data.put("ImageList", "");
            data.put("OpenSpec", 64);
            array.add(data);
        }
        return array;
    }


    /**
     * 查询仓库信息
     */

    public JSONArray warehouseInformation(){
        List<BaseSupplyInfoEntity> list= baseSupplyInfoService.getBySupplyType();
        JSONArray array=new JSONArray();
        for (BaseSupplyInfoEntity goods:list){
            JSONObject data=new JSONObject();
            data.put("Storage_Id", goods.getInfoNum());
            data.put("StorageCode", goods.getSupplyCode());
            data.put("StorageName", goods.getSupplyName());
            data.put("StorageAddress", goods.getSupplyAddress());
            data.put("StorageUrl", "sample string 5");
            array.add(data);
        }
        return array;
    }

    /**
     * 供应商信息
     * @return
     */
    public JSONArray supplyInformation(){
        List<BaseSupplyInfoEntity> list=baseSupplyInfoService.getBySupply();
        JSONArray array=new JSONArray();
        int num=0;
        for (BaseSupplyInfoEntity goods:list){
            num++;
            JSONObject data=new JSONObject();
            data.put("Provider_Id", 1);
            data.put("PinYinCode", "sample string 2");
            data.put("ProviderCode", goods.getSupplyCode());
            data.put("ProviderShortName", "GYS"+formatNum(num));
            data.put("ProviderName", goods.getSupplyName());
            data.put("Consignor_Id", 1);
            data.put("ConsignorCode", "sample string 6");
            data.put("ConsignorName", "sample string 7");
            data.put("Password", "sample string 8");
            data.put("City_Id", 1);
            data.put("CityName", goods.getCity());
            data.put("Type_Id", 1);
            data.put("TypeName", goods.getType());
            data.put("Linker",goods.getLinkName());
            data.put("Address", goods.getSupplyAddress());
            data.put("Zip", goods.getPostcode());
            data.put("Mobile", goods.getPhone());
            data.put("Tel", "sample string 15");
            data.put("Fax", "sample string 16");
            data.put("QQ", "sample string 17");
            data.put("MSN", "sample string 18");
            data.put("EnpterType", "sample string 19");
            data.put("Email", "sample string 20");
            data.put("CorpURL", "sample string 21");
            data.put("Bank", "sample string 22");
            data.put("BankCode", "sample string 23");
            data.put("RaxCode", "sample string 24");
            data.put("ArtificiaPerson", "sample string 25");
            data.put("TransitDays", 1.1);
            data.put("MoneyUpper", 1.1);
            data.put("Remark" ,"sample string 26");
            data.put("AccountsMode", "sample string 27");
            data.put("AccountsPeriod", "sample string 28");
            data.put("EnpterMode", "sample string 29");
            data.put("ContractStart", "2019-10-18T14:26:31.2992393+08:00");
            data.put("ContractEnd", "2019-10-18T14:26:31.2992393+08:00");
            data.put("TicketType", "sample string 30");
            data.put("CooperationMode", "sample string 31");
            data.put("IsFanDianFee", 64);
            data.put("FanDianFee", "sample string 32");
            data.put("ProductDiscount", "sample string 33");
            data.put("Rate", 1.0);
            data.put("CreditLine", 1.0);
            data.put("Overcharges", 1.0);
            data.put("QualityPlan", "sample string 34");
            data.put("QualityProportion", 1.0);
            data.put("IsNeedPeriod",1);
            data.put("ShelfLifeDay" ,1.0);
            data.put("NoReceivingRate", 1.0);
            data.put("StopSaleday", 1);
            data.put("OrderNo",1);
            data.put("CreateID", 1);
            data.put("Creator", "sample string 35");
            data.put("CreateDate", "2019-10-18T14:26:31.301184+08:00");
            data.put("ModifyID",1);
            data.put("Modifier", "sample string 36");
            data.put("ModifyDate","2019-10-18T14:26:31.301184+08:00");
            data.put("DeleteBy", "sample string 37");
            data.put("Enable",1);
            data.put("RecordAction", 0);
            array.add(data);
        }
        return array;
    }

    public String formatNum(int num){
        if (num < 10) {
            return "0" + num;
        }
        return "" + num;
    }

    @GetMapping("/GetByCode")
    public RestResponse getByCode(){
        //http://47.92.123.149:9010/api/Sale_Order/GetByCode?OrderCode=SO201909100001
        String api = "/api/Sale_Order/GetByCode";
        // 请求地址
        String requestUrl = baseUrl + api + "?OrderCode=" + "SO201909100001";
        // 请求参数
        JSONObject params = new JSONObject();
        params.put("name", name);
        params.put("token", token);
        String rData = HttpUtils.sendPost(requestUrl, "", params);
        return RestResponse.success().put("rData",JSONObject.parseObject(rData));
    }
}
