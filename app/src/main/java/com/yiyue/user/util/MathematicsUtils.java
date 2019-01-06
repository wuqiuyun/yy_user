package com.yiyue.user.util;

import java.math.BigDecimal;

/**
 *
 * 计算工具类
 *
 * Created by lyj on 2018/11/21.
 */
public class MathematicsUtils {
    /**
     * * 两个Double数相加 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double add(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.add(b2).doubleValue());
    }

    public static double sub(double v1, double v2) {//减法
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static double div(double v1, double v2) {//除法
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,2,BigDecimal.ROUND_DOWN).doubleValue();//保留两位小数(阶段)
    }

    public static double divInt(double v1, int v2) {//除法1
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Integer.toString(v2));
        return b1.divide(b2,2,BigDecimal.ROUND_DOWN).doubleValue();//保留两位小数(阶段)
    }

    public static double multiply(double v1, double v2) {//乘法
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static double sub1(double v1) {//取两位小数
        BigDecimal bg = new BigDecimal(v1);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }
}
