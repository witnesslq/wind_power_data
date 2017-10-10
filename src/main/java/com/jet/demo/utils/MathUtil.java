package com.jet.demo.utils;

import java.text.DecimalFormat;

/**
 * Copyright 2017 济中节能 All rights reserved.
 * Created by LiLei on 2017/10/10 16:11.
 */
public class MathUtil {
    private static DecimalFormat format = new DecimalFormat();
    /**
     * 保留小数点后几位有效数字
     *
     * @param source 浮点数值
     * @param size   几位有效数字
     * @return Double 保留小数位后的结果
     */
    public static Double keepDigitsAfterPoint(Double source, int size) {
        Double result = null;
        if (source != null && !source.isInfinite() && !source.isNaN()) {
            String pattern = "#";
            if (size > 0) {
                pattern += ".";
                for (int i = 0; i < size; i++) {
                    pattern += "0";
                }
            }
            format.applyPattern(pattern);
            result = Double.parseDouble(format.format(source));
        }
        return result;
    }
}
