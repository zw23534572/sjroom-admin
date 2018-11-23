package com.github.sjroom.dao;


import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.domain.response.SysMenuResponse;
import com.github.sjroom.domain.entity.SysMenu;
import com.github.sjroom.common.jdbc.core.BaseMapper;

import java.util.List;

public interface SysMenuDao extends BaseMapper<SysMenu> {

	/**
	 * 查询结果
	 *
	 * @param pageRequest
	 * @return
	 */
	List<SysMenuResponse> selectPage(PageRequest pageRequest);

	/**
	 * 根据父菜单，查询子菜单
	 *
	 * @param parentId 父菜单ID
	 */
	List<SysMenuResponse> queryListParentId(Long parentId);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuResponse> queryNotButtonList();

	/**
	 * 查询用户的权限列表
	 */
	List<SysMenuResponse> queryUserList(Long userId);



}
