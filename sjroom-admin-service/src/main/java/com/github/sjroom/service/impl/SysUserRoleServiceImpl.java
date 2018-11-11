package com.github.sjroom.service.impl;

import com.github.sjroom.common.AbstractService;
import com.github.sjroom.dao.SysUserRoleDao;
import com.github.sjroom.service.SysUserRoleService;
import com.github.sjroom.domain.entity.SysUserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户与角色对应关系
 */
@Service
public class SysUserRoleServiceImpl extends AbstractService implements SysUserRoleService {
	@Autowired
	SysUserRoleDao sysUserRoleDao;

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		if(roleIdList.size() == 0){
			return ;
		}
		
		//先删除用户与角色关系
		delete(userId);
		
		//保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("roleIdList", roleIdList);

		for(Long roleId : roleIdList ){
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(userId);
			sysUserRole.setRoleId(roleId);
			sysUserRoleDao.insert(sysUserRole);
		}
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysUserRoleDao.queryRoleIdList(userId);
	}

	@Override
	public void delete(Long userId) {
		SysUserRole deleteSysUserRole = new SysUserRole();
		deleteSysUserRole.setUserId(userId);
		List<SysUserRole> sysUserRoleList = sysUserRoleDao.selectList(deleteSysUserRole);
		for (SysUserRole sysUserRole : sysUserRoleList) {
			sysUserRoleDao.deleteById(sysUserRole.getId());
		}

	}
}
