package com.github.sjroom.service;

import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.common.response.PageResult;
import com.github.sjroom.domain.entity.SysRole;

import java.util.List;


/**
 * 角色
 */
public interface SysRoleService {


	PageResult selectPage(PageRequest pageRequest);

	void save(SysRole role);

	void update(SysRole role);

	void deleteBatch(Long[] roleIds);

	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(String createUser);

	List<Long> queryRoleIdByUserId(Long userId);

	/**
	 * 判断是否为管理员角色 true管理员，false不是管理员
	 * @return
	 */
	boolean isAdminRole(Long userId);
}
