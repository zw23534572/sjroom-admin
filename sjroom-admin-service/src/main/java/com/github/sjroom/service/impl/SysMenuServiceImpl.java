package com.github.sjroom.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.sjroom.common.AbstractService;
import com.github.sjroom.common.Constant;
import com.github.sjroom.common.PageResultUtils;
import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.common.response.PageResult;
import com.github.sjroom.dao.SysMenuDao;
import com.github.sjroom.dao.SysUserDao;
import com.github.sjroom.domain.entity.SysMenu;
import com.github.sjroom.domain.response.SysMenuResponse;
import com.github.sjroom.service.SysMenuService;
import com.github.sjroom.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


@Service
public class SysMenuServiceImpl extends AbstractService implements SysMenuService {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public SysMenuResponse getSysMenuVOById(Long id) {
        SysMenu menu = sysMenuDao.selectById(id);
        SysMenu menuParent = sysMenuDao.selectById(menu.getParentId());
        SysMenuResponse sysMenuVo = new SysMenuResponse();
        BeanUtils.copyProperties(menu, sysMenuVo);
        if (menuParent != null && !StringUtils.isEmpty(menuParent.getName())) {
            sysMenuVo.setParentName(menuParent.getName());
        }
        return sysMenuVo;
    }

    @Override
    public PageResult selectPage(PageRequest pageRequest) {
        logger.info("查询角色分页，入参：{}", pageRequest);
        Page<SysMenuResponse> sysRolePage = PageHelper.startPage(pageRequest.getPageNo(), pageRequest.getPageSize());
        sysMenuDao.selectPage(pageRequest);
        return PageResultUtils.convert(sysRolePage);
    }

    @Override
    public List<SysMenuResponse> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenuResponse> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenuResponse> userMenuList = new ArrayList<>();
        for (SysMenuResponse menu : menuList) {
            if (menuIdList.contains(menu.getId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuResponse> queryListParentId(Long parentId) {
        return sysMenuDao.queryListParentId(parentId);
    }


    @Override
    public List<SysMenuResponse> queryNotButtonList() {
        return sysMenuDao.queryNotButtonList();
    }

    @Override
    public List<SysMenuResponse> getUserMenuList(Long userId) {
        logger.info("SysMenuServiceImpl  getUserMenuList userId:" + userId);
        //用户菜单列表
        List<Long> menuIdList = sysUserDao.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public boolean insertOrUpdate(SysMenu sysMenu) {
        int result = 0;
        if (sysMenu.getId() == null) {
            result = sysMenuDao.insert(sysMenu);
        } else {
            result = sysMenuDao.updateById(sysMenu);
        }
        return true;
    }

    @Override
    public void deleteById(Long id) {
        sysMenuDao.deleteById(id);
    }


    /**
     * 获取所有菜单列表
     */
    private List<SysMenuResponse> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenuResponse> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuResponse> getMenuTreeList(List<SysMenuResponse> menuList, List<Long> menuIdList) {
        List<SysMenuResponse> subMenuList = new ArrayList<SysMenuResponse>();

        for (SysMenuResponse entity : menuList) {
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {//目录
                entity.setList(getMenuTreeList(queryListParentId(entity.getId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }
}
