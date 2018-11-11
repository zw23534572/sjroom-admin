package com.github.sjroom.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.sjroom.common.AbstractService;
import com.github.sjroom.common.Constant;
import com.github.sjroom.common.PageResultUtils;
import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.common.response.PageResult;
import com.github.sjroom.common.util.AssertUtil;
import com.github.sjroom.dao.SysRoleDao;
import com.github.sjroom.dao.SysUserRoleDao;
import com.github.sjroom.domain.entity.SysRole;
import com.github.sjroom.domain.entity.SysUserRole;
import com.github.sjroom.service.SysRoleMenuService;
import com.github.sjroom.service.SysRoleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysRoleServiceImpl extends AbstractService implements SysRoleService {

    public static String ADMIN_ROLE_NAME_ARR[] = new String[]{"超级管理员", "管理员"};


    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public PageResult selectPage(PageRequest pageRequest) {
        logger.info("查询角色分页，入参：{}", pageRequest);
        Page<SysRole> sysRolePage = PageHelper.startPage(pageRequest.getPageNo(), pageRequest.getPageSize());
        sysRoleDao.selectPage(pageRequest);
        return PageResultUtils.convert(sysRolePage);
    }

    @Override
    public void save(SysRole role) {
        role.setCreateTime(new Date());

        if (role.getId() == null) {
            sysRoleDao.insert(role);
        } else {
            sysRoleDao.updateById(role);
        }


        //保存角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
    }

    @Override
    public void update(SysRole role) {
        logger.info("SysRoleServiceImpl role:{}", role);
        sysRoleDao.updateById(role);


        //更新角色与菜单关系
        sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
    }

    @Override
    public void deleteBatch(Long[] roleIds) {
        List<Long> idList = new ArrayList<>();
        for (Long roleId : roleIds) {
            idList.add(roleId);
        }

        List<String> userNameList = sysUserRoleDao.queryUserNameByRoleId(idList.get(0));
        if (userNameList != null && userNameList.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (String item : userNameList) {
                stringBuffer.append(item + " ");
            }
            AssertUtil.throwBusinessException("无法删除，用户[" + stringBuffer.toString() + "]在使用!");
        }
        sysRoleDao.deleteBatchByIds(idList);
    }

    @Override
    public List<Long> queryRoleIdList(String createUser) {
        return sysRoleDao.queryRoleIdList(createUser);
    }

    @Override
    public List<Long> queryRoleIdByUserId(Long userId) {
        SysUserRole querySysUserRole = new SysUserRole();
        querySysUserRole.setUserId(userId);

        List<SysUserRole> sysUserRoleList = sysUserRoleDao.selectList(querySysUserRole);

        List<Long> resultList = new ArrayList<>();
        if (sysUserRoleList != null && sysUserRoleList.size() > 0) {
            for (SysUserRole sysUserRole : sysUserRoleList) {
                resultList.add(sysUserRole.getRoleId());
            }
        } else {
            resultList.add(Constant.DEFUALT_ROLE);
        }
        return resultList;
    }

    @Override
    public boolean isAdminRole(Long userId) {

        boolean isAdminRole = false;
        List<SysRole> sysRoleList = sysRoleDao.queryListByUserId(userId);
        List<String> roleNameList = Arrays.asList(ADMIN_ROLE_NAME_ARR);
        if (sysRoleList != null && sysRoleList.size() > 0) {
            for (SysRole sysRole : sysRoleList) {
                if (roleNameList.contains(sysRole.getRoleName())) {
                    isAdminRole = true;
                    break;
                }
            }
        }
        return isAdminRole;
    }
}
