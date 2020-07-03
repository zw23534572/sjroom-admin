package github.sjroom.admin.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.sjroom.admin.code.ErrorCode;
import github.sjroom.admin.utils.JwtTokenUtil;
import github.sjroom.core.RespVo;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * 验证成功当然就是进行鉴权了
 * 登录成功之后走此类进行鉴权操作
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        try {
            String token = request.getHeader(JwtTokenUtil.HEADER_TOKEN);
            Assert.throwOnFalse(ObjectUtil.isNotNull(token), ErrorCode.TOKEN_NOT_NULL);
            Assert.throwOnFalse(!JwtTokenUtil.isTokenExpired(token), ErrorCode.TOKEN_EXPIRED);

            String username = JwtTokenUtil.getUsernameFromToken(token);
            Assert.throwOnFalse(StringUtil.isNotBlank(username), ErrorCode.TOKEN_NOT_NULL_NAME);

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (Exception e) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            RespVo respVo = RespVo.ok(ErrorCode.UNAUTHORIZED_ERROR, e.getMessage());
            String result = JSON.toJSONString(respVo);
            log.error("doFilterInternal ex:{}", e);
            response.getWriter().write(result);
        }
    }
}
