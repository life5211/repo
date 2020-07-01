package com.xhu.bill.util;

import java.util.regex.Pattern;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-16 22:01
 */
public interface ConstantUtil {

    int SUCCESS = 0;
    int SYSTEM_ERROR = 1;
    String SYSTEM_ERROR_MESSAGE = "系统异常";

    Pattern PATTERN_NUMBER = Pattern.compile("[+-]?\\d{1,20}(\\.\\d+)?");


}
