package github.sjroom.admin.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Katrel.Zhou
 * @date 2019/5/26 14:25
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "sjroom.security")
public class WebSecurityProperties {

    /**
     * 不鉴权的请求
     */
    private List<String> noAuthorization;

    /**
     * 不鉴权的请求
     */
    private List<String> noAuthentication;

    private Path path;

    private boolean postOnly = true;

    private boolean forceOut = true;

    private boolean validateAble = true;

    @Setter
    @Getter
    public static class Path {

        private String loginByName;
        private String logout;
    }
}
