package com.github.sjroom.oauth2;

import com.github.sjroom.common.AbstractBase;
import com.github.sjroom.common.Constant;
import com.github.sjroom.common.util.AssertUtil;
import com.github.sjroom.domain.entity.SysUser;
import com.github.sjroom.domain.entity.SysUserToken;
import com.github.sjroom.domain.enums.YnEnum;
import com.github.sjroom.service.ShiroService;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Filter配置
 */
@Configuration
public class UrlInterceptor extends AbstractBase implements HandlerInterceptor {

    ShiroService shiroService;

    public UrlInterceptor() {

    }

    public UrlInterceptor(ShiroService shiroService) {
        this.shiroService = shiroService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute(Constant.USER_INFO) != null) {
            return true;
        }
        logger.info("登陆信息失效,查看其它请求情况");
        String accessToken = getRequestToken(request);

        logger.info("MyInterceptor preHandle accessToken = " + accessToken + " url = " + request.getRequestURI());

        //根据accessToken，查询用户信息
        SysUserToken tokenEntity = shiroService.queryByToken(accessToken);
        //token失效
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            AssertUtil.throwBusinessException("token失效，请重新登录");
        }

        //查询用户信息
        SysUser user = shiroService.queryUser(tokenEntity.getUserId());
        //账号锁定
        if (user.getYn() != YnEnum.YES.getCode()) {
            AssertUtil.throwBusinessException("账号已被锁定,请联系管理员");
        }
        request.getSession().setAttribute(Constant.USER_INFO, user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader("token");
        if (!StringUtils.isEmpty(token) && token.equals("undefined")) {
            token = null;
        }
        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = httpRequest.getParameter("token");
        }
        if (StringUtils.isEmpty(token)) {
            token = (String) httpRequest.getSession().getAttribute("token");
        }
        if (StringUtils.isEmpty(token)){
            AssertUtil.throwBusinessException("token失效，请重新登录");
        }
        return token;
    }
}
