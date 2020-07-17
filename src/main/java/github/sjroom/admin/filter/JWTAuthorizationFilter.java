package github.sjroom.admin.filter;

import com.alibaba.fastjson.JSON;
import github.sjroom.admin.code.ErrorCode;
import github.sjroom.admin.utils.JwtTokenUtil;
import github.sjroom.core.code.BaseErrorCode;
import github.sjroom.core.response.RespVo;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.response.ResponseMessageResolver;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            if (StringUtil.isBlank(token)) {
                chain.doFilter(request, response);
                return;
            }
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
        } catch (Exception ex) {
            log.error("JWTAuthenticationFilter unsuccessfulAuthentication ex:{}", ex);
            ResponseMessageResolver.failResolve(request, response, BaseErrorCode.UNAUTHORIZED_ERROR, ex.getMessage());
        }
    }
}
