package github.sjroom.admin.filter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @param e 异常信息
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);

//        if (e instanceof BusinessException) {
//            BusinessException dpe = (BusinessException) e;
//            Optional.ofNullable(dpe.getStatusCode()).ifPresent(response::setStatus);
//            IResultCode resultCode = dpe.getResultCode();
//            if (UtilObject.isNotNull(resultCode)) {
//                this.responseMessageResolver.failResolve(request, response, resultCode);
//                return;
//            }
//        }

        log.error("Unexpected error occurred during authorization,caused by ");
        log.error("==>", e);
//        this.responseMessageResolver
//            .failResolve(request, response, ApiCode.AUTHORIZATION_UNKNOWN_EXCEPTION);
    }
}
