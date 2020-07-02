package github.sjroom.core.mybatis.mapper;

import github.sjroom.core.code.IErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统内部code
 *
 * @author manson.zhou
 */
@Getter
@AllArgsConstructor
public enum SystemCode implements IErrorCode {
    /**
     * 系统未知异常
     */
    FAILURE("SYS00101", "系统未知异常"),
    /**
     * 请求被拒绝
     */
    REQ_REJECT("SYS00102", "请求被拒绝"),
    /**
     * 404 没找到请求
     */
    NOT_FOUND("SYS00103", "404 没找到请求"),
    /**
     * 缺少必要的请求参数
     */
    PARAM_MISS("SYS00104", "缺少必要的请求参数:{0}"),
    /**
     * 请求参数类型错误
     */
    PARAM_TYPE_ERROR("SYS00105", "请求参数{0}类型错误"),
    /**
     * 请求参数绑定错误
     */
    PARAM_BIND_ERROR("SYS00106", "请求参数绑定错误{0}:{1}"),
    /**
     * 参数校验失败
     */
    PARAM_VALID_ERROR("SYS00107", "参数校验失败{0}:{1}"),
    /**
     * 消息不能读取
     */
    MSG_NOT_READABLE("SYS00108", "消息不能读取"),
    /**
     * 不支持当前请求方法
     */
    METHOD_NOT_SUPPORTED("SYS00109", "不支持当前请求方法:{0}"),
    /**
     * 不支持当前媒体类型
     */
    MEDIA_TYPE_NOT_SUPPORTED("SYS00110", "不支持当前媒体类型"),
    /**
     * 不接受的媒体类型
     */
    MEDIA_TYPE_NOT_ACCEPT("SYS00111", "不接受的媒体类型"),

    //=====================================================//
    /**
     * feign 降级处理
     */
    FEIGN_FALL_BACK("SYS00201", "Feign请求异常熔断降级"),
    ;

    /**
     * code编码
     */
    private String code;
    /**
     * 中文信息描述
     */
    private String msg;

}
