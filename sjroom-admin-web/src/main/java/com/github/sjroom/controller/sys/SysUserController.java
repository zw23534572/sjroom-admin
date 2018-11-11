package com.github.sjroom.controller.sys;

import com.github.sjroom.base.BaseController;
import com.github.sjroom.common.annotation.RequiresPermissions;
import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.common.response.PageResult;
import com.github.sjroom.common.util.AssertUtil;
import com.github.sjroom.common.util.TokenGeneratorUtil;
import com.github.sjroom.common.validator.ValidatorUtils;
import com.github.sjroom.common.validator.group.AddGroup;
import com.github.sjroom.common.validator.group.UpdateGroup;
import com.github.sjroom.dao.SysUserDao;
import com.github.sjroom.domain.entity.SysUser;
import com.github.sjroom.domain.response.SysUserResponse;
import com.github.sjroom.domain.vo.SysUserVO;
import com.github.sjroom.service.SysRoleService;
import com.github.sjroom.service.SysUserRoleService;
import com.github.sjroom.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    SysUserRoleService sysUserRoleService;
    @Autowired
    SysRoleService sysRoleService;

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public PageResult list(PageRequest pageRequest) {
        logger.info("SysUserController list pageRequest:{}", pageRequest);

        //只有超级管理员，才能查看所有管理员列表
        if (!sysRoleService.isAdminRole(getUserId())) {
            pageRequest.setCreateUser(getCreateUser());
        }

        return sysUserService.selectPage(pageRequest);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public HashMap info() {
        HashMap hashMap = new HashMap<>();
        hashMap.put("user", getUser());
        hashMap.put("currentDate", new Date());
        return hashMap;
    }

    /**
     * 修改登录用户密码
     */
    @RequestMapping("/password")
    public int password(String password, String newPassword) {
        AssertUtil.notBlank(password, "原始密码");
        AssertUtil.notBlank(newPassword, "新密码");

        //sha256加密
        password = TokenGeneratorUtil.generateValue(password);
        //sha256加密
        newPassword = TokenGeneratorUtil.generateValue(newPassword);

        SysUser sysUser = getUser();
        if (!sysUser.getPassword().equals(password)){
            AssertUtil.throwBusinessException("原密码不正确");
        }

        //更新密码
        int count = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (count == 0) {
            AssertUtil.throwBusinessException("更新操作,错误.");
        }
        return 1;
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public SysUserResponse info(@PathVariable("userId") Long userId) {
        SysUser user = sysUserDao.selectById(userId);

        SysUserResponse sysUserResponse = new SysUserResponse();
        BeanUtils.copyProperties(user, sysUserResponse);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        sysUserResponse.setRoleIdList(roleIdList);
        return sysUserResponse;
    }

    /**
     * 保存用户
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public int save(@RequestBody SysUserVO sysUserVO) {
        ValidatorUtils.validateEntity(sysUserVO, AddGroup.class);
        sysUserVO.setCreateUser(getCreateUser());

        SysUser querySysUser = new SysUser();
        querySysUser.setAccount(sysUserVO.getAccount());
        querySysUser = sysUserDao.selectOne(querySysUser);
        if (querySysUser != null) {
            AssertUtil.throwBusinessException("该用户账号已经存在.");
        }

        String email = sysUserVO.getEmail();
        if (!StringUtils.isEmpty(email)) {
            querySysUser = new SysUser();
            querySysUser.setEmail(email);
            querySysUser = sysUserDao.selectOne(querySysUser);
            if (querySysUser != null) {
                AssertUtil.throwBusinessException("该邮箱已存在.");
            }
        }

        sysUserService.save(sysUserVO);
        return 1;
    }

    /**
     * 修改用户
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public int update(@RequestBody SysUserVO sysUserVO) {
        ValidatorUtils.validateEntity(sysUserVO, UpdateGroup.class);
        sysUserVO.setCreateUser(getCreateUser());
        sysUserService.save(sysUserVO);
        return 1;
    }

    /**
     * 删除用户
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public int delete(@RequestBody Long[] userIds) {
        sysUserService.deleteBatch(userIds);
        return 1;
    }
}
