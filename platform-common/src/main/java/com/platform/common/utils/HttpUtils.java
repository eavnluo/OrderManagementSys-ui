package com.platform.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP请求工具类
 * @author Tepu
 * @date 2019-04-09
 */
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static RequestConfig config;
    static {
        // 请求超时事件配置
        config = RequestConfig.custom().setConnectTimeout(10*1000).setSocketTimeout(10*1000).build();
    }


    /**
     * Get请求
     * @param url 请求地址
     * @return
     */
    public static JSONObject sendGet(String url) {
        JSONObject rData = new JSONObject();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        try {
            get.setConfig(config);

            CloseableHttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 请求成功
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity, "UTF-8");
                if (data != null) {
                    if (data.startsWith("[") && data.endsWith("]")) {
                        // 返回数组
                        rData.put("data", JSONArray.parseArray(data));
                    } else if (data.startsWith("{") && data.endsWith("}")) {
                        // 返回JSON对象
                        rData = JSONObject.parseObject(data);
                    }
                }
            } else {
                // 请求失败
                rData.put("status", response.getStatusLine().getStatusCode());
                rData.put("msg", response.getStatusLine().getReasonPhrase());
            }
        } catch (Exception e) {
            logger.error("sendGet", e);
            rData.put("status", "400");
            rData.put("msg", e.getMessage());
        } finally {
			get.releaseConnection();
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return rData;
    }


    /**
     * post请求传输json参数
     * @param url  url地址
     * @param param 参数
     * @return
     */
    public static String httpPost(String url, JSONObject param) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String rData = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        try {
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            if (null != param) {
                // 请求参数
                List<NameValuePair> parameters = new ArrayList<>(0);
                for (String key : param.keySet()) {
                    parameters.add(new BasicNameValuePair(key, param.get(key).toString()));
                }
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters);
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                try {
                    // 读取服务器返回过来的json字符串数据
                    rData = EntityUtils.toString(result.getEntity(), "utf-8");
                } catch (Exception e) {
                    logger.error("httpPost", e);
                }
            }
            result.close();
        }
        catch (IOException e) {
            logger.error("httpPost", e);
        } finally {
            httpPost.releaseConnection();
            try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return rData;
    }

    /**
     * post请求传输json参数
     * @param url  url地址
     * @param param 参数
     * @param headerParams 表头参数
     * @return
     */
    public static String sendPost(String url, String param, JSONObject headerParams) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String rData = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        try {
            httpPost.setHeader("Content-type", "application/json");
            if (headerParams != null) {
                for (String key : headerParams.keySet()) {
                    httpPost.setHeader(key, headerParams.getString(key));
                }
            }

            if (StringUtils.isNotBlank(param)) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(param, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                try {
                    // 读取服务器返回过来的json字符串数据
                    rData = EntityUtils.toString(result.getEntity(), "utf-8");
                } catch (Exception e) {
                    logger.error("sendPost", e);
                }
            }
            result.close();
        }
        catch (IOException e) {
            logger.error("sendPost", e);
        } finally {
            httpPost.releaseConnection();
            try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return rData;
    }

    /**
     * 组装请求地址
     * @param params 请求参数
     * @return 新的请求地址
     */
    public static String formatParams(Map<String, String> params) {
        if (params == null || params.size() == 0) {
            return "";
        }
        try {
            StringBuilder newUrl = new StringBuilder();
            for (String key : params.keySet()) {
                newUrl.append(key + "=" + params.get(key) + "&");
            }
            return newUrl.deleteCharAt(newUrl.length() - 1).toString();
        } catch (Exception e) {
            logger.error("", e);
        }
        return "";
    }

}
