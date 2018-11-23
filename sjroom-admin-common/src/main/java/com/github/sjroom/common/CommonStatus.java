package com.github.sjroom.common;


import com.github.sjroom.common.response.CommonResponse;
import org.springframework.util.StringUtils;

/**
 * <pre>
 * 公共的业务异常(BusinessException)枚举类。各个模块定义自己的异常枚举类。
 * 用来统一平台的公共返回码，如：成功的返回码，参数异常的返回码....
 * </pre>
 * <p>
 * <b>异常码区段： 100-200</b>
 *
 * @author wzy 2017-08-11
 */
public enum CommonStatus  {

    /**
     * 参数异常
     */
    ILLEGAL_PARAM(101, "参数[{0}]不能为空"),
    /**
     * 序列化异常
     */
    SERIALIZE_ERROR(102, "序列化异常"),

    /**
     * token失效
     */
    TOKEN_ERROR(401, "token失效,请重新登录"),

    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 处理失败
     */
    FAIL(400, "处理失败！{0}"),
    /**
     * 系统错误
     */
    ERROR(500, "系统错误！{0}"),

    /**
     * 数据库异常
     */
    DB_ERROR(503,"数据库异常"),

    /**
     * mq系统错误
     */
    MQ_ERROR(505, "消息组件发生异常"),
    /**
     * mq系统错误
     */
    RETRY_UTIL_ERROR(506, "重试组件发生异常"),

    /**
     * 分布式锁错误
     */
    LOCKING_ERROR(510, "分布式锁组件发生异常"),

    /**
     * 监听器启动错误
     */
    LISTENER_START_ERROR(520, "检测 {0} 启动失败"),
    ;


    private int code;
    private String message;

    private CommonStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }

    public String getMessage(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            return message.replace("{0}", msg);
        } else {
            return message;
        }
    }

    public IResult joinSystemStatusCode() {
        return joinSystemStatusCode(this.message);
    }

    /**
     * 如果当前code 的前缀，包含系统码，则不添加.
     * update author ZhouWei
     *
     * @param message
     * @return
     */
    public IResult joinSystemStatusCode(String message) {
        String fullCodeStr = Integer.toString(ApplicationInfo.instance().getSystemCode());
        Integer fullCode = this.code;
        /**
         * 不包含系统码，才添加。
         */
        if (!fullCodeStr.startsWith(String.valueOf(this.code))) {
            fullCode = Integer.parseInt(fullCodeStr + this.code);
        }
        return new CommonResponse(fullCode, message);
    }

}
