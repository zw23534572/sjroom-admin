package com.github.sjroom.base;

import com.github.sjroom.common.AbstractController;
import com.github.sjroom.common.Constant;
import com.github.sjroom.domain.entity.SysUser;

public abstract class BaseController extends AbstractController {


    protected SysUser getUser() {
        return (SysUser) getSessionAttribute(Constant.USER_INFO);
    }

    /**
     * 主键
     * @return
     */
    protected Long getUserId() {
        return getUser().getId();
    }

    /**
     * 用户账号
     * @return
     */
    protected String getCreateUser() {
        return getUser().getAccount();
    }
}
