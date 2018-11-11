package com.github.sjroom.service.impl;

import com.github.sjroom.common.AbstractService;
import com.github.sjroom.common.Constant;
import com.github.sjroom.dao.SysUserDao;
import com.github.sjroom.dao.SysUserTokenDao;
import com.github.sjroom.domain.entity.SysUser;
import com.github.sjroom.domain.entity.SysUserToken;
import com.github.sjroom.service.ShiroService;
import com.github.sjroom.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ShiroServiceImpl extends AbstractService implements ShiroService {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserDao SysUserDao;
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    @Override
    public Set<String> getUserPermissions(long userId) {

        List<String> resultPermsList = new ArrayList<>();
        List<Long> userRoleIdList = sysRoleService.queryRoleIdByUserId(userId);
        for(Long userRoleId : userRoleIdList ){
            List<String>  permsList = SysUserDao.queryAllPermsByRoleId(userRoleId);

            //将多个角色对应的权限添加
            resultPermsList.addAll(permsList);
        }

        /**
         * 多个角色对应的权限,去重d
         */
        Set<String> permsSet = new HashSet<>();
        for (String perms : resultPermsList) {
            if (StringUtils.isEmpty(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public void clearPermsList(long roleId) {
        /**
         * 缓存的key值
         */
        String redisKey = Constant.PROJECT_REDIS_ROLE_PERMS + String.valueOf(roleId);
    }

    @Override
    public SysUserToken queryByToken(String token) {
        SysUserToken sysUserToken = new SysUserToken();
        sysUserToken.setToken(token);
        return sysUserTokenDao.selectOne(sysUserToken);
    }

    @Override
    public SysUser queryUser(Long userId) {
        return SysUserDao.selectById(userId);
    }
}
