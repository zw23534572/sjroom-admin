package com.github.sjroom.service.impl;

import com.github.sjroom.dao.SysRoleMenuDao;
import com.github.sjroom.service.SysRoleMenuService;

import java.util.List;

import com.github.sjroom.domain.entity.SysRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {

        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setRoleId(roleId);
        sysRoleMenu = sysRoleMenuDao.selectOne(sysRoleMenu);
        //先删除角色与菜单关系
        sysRoleMenuDao.deleteById(sysRoleMenu.getId());

        if (menuIdList.size() == 0) {
            return;
        }

        for (Long item : menuIdList) {
            SysRoleMenu insertSysRoleMenu = new SysRoleMenu();
            insertSysRoleMenu.setMenuId(item);
            insertSysRoleMenu.setRoleId(roleId);
            sysRoleMenuDao.insert(insertSysRoleMenu);
        }
    }

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
        return sysRoleMenuDao.queryMenuIdList(roleId);
    }
}
