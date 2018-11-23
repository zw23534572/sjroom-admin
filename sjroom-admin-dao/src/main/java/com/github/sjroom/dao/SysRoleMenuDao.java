package com.github.sjroom.dao;


import com.github.sjroom.domain.entity.SysRoleMenu;
import com.github.sjroom.common.jdbc.core.BaseMapper;

import java.util.List;

public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {
    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Long> queryMenuIdList(Long roleId);

    /**
     * 根据菜单Id,查出对应的所有角色名称
     */
    List<String> queryRoleNameByMenuId(Long menuId);
}
