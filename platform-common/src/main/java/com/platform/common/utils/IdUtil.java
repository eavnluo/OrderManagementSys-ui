package com.platform.common.utils;

/**
 * ID生成工具类
 * @author Mr.panbb
 * @date 2019-10-18
 */
public class IdUtil {

    /**
     * 生成ID
     * @return ID
     */
    public static String createId () {
        return String.valueOf(System.currentTimeMillis());
    }

}
