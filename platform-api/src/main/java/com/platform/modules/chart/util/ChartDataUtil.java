package com.platform.modules.chart.util;

import com.alibaba.fastjson.JSONObject;

/**
 * 构造图表模拟数据
 * @author Mr.panbb
 * @date 2019-09-06
 */
public class ChartDataUtil {

    /**
     * 基本数据
     * @return Object
     */
    public static JSONObject numberData() {
        JSONObject data = new JSONObject();
        data.put("allOrder", 4006);
        data.put("onWayOrder", 4547);
        data.put("storeNum", 47803);
        data.put("storeRate", 99.88);
        data.put("inOrder", 4720);
        data.put("outOrder", 4220);
        return data;
    }

    /**
     * 月度日订单总数量变化
     * @return Object
     */
    public static JSONObject dayOrderData() {
        JSONObject data = new JSONObject();

        return data;
    }

    /**
     * 月度日订单入库数量变化
     * @return Object
     */
    public static JSONObject dayInOrderData() {
        JSONObject data = new JSONObject();

        return data;
    }

    /**
     * 月度日订单总数量变化
     * @return Object
     */
    public static JSONObject dayOutOrderData() {
        JSONObject data = new JSONObject();

        return data;
    }

    /**
     * 月度订单环比总数量变化
     * @return Object
     */
    public static JSONObject monthOrderHb() {
        JSONObject data = new JSONObject();

        return data;
    }

}
