package com.github.sjroom.common.jdbc.annotation;


import com.github.sjroom.common.jdbc.enums.FieldStrategyEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-11 00:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TableField {

    /**
     * <p>
     * 字段值（驼峰命名方式，该值可无）
     * </p>
     */
    String value() default "";
    /*
	 * <p>
	 * 是否为数据库表字段
	 * </p>
	 * <p>
	 * 默认 true 存在，false 不存在
	 * </p>
	 */
    boolean exist() default true;

    /*
     * <p>
     * 字段验证
     * </p>
     * <p>
     * 默认 非 null 判断
     * </p>
     */
    FieldStrategyEnum validate() default FieldStrategyEnum.NULL;


}
