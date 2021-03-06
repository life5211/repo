package com.xhu.bill.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-28 20:59
 */
public interface Util {

    /**
     * 判断对象是否为空 null || size || length 为零 true
     */
    static boolean isEmpty(Object obj) {
        return !isNotEmpty(obj);
    }

    /**
     * 判断对象非空
     * 不为null 且size不为零
     */
    static boolean isNotEmpty(Object obj) {
        return obj != null && (
                obj instanceof CharSequence && ((CharSequence) obj).length() > 0
                        || obj instanceof Map && !((Map) obj).isEmpty()
                        || obj instanceof Collection && !((Collection) obj).isEmpty()
                        || obj instanceof Object[] && ((Object[]) obj).length > 0
                        || obj instanceof Number);
    }

    /**
     * 判断字符串长度是否在给定范围内
     *
     * @param min int 极小限定
     */
    static boolean isInSize(Object obj, int min) {
        return isInSize(obj, min, Long.MAX_VALUE);
    }

    /**
     * 判断字符串长度是否在给定范围内
     *
     * @param max long 极大限定
     */
    static boolean isInSize(Object obj, long max) {
        return isInSize(obj, 0, max);
    }

    /**
     * 判断对象尺寸是否在给定范围内
     *
     * @see CharSequence
     * @see Map
     * @see Collection
     * @see Number
     */
    static boolean isInSize(Object obj, int min, long max) {
        return obj != null
                && (obj instanceof CharSequence && ((CharSequence) obj).length() >= min && ((String) obj).length() <= max
                || obj instanceof Map && ((Map) obj).size() >= min && ((Map) obj).size() <= max
                || obj instanceof Collection && ((Collection) obj).size() >= min && ((Collection) obj).size() <= max
                || obj instanceof Object[] && ((Object[]) obj).length >= min && ((Object[]) obj).length <= max
                || obj instanceof Number && ((Number) obj).doubleValue() > min && ((Number) obj).doubleValue() <= max
        );
    }

//    static int getSize(Object obj){
//        if (obj == null) {
//            return 0;
//        }
//        if (obj instanceof CharSequence) {
//            return ((CharSequence) obj).length();
//        }
//        if (obj instanceof Map) {
//            return ((Map) obj).size();
//        }
//        if (obj instanceof Collection) {
//            return ((Collection) obj).size();
//        }
//        if (obj.getClass().isArray()) {
////            Arrays.deepToString()
//        }
//    }

    /**
     * 输入正整数返回Integer，否则返回Null
     */
    static Integer getInteger(Object obj) {
        return getInteger(obj, null);
    }

    /**
     * 输入正整数返回Integer，否则返回orElse
     */
    static Integer getInteger(Object obj, Integer orElse) {
        if (obj == null) {
            return orElse;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        if (ConstantUtil.PATTERN_NUMBER.matcher(obj.toString()).matches()) {
            return Integer.valueOf(obj.toString());
        }
        return orElse;
    }

    /**
     * 输入正整数返回Integer，否则返回Null
     */
    static Long getLong(Object obj) {
        return getLong(obj, null);
    }

    /**
     * 输入正整数返回Integer，否则返回orElse
     */
    static Long getLong(Object obj, Long orElse) {
        if (obj == null) {
            return orElse;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        if (ConstantUtil.PATTERN_NUMBER.matcher(obj.toString()).matches()) {
            return Long.valueOf(obj.toString());
        }
        return orElse;
    }

    /**
     * 输入浮点数返回Double，null
     */
    static Double getDouble(Object obj) {
        return getDouble(obj, null);
    }

    /**
     * 输入浮点数返回Double，否则返回orElse
     */
    static Double getDouble(Object obj, Double orElse) {
        if (obj == null) {
            return orElse;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        if (ConstantUtil.PATTERN_NUMBER.matcher(obj.toString()).matches()) {
            return Double.valueOf(obj.toString());
        }
        return orElse;
    }

    /**
     * 输入非空对象返回obj.toString().trim()，否则返回null
     */
    static String getString(Object obj) {
        return getString(obj, null);
    }

    /**
     * 输入非空对象返回obj.toString().trim()，否则返回orElse
     */
    static String getString(Object obj, String orElse) {
        if (obj != null) {
            return obj.toString().trim();
        }
        return orElse;
    }

}
