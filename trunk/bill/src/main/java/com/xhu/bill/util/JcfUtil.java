package com.xhu.bill.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * java 集合工具类
 *
 * @author user17
 * @version 1.0
 * @date 2019-9-16 18:28
 */
public class JcfUtil {

    public static <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = new LinkedList<>();
        iterable.forEach(list::add);
        return list;
    }

    public static <K extends Serializable, V> MapBuilder<K, V> ofMap(K k, V v) {
        return new MapBuilder<K, V>().put(k, v);
    }

    public static <K extends Serializable, V> Map<K, V> ofSingleMap(K k, V v) {
        Map<K, V> map = new HashMap<>(1);
        map.put(k, v);
        return map;
    }

    public static class MapBuilder<K, V> {
        private Map<K, V> map = new HashMap<>();

        public MapBuilder<K, V> put(K k, V v) {
            this.map.put(k, v);
            return this;
        }

        public Map<K, V> build() {
            return this.map;
        }
    }
}
