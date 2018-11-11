package com.github.sjroom.dao;


import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.domain.entity.SysRole;
import com.github.sjroom.jdbc.core.BaseMapper;

import java.util.List;

public interface SysRoleDao extends BaseMapper<SysRole> {

	/**
	 * 查询结果
	 *
	 * @param pageRequest
	 * @return
	 */
	List<SysRole> selectPage(PageRequest pageRequest);

	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(String createUser);

	/**
	 * 根据userId获取所有的角色列表
	 * @param userId
	 * @return
	 */
	List<SysRole> queryListByUserId(Long userId);
}
