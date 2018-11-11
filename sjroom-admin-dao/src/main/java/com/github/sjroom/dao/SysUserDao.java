package com.github.sjroom.dao;

import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.common.response.PageResult;
import com.github.sjroom.jdbc.core.BaseMapper;
import com.github.sjroom.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserDao extends BaseMapper<SysUser> {

	/**
	 * 查询list
	 * @param pageRequest
	 * @return
	 */
	List<SysUser> selectPage(PageRequest pageRequest);

	/**
	 * 查询用户的所有权限
	 * @param roleId  角色ID
	 */
	List<String> queryAllPermsByRoleId(@Param("roleId") Long roleId);


	/**
	 * 查询用户的所有权限,当用户没有设置权限
	 */
	List<String> queryAllPermsForDefault(Long roleId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	List<Long> queryAllMenuIdByRoleId(@Param("roleId") Long roleId);




}
