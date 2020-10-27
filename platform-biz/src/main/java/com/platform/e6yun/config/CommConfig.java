package com.platform.e6yun.config;

import com.platform.common.utils.DateUtils;
import com.platform.e6yun.utils.MD5Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * 追踪宝基础配置
 * @author Mr.panbb
 */
public class CommConfig {
	/**
	 * 公钥
	 */
	public static String  AppKey = "8642499c-f398-4ae6-985f-7a9f09a4f536";
	/**
	 * 密钥
	 */
	public static String  AppSecret = "e197ea00-ea4b-49bd-a187-9f5642d41db3";
	public static String  MotionBaseURL = "http://api.e6gps.com/public/v3/Inface/Call";
	public static String  ReportBaseURL = "http://api.e6gps.com/public/v3/StatisticsReport/Call";
	public static String  VehicleMap = "http://api.e6gps.com/public/v3/MapServices/PlayTrack_ZHB.aspx";
	public static String  LoginE6WebGisUrl = "http://login.e6gps.com/Home/LoginWithToken?";


	public static String Format = "json";


	/**
	 * 获取系统当前时间
	 * @return
	 */
	public static String GetNowTime() {
		return DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);
	}

	/**
	 * 获取签名
	 * @param map 签名参数
	 * @return 签名
	 */
	public static String GetSign(Map<String, String> map) {
		//将所有的键放在一个collection进行排序，是为了生成签名用到
		ArrayList<String> keyList = new ArrayList<String>();
		for(String dataKey  : map.keySet()) {
			keyList.add(dataKey);
		}

		//对键进行排序
		Collections.sort(keyList);

		//根据键再从map当中获取值，拼接成字符串
		StringBuilder  sb = new StringBuilder();
		for(String key : keyList) {
			String value = map.get(key);
			if(value !=null) {
				sb.append(key);
				sb.append(value);
			}
		}
		return Sign(sb.toString());
	}

	/**
	 * 生成签名
	 * @param pPrestr 需要签名的字符串
	 * @return 签名结果
	 */
	public static String Sign(String pPrestr) {
		pPrestr = AppSecret + pPrestr + AppSecret;
		String md5Str = MD5Util.str2MD5(pPrestr);
		return md5Str.toUpperCase();
	}
}
