package com.github.sjroom.common;

/**
 * 通用结果接口
 * @author luobinwen
 *
 */
public interface IResult {
    /**
     * 获取枚举中定义的异常码
     * @return
     */
    int getCode();

    /**
     * 获取枚举中定义的异常信息
     * @return
     */
	String getMsg();
}