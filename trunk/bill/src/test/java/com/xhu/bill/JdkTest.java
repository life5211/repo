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
        BigDecimal decimal = new BigDecimal("100");
        BigDecimal divide = decimal.divide(new BigDecimal("3"),10, BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);
        System.out.println(divide.toString());
    }
}
