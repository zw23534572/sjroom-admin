package com.github.sjroom.service;

import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.common.response.PageResult;
import com.github.sjroom.domain.vo.SysUserVO;
import com.github.sjroom.domain.entity.SysUser;

import java.util.List;


/**
 * 系统用户
 */
public interface SysUserService  {

    SysUser getSysUserByAccount(String account);

    SysUser getSysUserById(Long id);

    /**
     * 查询数据
     * @param pageRequest
     * @return
     */
    PageResult selectPage(PageRequest pageRequest);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    int updatePassword(Long userId, String password, String newPassword);

    void save(SysUserVO userVo);

    void deleteBatch(Long[] userId);

}
