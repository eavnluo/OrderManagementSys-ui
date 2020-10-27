package com.platform.e6yun.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.utils.HttpUtils;
import com.platform.e6yun.config.CommConfig;
import com.platform.e6yun.service.E6yunService;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 追踪宝接口实现
 * @author Mr.panbb
 * @date 2019-10-17
 */
@Service("e6yunService")
public class E6yunServiceImpl implements E6yunService {

    /**
     * 创建追踪宝轨迹信息，以地图显示的url地址
     * @param equipNo 追踪宝设备编号，例如：863014532875684、863014532875739
     * @param startTime 开始时间 例如：2019-10-17 13:58:00
     * @param endTime 结束时间 例如：2019-10-18 14:00:00
     * @return url
     */
    @Override
    public String createMapUrl(String equipNo, String startTime, String endTime) {
        Map<String, String> params = new LinkedHashMap<>(9);
        params.put("appkey", CommConfig.AppKey);
        params.put("timestamp", CommConfig.GetNowTime());
        params.put("equipno", equipNo);
        params.put("btime", startTime);
        params.put("etime", endTime);
        params.put("effectivetime", "60");
        String sign = CommConfig.GetSign(params);
        params.put("sign", sign);
        // 请求地址
        String url = CommConfig.VehicleMap + "?" + HttpUtils.formatParams(params);
        return url;
    }

    /**
     * 获取车辆轨迹信息
     *
     * @param parameters 请求参数
     * @return 车辆信息 轨迹坐标
     */
    @Override
    public JSONObject getTrackDetail(Map<String, String> parameters) {
        Map<String, String> params = new LinkedHashMap<>(6);
        params.put("method", "GetTrackDetail");
        params.put("timestamp", CommConfig.GetNowTime());
        params.put("appkey", CommConfig.AppKey);
        params.put("format", "json");
        params.put("vehicle", "GB1234");
        params.put("btime", parameters.get("btime"));
        params.put("etime", parameters.get("etime"));

        params.put("isoffsetlonlat", "0");
        params.put("isplacename", "0");
        String sign = CommConfig.GetSign(params);
        params.put("sign", sign);
        // 请求地址
        String paramsUrl =HttpUtils.formatParams(params);
        String url = CommConfig.ReportBaseURL + "?" + paramsUrl.replace(" ", "%20");
        JSONObject data = HttpUtils.sendGet(url);
        return data;
    }
}
