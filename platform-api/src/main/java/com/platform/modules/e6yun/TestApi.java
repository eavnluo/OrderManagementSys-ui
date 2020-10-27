package com.platform.modules.e6yun;

import com.alibaba.fastjson.JSONObject;
import com.platform.common.utils.HttpUtils;
import com.platform.common.utils.RestResponse;
import com.platform.e6yun.config.CommConfig;
import com.platform.modules.web.ApiBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 易流云（追踪宝），接口测试
 * @author Mr.panbb
 * @date 2019-09-27
 */
@RestController
@RequestMapping(value = "/e6yun")
public class TestApi extends ApiBaseController {

    private static Logger logger = LoggerFactory.getLogger(ApiBaseController.class);

    /**
     * 获取车辆轨迹信息
     * @return Object
     */
    @GetMapping(value = "/getTrackDetail")
    public RestResponse getTrackDetail() {
        try {
            Map<String, String> params = new LinkedHashMap<>(9);
            params.put("method", "GetTrackDetail");
            params.put("timestamp", CommConfig.GetNowTime());
            params.put("appkey", CommConfig.AppKey);
            params.put("format", "json");
            params.put("vehicle", "GB1234");
            params.put("btime", "2019-09-12 21:11:54");
            params.put("etime", "2019-09-28 21:11:54");
            params.put("isoffsetlonlat", "0");
            params.put("isplacename", "0");
            String sign = CommConfig.GetSign(params);
            params.put("sign", sign);
            // 请求地址
            String paramsUrl =HttpUtils.formatParams(params);
            String url = CommConfig.ReportBaseURL + "?" + paramsUrl.replace(" ", "%20");
            logger.error(url);
            JSONObject data = HttpUtils.sendGet(url);
            return RestResponse.success().put("data", data);
        } catch (Exception e) {
            logger.error("", e);
        }
        return RestResponse.error();
    }

    /**
     * 创建地图访问URL
     * @return
     */
    @RequestMapping(value = "/createMapUrl")
    public RestResponse createMapUrl() {
        Map<String, String> params = new LinkedHashMap<>(9);
        params.put("appkey", CommConfig.AppKey);
        params.put("timestamp", CommConfig.GetNowTime());
        params.put("equipno", "863014532875684");
        params.put("btime", "2019-10-15 21:11:54");
        params.put("etime", "2019-10-17 21:11:54");
        params.put("effectivetime", "60");
        String sign = CommConfig.GetSign(params);
        params.put("sign", sign);
        // 请求地址
        String url = CommConfig.VehicleMap + "?" + HttpUtils.formatParams(params);
        logger.error(url);
        return RestResponse.success(url);
    }

}
