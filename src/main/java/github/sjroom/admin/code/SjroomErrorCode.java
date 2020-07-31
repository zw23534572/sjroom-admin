package github.sjroom.admin.code;

import github.sjroom.core.code.BaseErrorCode;

/**
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-05-27 08:34
 */
public interface SjroomErrorCode extends BaseErrorCode {

    String SYSTEM_ERROR_ADD_01 = "50001001"; //该数据已经添存在不能再次重复添加{0}
}
