package com.github.sjroom.controller.sys;

import com.github.sjroom.base.BaseController;
import com.github.sjroom.common.Constant;
import com.github.sjroom.common.YNStatus;
import com.github.sjroom.common.util.AssertUtil;
import com.github.sjroom.common.util.TokenGeneratorUtil;
import com.github.sjroom.dao.SysUserDao;
import com.github.sjroom.domain.entity.SysUser;
import com.github.sjroom.service.SysUserTokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@RestController
public class SysLoginController extends BaseController {

    @Autowired
    SysUserDao sysUserMapper;
    @Autowired
    SysUserTokenService sysUserTokenService;

    @Value("${spring.profiles.active}")
    private String env;

    @RequestMapping(value = "/sys/env")
    public String getEnv() {
        return env;
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, String username, String password) throws IOException {

        logger.info("SysLoginController login username:{} password:{} ", username, password);

        AssertUtil.notBlank(username, "账号");

        //用户信息
        SysUser sysUser = new SysUser();
        sysUser.setAccount(username);
        sysUser = sysUserMapper.selectOne(sysUser);

        String inputMd5Password = TokenGeneratorUtil.generateValue(password);
        //账号不存在、密码错误
        if (sysUser == null || !sysUser.getPassword().equals(inputMd5Password)) {
            AssertUtil.throwBusinessException("账号或密码不正确");
        }

        //账号锁定
        if (sysUser.getYn().intValue() == YNStatus.NO.getCode()) {
            AssertUtil.throwBusinessException("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        String token = sysUserTokenService.createToken(sysUser.getId());
        if (request.getSession() != null) {
            request.getSession().setAttribute("token", token);
            logger.info("将登陆信息保存到缓存信息 key:{},token:{}", request.getSession().getId(), token);
        }
        return token;
    }

    /**
     * 退出
     */
    @RequestMapping(value = "/sys/logout", method = RequestMethod.POST)
    public int logout(HttpServletRequest request, HttpServletResponse response) {
        String token = (String) request.getSession().getAttribute("token");
        sysUserTokenService.logout(token);
        //注销当前登录的用户信息
        request.getSession().setAttribute(Constant.USER_INFO, null);
        return 1;
    }

}
