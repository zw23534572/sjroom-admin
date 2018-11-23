package com.github.sjroom.common.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 数据库主键标识
 * </p>
 * 
 * @author Zhouwei
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableId {
    /**
     * <p>
     * 字段值（驼峰命名方式，该值可无）
     * </p>
     */
    String value() default "";

}
