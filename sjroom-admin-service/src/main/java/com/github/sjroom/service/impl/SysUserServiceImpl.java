package com.github.sjroom.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.sjroom.common.AbstractService;
import com.github.sjroom.common.PageResultUtils;
import com.github.sjroom.common.YNStatus;
import com.github.sjroom.common.exception.BusinessException;
import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.common.response.PageResult;
import com.github.sjroom.common.util.AssertUtil;
import com.github.sjroom.common.util.TokenGeneratorUtil;
import com.github.sjroom.dao.SysUserDao;
import com.github.sjroom.dao.SysUserRoleDao;
import com.github.sjroom.domain.vo.SysUserVO;
import com.github.sjroom.domain.entity.SysUser;
import com.github.sjroom.domain.entity.SysUserRole;
import com.github.sjroom.service.SysRoleService;
import com.github.sjroom.service.SysUserRoleService;
import com.github.sjroom.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.util.StringUtils;

import static com.github.sjroom.common.Constant.DEFUALT_ROLE;

@Service
public class SysUserServiceImpl extends AbstractService implements SysUserService {
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public SysUser getSysUserByAccount(String account) {
        AssertUtil.notBlank(account, "账号");
        SysUser sysUser = new SysUser();
        sysUser.setAccount(account);
        return sysUserDao.selectOne(sysUser);
    }

    @Override
    public SysUser getSysUserById(Long id) {
        return sysUserDao.selectById(id);
    }

    @Override
    public PageResult selectPage(PageRequest pageRequest) {
        Page<SysUser> sysUserPage = PageHelper.startPage(pageRequest.getPageNo(), pageRequest.getPageSize());
        sysUserDao.selectPage(pageRequest);
        return PageResultUtils.convert(sysUserPage);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        SysUserRole querySysUserRole = new SysUserRole();
        querySysUserRole.setUserId(userId);
        querySysUserRole = sysUserRoleDao.selectOne(querySysUserRole);
        //如果该用户没有权限，设置为模板权限
        return sysUserDao.queryAllMenuIdByRoleId(querySysUserRole == null ? DEFUALT_ROLE : querySysUserRole.getRoleId());
    }

    @Override
    public int updatePassword(Long userId, String password, String newPassword) {
        SysUser sysUser = sysUserDao.selectById(userId);
        sysUser.setPassword(newPassword);
        return sysUserDao.updateById(sysUser);
    }

    @Override
    public void save(SysUserVO sysUserVO) {
        logger.info("SysUserServiceImpl save:{}", sysUserVO);

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserVO, sysUser);
        if (sysUser.getId() == null) {
            //sha256加密
            sysUser.setPassword(TokenGeneratorUtil.generateValue(sysUserVO.getPassword()));
            sysUser.setSalt("");
            sysUserDao.insert(sysUser);
        } else {
            if (!StringUtils.isEmpty(sysUserVO.getPassword())) {
                sysUser.setPassword(TokenGeneratorUtil.generateValue(sysUserVO.getPassword()));
            }
            sysUserDao.updateById(sysUser);
        }

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(sysUser.getId(), sysUserVO.getRoleIdList());
    }

    @Override
    public void deleteBatch(Long[] userId) {
        if (userId != null && userId.length > 0) {
            for (Long item : userId) {
                SysUser sysUser = new SysUser();
                sysUser.setId(item);
                sysUser.setStatus((byte) YNStatus.NO.getCode());
                sysUser.setYn((byte) YNStatus.NO.getCode());
                sysUserDao.updateById(sysUser);
            }
        }
    }
}
