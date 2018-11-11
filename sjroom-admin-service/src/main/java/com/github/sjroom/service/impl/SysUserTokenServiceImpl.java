package com.github.sjroom.service.impl;

import com.github.sjroom.common.AbstractService;
import com.github.sjroom.common.Constant;
import com.github.sjroom.common.util.HttpContextUtil;
import com.github.sjroom.common.util.TokenGeneratorUtil;
import com.github.sjroom.dao.SysUserDao;
import com.github.sjroom.dao.SysUserTokenDao;
import com.github.sjroom.domain.entity.SysUser;
import com.github.sjroom.domain.entity.SysUserToken;
import com.github.sjroom.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysUserTokenServiceImpl extends AbstractService implements SysUserTokenService {

    @Autowired
    private SysUserTokenDao sysUserTokenDao;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public String createToken(long userId) {
        logger.info("SysUserTokenServiceImpl createToken userId:{}", userId);
        //生成一个token
        String token = TokenGeneratorUtil.generateValue();
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + Constant.EXPIRE);

        String sessionId = HttpContextUtil.getHttpServletRequest().getSession().getId();
        //判断是否生成过token
        SysUserToken sysUserToken = new SysUserToken();
        sysUserToken.setUserId(userId);
        sysUserToken.setToken(token);
        sysUserToken.setUpdateTime(now);
        sysUserToken.setExpireTime(expireTime);
        sysUserToken.setSessionId(sessionId);
        sysUserTokenDao.insert(sysUserToken);

        //更新登陆时间
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setCreateTime(new Date());
        sysUserDao.updateById(sysUser);

        return token;
    }

    @Override
    public void logout(String token) {
        SysUserToken sysUserToken = new SysUserToken();
        sysUserToken.setToken(token);
        sysUserToken = sysUserTokenDao.selectOne(sysUserToken);
        if (sysUserToken != null) {
            sysUserTokenDao.deleteById(sysUserToken.getId());
        }
    }

}
