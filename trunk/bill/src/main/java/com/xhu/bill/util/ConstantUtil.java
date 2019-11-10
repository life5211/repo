package com.xhu.bill.util;

import java.util.regex.Pattern;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-16 22:01
 */
public class ConstantUtil {

    public static final int SUCCESS = 0;
    public static final int SYSTEM_ERROR = 1;
    public static final String SYSTEM_ERROR_MESSAGE = "系统异常";

    public static final Pattern PATTERN_NUMBER_DOUBLE = Pattern.compile("[+-]?\\d{1,20}(\\.\\d+)?");


}
