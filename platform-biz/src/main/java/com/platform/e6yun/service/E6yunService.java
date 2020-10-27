package com.platform.e6yun.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 追踪宝接口
 * @author Mr.panbb
 * @date 2019-10-17
 */
public interface E6yunService {
    /**
     * 创建追踪宝轨迹信息，以地图显示的url地址
     * @param equipNo 追踪宝设备编号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return url
     */
    String createMapUrl(String equipNo, String startTime, String endTime);

    /**
     * 获取车辆轨迹信息
     * @param params 请求参数
     * @return 车辆信息
     */
    JSONObject getTrackDetail (Map<String,String> params);
}
