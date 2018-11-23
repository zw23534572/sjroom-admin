package com.github.sjroom.common.util;
import java.util.*;
import java.util.Map.Entry;

/**
 * 集合相关处理工具类
 *
 * @author zisong.wang
 */
public class ArrayUtil {

    public static final String KEY_SPLIT_STR = "=";
    public static final String ENTRY_SPLIT_STR = ",";

    /**
     * 从List中获取第一个元素
     *
     * @param list 集合
     * @param <T>  元素类型
     * @return
     */
    public static <T> T getFirst(List<T> list) {
        if (!ValidationUtil.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    /*******************************************Map 相关*****************************************/
    /**
     * 把一个字符串转成map，字符串格式：key->v;key->v2;key->3
     *
     * @param string
     * @return
     */
    public static Map<String, String> stringToMap(String string) {
        return stringToMap(string, KEY_SPLIT_STR, ENTRY_SPLIT_STR);
    }

    /**
     * 把一个字符串转成map
     * 字符串格式：key->v;key->v2;key->3
     *
     * @param string        字符串
     * @param keySplitStr   连接k,v的字符串
     * @param entrySplitStr 连接每个entry的字符串
     * @return
     */
    public static Map<String, String> stringToMap(String string, String keySplitStr, String entrySplitStr) {
        Map<String, String> map = new HashMap<>(16);
        if (StringsUtil.isNotBlank(string)) {
            String[] groups = string.split(entrySplitStr);
            for (String group : groups) {
                String[] entry = group.split(keySplitStr);
                if (entry != null && entry.length > 1) {
                    map.put(entry[0], entry[1]);
                }
            }
        }
        return map;
    }

    /**
     * 根据一组可变参数的数组对象生成一个Map，用法如下：
     * <p>
     * <pre>
     * 	Maps.map(key,value,key,value....);
     * </pre>
     *
     * @param keyValues 可变参数数，如果为单数，则最后一个被忽略,如果长度小于2,则返回Null
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> map(Object... keyValues) {
        if (keyValues != null && keyValues.length > 1) {
            Class<?> kClass = keyValues[0].getClass();
            Class<?> vClass = keyValues[1].getClass();
            return (Map<K, V>) map(kClass, vClass, keyValues);
        }
        return null;
    }

    /**
     * 根据一组可变参数的数组对象生成一个Map，并且有强制类型化，用法如下：
     * <p>
     * <pre>
     * Maps.map(key.class,value.class,key,value,key,value,key,value......);
     * </pre>
     *
     * @param kClass    key类型
     * @param vClass    value类型
     * @param keyValues 可变参数数，如果为单数，则最后一个被忽略,如果长度小于2,则返回Null
     * @return
     */
    public static <K, V> Map<K, V> map(Class<K> kClass, Class<V> vClass, Object... keyValues) {
        Map<K, V> m = new HashMap<>(16);
        int i = 1;
        Object preObj = null;
        for (Object o : keyValues) {
            if (i % 2 == 0) {
                K k = kClass.cast(preObj);
                V v = vClass.cast(o);
                m.put(k, v);
            }
            preObj = o;
            i++;
        }
        return m;
    }

    /**
     * 根据一组可变参数的数组对象生成一个Map，但不会对K，V使用泛型，用法如下：
     * <p>
     * <pre>
     * Maps.map(key,value,key,value,key,value......);
     * </pre>
     *
     * @param keyValues 可变参数数，如果为单数，则最后一个被忽略,如果长度小于2,则返回Null
     * @return
     */
    public static Map<Object, Object> mapByAarray(Object... keyValues) {
        Map<Object, Object> m = new HashMap<>(20);
        int i = 1;
        Object key = null;
        for (Object value : keyValues) {
            if (i % 2 == 0) {
                m.put(key, value);
            }
            key = value;
            i++;
        }
        return m;
    }

    /**
     * 以list方式读取map的值
     *
     * @param map map
     * @param <K> K类型
     * @param <V> V类型
     * @return
     */
    public static <K, V> List<V> mapToList(Map<K, V> map) {
        List<V> list = new ArrayList<>();
        if (!ValidationUtil.isEmpty(map)) {
            for (Entry<K, V> entry : map.entrySet()) {
                list.add(entry.getValue());
            }
            return list;
        }
        return list;
    }


    /**
     * 删除map中的空值
     *
     * @param map
     * @return
     */
    public static Map<String, Object> removeEmptyFromMap(Map<String, Object> map) {
        Map<String, Object> mapTemp = new HashMap<>(map.size());
        Iterator<?> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            @SuppressWarnings("rawtypes")
            Entry entry = (Entry) iter.next();
            String key = entry.getKey().toString();
            Object val = entry.getValue();
            if (!ValidationUtil.isEmpty(val)) {
                mapTemp.put(key, val);
            }
        }
        return mapTemp;
    }


}
