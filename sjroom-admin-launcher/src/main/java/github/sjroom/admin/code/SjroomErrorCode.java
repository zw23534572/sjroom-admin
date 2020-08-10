package github.sjroom.admin.code;

import github.sjroom.core.code.BaseErrorCode;

/**
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-05-27 08:34
 */
public interface SjroomErrorCode extends BaseErrorCode {

    String MENU_DELETE_ERROR_001 = "004004001"; //只有未启用的数据，才能进行删除
    String MENU_DELETE_ERROR_002 = "004004002"; //无法删除，角色{0}在使用.
    String MENU_DELETE_ERROR_003 = "004004003"; //请先删除{0}子菜单或按钮
}
