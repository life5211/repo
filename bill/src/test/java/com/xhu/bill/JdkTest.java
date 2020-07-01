package com.xhu.bill;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author user17
 * @version 1.0
 * @date 2019-10-6 16:41
 */
public class JdkTest {
    @Test
    public void run1() {
        double v = 15.3 * 100 % 3;
        System.out.println(0.3 == 0.1 + 0.2);
        System.out.println(3 == 1 + 2);
        System.out.println(15.3 * 100 % 3 == 0);
        System.out.println(v);
        System.out.println(15.3 * 100 / 300 == 5.1);
        System.out.println(15.3 / 3 == 5.1);
        BigDecimal decimal = new BigDecimal("100");
        BigDecimal divide = decimal.divide(new BigDecimal("3"), 10, BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);
        System.out.println(divide.toString());
    }
}
