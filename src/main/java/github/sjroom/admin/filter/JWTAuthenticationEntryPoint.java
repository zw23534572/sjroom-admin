package github.sjroom.admin.filter;

import github.sjroom.core.code.BaseErrorCode;
import github.sjroom.core.response.ResponseMessageResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by echisan on 2018/6/24
 */
@Slf4j
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {


    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("JWTAuthenticationEntryPoint commence ex:{}", authException);
        ResponseMessageResolver.failResolve(request, response, BaseErrorCode.UNAUTHORIZED_ERROR, authException.getMessage());
    }
}
