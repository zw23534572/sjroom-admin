package com.github.sjroom.common.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author manson
 * @since 1.0.0
 */
public class ReflectUtil {

    /**
     * 将source对象中的属性值复制到target对象中
     * NOTE:如果source拥有属性A,但target没有属性A,则会被忽略
     *
     * @param source
     * @param target
     */
    static void copyFieldValues(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();
        Field[] fields = getAllFields(targetClass);
        for (int i = 0; i < fields.length; i++) {
            Field targetField = fields[i];
            if (targetField.isSynthetic()) {
                continue;
            }
            String fieldName = targetField.getName();
            try {
                Object sourceValue = getFieldValueWithGetterMethod(source, sourceClass, fieldName);
                setFieldValueWithSetterMethod(target, sourceValue, targetClass, targetField);
            } catch (Exception e) {
                //ignored
            }
        }
    }

    /**
     * 获取clazz的所有属性,包括继承获得的属性
     *
     * @param clazz
     * @return
     */
    public static Field[] getAllFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Class<?> superClass = clazz.getSuperclass();
        while (superClass != null && superClass != Object.class) {
            Field[] copy = fields;
            Field[] superClassFields = superClass.getDeclaredFields();
            Field[] newFields = new Field[copy.length + superClassFields.length];
            System.arraycopy(copy, 0, newFields, 0, copy.length);
            System.arraycopy(superClassFields, 0, newFields, copy.length, superClassFields.length);
            fields = newFields;
            superClass = superClass.getSuperclass();
        }
        return fields;
    }

    /**
     * 获取getter方法
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    static Method getGetterMethod(Class<?> clazz, String fieldName) {
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            return clazz.getDeclaredMethod(methodName, new Class<?>[]{});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取getter方法
     *
     * @param clazz
     * @param field
     * @return
     */
    static Method getGetterMethod(Class<?> clazz, Field field) {
        String fieldName = field.getName();
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            return getInheritMethod(clazz, methodName, new Class<?>[]{});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取getter方法
     *
     * @param clazz
     * @param field
     * @return
     */
    static Method getSetterMethod(Class<?> clazz, Field field) {
        String fieldName = field.getName();
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            return getInheritMethod(clazz, methodName, new Class<?>[]{field.getType()});
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取继承的方法
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     * @throws NoSuchMethodException
     */
    static Method getInheritMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null && superClass != Object.class) {
                return getInheritMethod(superClass, methodName, parameterTypes);
            } else {
                throw e;
            }
        }
    }

    /**
     * 使用getter方法获取属性值
     *
     * @param object
     * @param clazz
     * @param field
     * @return
     */
    public static Object getFieldValueWithGetterMethod(Object object, Class<?> clazz, Field field) {
        Method method = getGetterMethod(clazz, field);
        try {
            return method.invoke(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用getter方法获取属性值
     *
     * @param object
     * @param clazz
     * @param fieldName
     * @return
     */
    static Object getFieldValueWithGetterMethod(Object object, Class<?> clazz, String fieldName) {
        Method method = getGetterMethod(clazz, fieldName);
        try {
            return method.invoke(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用setter方法设置属性值
     *
     * @param target
     * @param value
     * @param clazz
     * @param field
     * @return
     */
    public static Object setFieldValueWithSetterMethod(Object target, Object value, Class<?> clazz, Field field) {
        Method method = getSetterMethod(clazz, field);
        try {
            return method.invoke(target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 利用反射获取指定对象的指定属性
     *
     * @param target    目标对象
     * @param fieldName 目标属性
     * @return 目标属性的值
     */
    public static Object getFieldValue(Object target, String fieldName) {
        Object result = null;
        Field field = getField(target, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                result = field.get(target);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 利用反射获取指定对象里面的指定属性
     *
     * @param target    目标对象
     * @param fieldName 目标属性
     * @return 目标字段
     */
    public static Field getField(Object target, String fieldName) {
        Field field = null;
        for (Class<?> clazz = target.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                //ignored
            }
        }
        return field;
    }

    /**
     * 利用反射设置指定对象的指定属性为指定的值
     *
     * @param target     目标对象
     * @param fieldName  目标属性
     * @param fieldValue 目标值
     */
    public static void setFieldValue(Object target, String fieldName, Object fieldValue) {
        Field field = getField(target, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                field.set(target, fieldValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

}
