package com.github.sjroom.service;

import com.github.sjroom.domain.entity.SysUserToken;
import com.github.sjroom.domain.entity.SysUser;

import java.util.Set;

/**
 * shiro相关接口
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserToken queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     *
     * @param userId
     */
    SysUser queryUser(Long userId);

    void clearPermsList(long roleId);
}
