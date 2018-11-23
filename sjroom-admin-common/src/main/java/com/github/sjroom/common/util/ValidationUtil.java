package com.github.sjroom.common.util;


import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-03-23 10-18
 */
public class ValidationUtil {
    /**
     * 判断某个map是否为空
     *
     * @param m
     * @return
     */
    public static boolean isEmpty(Map m) {
        return m == null || m.isEmpty();
    }

    /**
     * 判断某个集合是否为空
     *
     * @param c
     * @return
     */
    public static boolean isEmpty(Collection c) {
        return CollectionUtils.isEmpty(c);
    }

    /**
     * 判断某个数组是否为空
     *
     * @param args
     * @return
     */
    public static boolean isEmpty(Object[] args) {
        return args == null || args.length == 0;
    }

    /**
     * 判断某个数组是否不为空
     *
     * @param args
     * @return
     */
    public static boolean isNotEmpty(Object[] args) {
        return !isEmpty(args);
    }

    /**
     * 判断某个集合是否不为空
     *
     * @param coll
     * @return
     */
    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param obj 待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return StringsUtil.isEmpty(obj.toString());
        } else if (obj instanceof Collection) {
            return isEmpty(((Collection) obj));
        } else if (obj instanceof Map) {
            return isEmpty((Map) obj);
        }
        return false;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素>0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Object pObj) {
        return !isEmpty(pObj);
    }
}
