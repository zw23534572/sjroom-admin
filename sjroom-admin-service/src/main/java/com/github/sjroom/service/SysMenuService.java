package com.github.sjroom.service;

import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.common.response.PageResult;
import com.github.sjroom.domain.entity.SysMenu;
import com.github.sjroom.domain.response.SysMenuResponse;
import com.github.sjroom.domain.vo.SysMenuVO;

import java.util.List;

/**
 * 菜单管理
 */
public interface SysMenuService  {

    SysMenuResponse getSysMenuVOById(Long id);

    PageResult selectPage(PageRequest pageRequest);

    List<SysMenuResponse> getUserMenuList(Long userId);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     */
    List<SysMenuResponse> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId 父菜单ID
     */
    List<SysMenuResponse> queryListParentId(Long parentId);

    List<SysMenuResponse> queryNotButtonList();

    /**
     * 新增或者修改
     * @param sysMenu
     * @return
     */
    boolean insertOrUpdate(SysMenu sysMenu);

    /**
     * 删除方法
     * @param id
     */
    void deleteById(Long id);

}
