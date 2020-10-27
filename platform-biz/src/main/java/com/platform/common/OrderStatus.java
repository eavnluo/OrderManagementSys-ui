package com.platform.common;

/**
 * @author Created by Tp-wangwh on 2019-9-4.
 */
public enum  OrderStatus {

    OrderCreate("1000","订单创建"),
    ConfirmOder("1010","订单确认"),
    TiHuo("1020","提货"),
    OnWay("1030","在途"),
    Arrive("1040","到达"),
    Sign("1050","签收"),
    Complete("1060","完成"),

    InOrderCreate("1000","订单创建"),
    InConfirmOder("1010","订单确认"),
    InTiHuo("1020","提货计划"),
    InOnWay("1030","在途"),
    InFactory("1040","到达工厂"),
    InConfrimTake("1050","确认提货"),
    InBack("1060","返程"),
    InOnStorage("1070","到达仓库"),
    InUnload("1080","卸货"),
    InConfirmScan("1090","扫码确认"),
    InToStorage("1100","入库"),
    InShangjia("1110","上架"),
    InComplete("1120","完成"),
    /*******出库 **********/
    OutOrderCreate("2000","订单创建"),
    OutConfirmOder("2010","订单确认"),
    OutChuStorage("2020","出库计划"),
    OutTiHuo("2030","提货计划"),
    OutScanOut("2040","扫码出库"),
    OutZhuanghuo("2050","装货"),
    OutConfrimTake("2060","确认提货"),
    OutDelivery ("2070","送货"),
    OutArrive("2080","到达"),
    OutUnload("2090","卸货"),
    OutConfirmScan("2100","扫码确认"),
    OutConfirmSign("2110","签收确认"),
    OutComplete("2120","完成");




    private String code;

    private String statusName;

    OrderStatus(String code,String statusName){
        this.code=code;
        this.statusName=statusName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * 根据编号获取枚举对象
     * @param code
     * @return
     */
    public static String getByCode(String code) {
        String value = null;
        for (OrderStatus s : OrderStatus.values()) {
            if (s.code.equals(code)) {
                value = s.statusName;
                break;
            }
        }
        return value;
    }
}
