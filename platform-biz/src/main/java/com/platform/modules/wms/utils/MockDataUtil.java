package com.platform.modules.wms.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.platform.common.utils.DateUtils;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.order.entity.OrderDetailGoodsInfoEntity;
import com.platform.modules.order.entity.OrderHeaderInfoEntity;

import java.util.Date;
import java.util.List;

/**
 * 数据组装工具类
 *
 * @author Mr.panbb
 * @date 2019-10-17
 */
public class MockDataUtil {
    /**
     * 组装入库订单数据
     *
     * @param order     订单信息
     * @param goodsList 订单商品信息
     * @param buyer
     * @param store
     * @param carSupply
     * @return Object
     */
    public static JSONObject inOrderData(OrderHeaderInfoEntity order,
                                         List<OrderDetailGoodsInfoEntity> goodsList,
                                         BaseCustomerInfoEntity buyer,
                                         BaseSupplyInfoEntity store,
                                         BaseSupplyInfoEntity carSupply) {

        JSONObject in = new JSONObject();
        JSONArray array = new JSONArray();
        Integer amount = 0;
        double totalMoney = 0.0;
        in.put("Order_Id", order.getInfoNum());//预到货单ID
        in.put("OrderCode", order.getOrderItem());//预到货单号
        in.put("ReturnOrderCode", "");//退货单号
        in.put("Consignor_Id", buyer.getInfoNum());//货主ID-即买方
        in.put("ConsignorCode", order.getBuyerCode());//货主编号
        in.put("ConsignorName", order.getBuyerName());//货主名称
        in.put("Storage_Id", store.getInfoNum());//所属仓库ID
        in.put("StorageName", order.getStoreHouseName());//仓库名称
        in.put("User_Id", 8);//经手人ID
        in.put("UserTrueName", "sample string 9");//经手人
        in.put("Dept_Id", 1);//部门ID
        in.put("DeptName", "仓储部");//部门
        in.put("OrderType", "常规预到货");//单据类型
        in.put("ArrivedDate","2019-11-01 13:08:48");// 预计到货时间
        in.put("ApplyDate", DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));//申请日期
        in.put("Provider_Id", carSupply.getInfoNum());//供应商ID,即卡车供应商
        in.put("ProviderCode", order.getSupplyCode());//供应商编号
        in.put("ProviderShortName", order.getSupplyName());//供应商名称
        in.put("TrackingNumber", "");//预到货识别码
        in.put("ProductionStatus", "sample string 17");//生产状态
        in.put("Rate", 1.0);//税率
        in.put("ExpressFee", 1);//快递费
        in.put("Refund", 0.0);//退款额
        in.put("FinStatusID", 1);//付款状态ID
        in.put("FinStatusText", "sample string 19");//付款状态
        in.put("TotalRateMoney", 1.0);//合计价税
        in.put("StatusID", 1);//订单状态ID
        in.put("StatusText", "新建");//订单状态
        in.put("IsChecking", 20);//需要质检
        in.put("IsArrivalProcess", 1);//到货加工
        in.put("Auditor", "sample string 21");//审核人
        in.put("Auditing", "64");//审核
        in.put("AuditDate", "2019-09-20 10:03:02");//审核日期
        in.put("Remark", "");//备注
        in.put("CorpURL", "sample string 23");//供应商URL
        in.put("IsCiqDeclare", "64");//需要海关报检
        in.put("ExternalNo", "sample string 24");//跨境平台系统采购单号
        in.put("ExternalNo2", "sample string 25");//外部采购单号
        in.put("SourceId", 0);//来源ID
        in.put("Creator", order.getCreatedBy());//创建人
        in.put("CreateDate", DateUtils.format(order.getCreatedTime(),DateUtils.DATE_TIME_PATTERN));//创建时间
        in.put("Modifier", order.getUpdatedBy());//修改人
        in.put("ModifyDate", DateUtils.format(order.getUpdatedTime(),DateUtils.DATE_TIME_PATTERN));//修改时间
        in.put("ReturnStatusText", "sample string 29");//退货状态
        in.put("ReturnStatusID", "64");//退货状态ID
        in.put("Enable", 64);//是否可用
        in.put("CreateID", 1);//创建人ID
        in.put("ModifyID", 1);//修改人ID
        in.put("ReceiveDate","2019-11-01 13:08:48");
        for (OrderDetailGoodsInfoEntity goods : goodsList) {
            JSONObject item = new JSONObject();
            item.put("OrderList_Id", 1);//采购明细ID
            item.put("Order_Id", 2);//采购单ID
            item.put("SourceType", 1);//来源类别
            item.put("SourceMainID", 1);//来源ID
            item.put("SourceListID", 1);//来源明细ID
            item.put("Product_Id", goods.getLineNum());//商品ID
            item.put("ProductCode", goods.getGoodsCode());//商品编号
            item.put("ProductName", goods.getGoodsName());//商品名称
            item.put("ProductModel", goods.getBarCode());//条形码
            item.put("ProductSpec", goods.getGoodsModel());//商品规格
            item.put("SpecAlias", "sample string 8");//规格值别名
            item.put("ProductBarCode", goods.getBarCode());//商品型号
            item.put("Quantity", goods.getGoodsNum().intValue());//数量
            item.put("EnterQuantity", 0);//已收货数量
            item.put("Overcharges", 1.0);//超收百分比%
            item.put("PurchasePrice", goods.getPrice());//单价
            item.put("PurchaseMoney", goods.getAmount());//金额
            item.put("RelationCode", "sample string 11");//关联码
            item.put("BatchNumber", "sample string 12");//批次号
            item.put("ProduceDate", "2019-09-20 10:03:02");//生产日期
            item.put("ShelfLifeDate", "sample string 13");//保质期
            item.put("NoReceivingDate", "2019-09-20 10:03:02");//禁收日期
            item.put("PlateCode", "sample string 14");//厂家拍号
            item.put("Rate", 1.0);//税率
            item.put("RatePrice", 1.0);//稅价
            item.put("RateMoney", 1.0);//价税合计
            item.put("ReturnQuantity", 1);//退货数量
            item.put("CustomProduct", 64);//定制商品
            item.put("SingleSignCode", "sample string 15");//定制唯一码
            item.put("AppendParameter", "sample string 16");//定制值
            item.put("DeliveryDate", "2019-11-01 13:08:48");//预计到货日期
            item.put("SmallUnit", "sample string 17");//小单位
            item.put("URL", "sample string 18");//参考URL
            item.put("DeclareNo", "sample string 19");//保险单号
            item.put("FromOrderIds", "[]");//关联客户订单
            item.put("BigUnit", "sample string 21");//大单位
            item.put("UnitConvert", 1.0);//换算关系
            item.put("UnitConvertText", "sample string 22");//单位关系
            item.put("Enable", 64);//是够可用
            item.put("Remark", "商品备注");//备注
            item.put("CreateID", 1);//创建人ID
            item.put("Creator", goods.getCreatedBy());//创建人
            item.put("CreateDate", DateUtils.format(goods.getCreatedTime(),DateUtils.DATE_TIME_PATTERN));//创建时间
            item.put("ModifyID", 1);//修改人ID
            item.put("Modifier", goods.getUpdatedBy());//修改人
            item.put("ModifyDate", DateUtils.format(goods.getUpdatedTime(),DateUtils.DATE_TIME_PATTERN));//修改时间
            item.put("SourceId", 0);//来源采购ID
            item.put("RecordAction", 0);//记录操作动作
            array.add(item);
            amount = amount + goods.getGoodsNum().intValue();
            totalMoney = totalMoney + goods.getAmount();
        }
        in.put("TotalMoney", totalMoney);//合计总额
        in.put("TotalPaidMoney", totalMoney);//付款金额
        in.put("TotalQuantity", amount);//合计数量
        in.put("Purchase_OrderList", array);
        in.put("RecordAction", 0);//记录操作动作
        return in;
    }

    /**
     * 组装出库数据
     *
     * @param order     订单信息
     * @param goodsList 商品列表
     * @param store
     * @return Object
     */
    public static JSONObject outOrderData(OrderHeaderInfoEntity order, List<OrderDetailGoodsInfoEntity> goodsList, BaseSupplyInfoEntity store) {

        JSONObject out = new JSONObject();
        JSONArray array = new JSONArray();
        Integer amount = 0;
        double totalMoney = 0.0;
        out.put("Order_Id", 1);//销售单ID
        out.put("OrderCode", "");//销售单号
        out.put("StoreOrderCode", order.getOrderItem());//店铺订单号
        out.put("OrderChannel", 64);//订单渠道
        out.put("Storage_Id", store.getInfoNum());//所属仓库ID
        out.put("StorageName", order.getStoreHouseName());//所属仓库
        out.put("GiftMessage", "sample string 5");//客户留言
        out.put("Remark", "sample string 6");//备注
        out.put("ApplyDate", order.getCreatedTime());//下单日期
        out.put("ShippingAmount", 1);//应付快递费
        out.put("DiscountAmount", 0.0);//折扣金额
        out.put("TotalPaid", 1.0);//实付金额
        out.put("Email", "sample string 7");//电子邮件
        out.put("Mobile", "");//手机号码
        out.put("Telephone", "");//电话
        out.put("PostCode", "000000");//邮编
        out.put("ProvinceName", "");//省
        out.put("CityName", "");//市
        out.put("RegionName", "");//区
        out.put("CountryName", "sample string 14");//国家
        out.put("ShippingName", "");//收货人
        out.put("ShippingAddress", "");//收货地址
        out.put("StoreOrderId", order.getInfoNum());//店铺订单ID
        out.put("StatusID", 1);//订单状态ID
        out.put("StatusText", "新建");//订单状态
        for (OrderDetailGoodsInfoEntity goods : goodsList) {
            JSONObject item = new JSONObject();
            item.put("OrderList_Id", 1);//订单明细ID
            item.put("Order_Id", 1);//销售ID
            item.put("Product_Id", goods.getLineNum());//商品ID
            item.put("ProductCode", goods.getGoodsCode());//编码
            item.put("ProductName", goods.getGoodsName());//商品名称
            item.put("ProductModel", goods.getBarCode());//条形码
            item.put("ProductSpec", goods.getGoodsModel());//商品规格
            item.put("QuantityOrder", goods.getGoodsNum().intValue());//订购数量
            item.put("SalePrice", goods.getPrice());//销售单价
            item.put("DiscountAmount", 1.0);//优惠金额
            item.put("DiscountRate", 1.0);//货品折扣
            item.put("SubTotal", 1.0);//贷款金额
            item.put("RowTotal", goods.getAmount());//应收金额
            item.put("RowPaid", goods.getAmount());//实收金额
            item.put("ShippingAmount", 1.0);//邮资成本
            item.put("Weight", 1.0);//重量
            item.put("SmallUnit", "sample string 8");//单位
            item.put("Remark", "商品备注");//备注
            item.put("ConsignorCode", order.getBuyerCode());//货主编号
            item.put("ConsignorName", order.getBuyerName());//货主名称
            item.put("StoreOrderId", order.getOrderItem());//店铺订单号
            array.add(item);
            amount = amount + goods.getGoodsNum().intValue();
            totalMoney = totalMoney + goods.getAmount();
        }
        out.put("TotalQuantityOrder",amount);//合计数量
        out.put("GrandTotal", totalMoney);//应收合计
        out.put("Sale_OrderList", array);
        out.put("ExpressCorpType", 64);//快递类别
        out.put("ExpressCorp_Id", 1);//快递ID
        out.put("ExpressCorpName", "sample string 18");//快递名称
        out.put("ExpressCode", "sample string 19");//快递单号
        out.put("ConsignorCode", order.getBuyerCode());//货主Code
        out.put("ConsignorName", order.getBuyerName());//货主名
        out.put("BillingName", order.getSellerName());//发货人
        out.put("BillingZip", "sample string 23");//发件邮编
        out.put("BillingAddress", "");//发件地址
        out.put("BillingTel", "");//发货人电话
        out.put("BillingCountry", "sample string 26");//发件国家
        return out;
    }
}
