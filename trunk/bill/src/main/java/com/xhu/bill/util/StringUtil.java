package com.xhu.bill.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author user17
 * @version 1.0
 * @date 2019-10-6 17:59
 */
public interface StringUtil {

    /**
     * 判断数字字符串是否合法
     */
    static boolean isNumberString(String number) {
        return StringUtils.isNotBlank(number) && ConstantUtil.PATTERN_NUMBER.matcher(number).matches();
    }

    /**
     * 判断数字字符串是否合法
     */
    static boolean isNotNumberString(String number) {
        return !isNumberString(number);
    }

}
