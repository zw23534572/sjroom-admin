package com.github.sjroom.controller.sys;


import com.github.sjroom.base.BaseController;
import com.github.sjroom.common.annotation.RequiresPermissions;
import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.common.response.PageResult;
import com.github.sjroom.common.validator.ValidatorUtils;
import com.github.sjroom.dao.SysRoleDao;
import com.github.sjroom.domain.entity.SysRole;
import com.github.sjroom.service.SysRoleMenuService;
import com.github.sjroom.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public PageResult list(PageRequest pageRequest) {

        //查询列表数据
        PageResult list = sysRoleService.selectPage(pageRequest);
        return list;
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public List<SysRole> select() {
        return sysRoleDao.selectList(new SysRole());
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public SysRole info(@PathVariable("roleId") Long roleId) {
        SysRole role = sysRoleDao.selectById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);
        return role;
    }

    /**
     * 保存角色
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public int save(@RequestBody SysRole role) {
        ValidatorUtils.validateEntity(role);
        role.setCreateUser(getCreateUser());
        sysRoleService.save(role);
        return 1;
    }

    /**
     * 修改角色
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public int update(@RequestBody SysRole role) {
        ValidatorUtils.validateEntity(role);
        role.setCreateUser(getCreateUser());
        sysRoleService.update(role);
        return 1;
    }

    /**
     * 删除角色
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public int delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return 1;
    }
}
