package github.sjroom.admin.filter;

import github.sjroom.core.code.BaseErrorCode;
import github.sjroom.core.response.ResponseMessageResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <B>说明：鉴权失败处理流程</B><BR>
 *
 * @author zak.wu
 * @version 1.0.0
 * @date 2019/5/31 13:51
 */
@Slf4j
@AllArgsConstructor
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 鉴权失败处理流程
     *
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param e        异常信息
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        log.error("JWTAuthenticationEntryPoint commence ex:{}", e);
        ResponseMessageResolver.failResolve(request, response, BaseErrorCode.UNAUTHORIZED_ERROR, e.getMessage());
    }
}
