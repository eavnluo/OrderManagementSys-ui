package com.platform.common.utils;

import java.text.NumberFormat;

/**
 * 数字工具类
 * @author Mr.panbb
 * @date 2020-03-31
 */
public class NumberUtils {

    /**
     * 保留小数点几位
     * @param num 位数
     * @param value 原始值
     * @return
     */
    public static double format(int num, double value) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(num);
        nf.setMinimumFractionDigits(num);
        return Double.valueOf(nf.format(value));
    }

}
