package github.sjroom.admin.code;

import github.sjroom.core.code.BaseErrorCode;

/**
 * 业务 code，需要配置 code 前缀，业务只需要关心code中间5位
 *
 * <p>
 * 业务标识：2位，开发人员按照业务模块定义，例如：(01)SKU。 业务错误码：3位，开发人员自己定义。
 * </p>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-05-27 08:34
 */
public interface SjroomErrorCode extends BaseErrorCode {

    String SYSTEM_ERROR_ADD_01 = "50001001"; //该数据已经添存在不能再次重复添加{0}
}
