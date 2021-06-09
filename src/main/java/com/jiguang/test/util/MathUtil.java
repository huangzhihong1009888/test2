package com.jiguang.test.util;

import java.math.BigDecimal;

public class MathUtil {
    public final static char[] BASE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',

            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static double keepDecimals(double number, int several) {
        BigDecimal ra = BigDecimal.valueOf(number);

        return ra.setScale(several, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    public synchronized static String generateShortCode(Long number, Integer arbitraryBase) {
        StringBuilder result = new StringBuilder();

        if (number == null) {
            number = System.currentTimeMillis();

        }

        if (arbitraryBase == null) {
            arbitraryBase = 10;

        }

        if (arbitraryBase > BASE.length) {
            throw new IndexOutOfBoundsException("超过进制转换限制，最大" + BASE.length);

        }

//余数

        int remainder = 0;

//商

        long quotient;

//当 商 = 0 时结束

        while ((quotient = number / arbitraryBase) != 0) {
//取余

            remainder = (int) (number % arbitraryBase);

            result.insert(0, BASE[remainder]);

            number = quotient;

        }

//最后取一次余

        remainder = (int) (number % arbitraryBase);

        result.insert(0, BASE[remainder]);

        int length = result.length();

        if (length < 8) {
            for (int i = 0; i < (8 - length); i++) {
                result.insert(0, BASE[0]);

            }

        }

        return result.toString();

    }

    /**
     * @param tag   实际值
     * @param tag2  目标值
     * @param range 误差范围
     * @throws 2020/9/8 16:50
     * @Description: 检测是否在误差范围内
     * @Return java.lang.Boolean
     * @Author milk
     */

    public static Boolean withinRange(double tag, double tag2, double range) {
        return Math.abs(tag - tag2) <= Math.abs(range);

    }

}
