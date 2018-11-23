package com.github.sjroom.dao;

import com.github.sjroom.common.jdbc.core.BaseMapper;
import com.github.sjroom.domain.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleDao extends BaseMapper<SysUserRole> {

	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	/**
	 * 根据角色Id,查出对应的所有用户名称
	 * @param roleId
	 * @return
	 */
	List<String> queryUserNameByRoleId(Long roleId);
}
