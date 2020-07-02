package github.sjroom.admin.config;

import github.sjroom.admin.filter.JWTAuthenticationEntryPoint;
import github.sjroom.admin.filter.JWTAuthenticationFilter;
import github.sjroom.admin.filter.JWTAuthorizationFilter;
import github.sjroom.core.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
@EnableWebSecurity
//添加annotation 支持,包括（prePostEnabled，securedEnabled...）
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String DEFAULT_LOGIN_PATH = "/login";
    private static final String DEFAULT_LOGOUT = "/logout";

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 忽略需要认证的路径
     */
    private List<String> noAuthentication = new ArrayList<>(16);

    @Autowired
    private WebSecurityProperties webSecurityProperties;

    @PostConstruct
    public void afterPropertiesSet() {
        List<String> noAuthentication = this.webSecurityProperties.getNoAuthentication();
        if (!CollectionUtil.isEmpty(noAuthentication)) {
            this.noAuthentication = noAuthentication;
        }

        log.info("Load WebSecurityProperties: [forceOut: {}, validateAble: {}].",
                webSecurityProperties.isForceOut(), webSecurityProperties.isValidateAble());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                //所有用户可以访问"/resources"目录下的资源以及访问"/home"和favicon.ico
                .authorizeRequests()
                .antMatchers(this.noAuthentication.toArray(new String[this.noAuthentication.size()])).permitAll()

                //前面没有匹配上的请求，全部需要认证；
                .anyRequest().authenticated()

                .and()
                //指定登录界面，并且设置为所有人都能访问；
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .exceptionHandling()
                .authenticationEntryPoint(new JWTAuthenticationEntryPoint())

//                .and()
//                .logout().permitAll()
//                .logoutRequestMatcher(new AntPathRequestMatcher(DEFAULT_LOGOUT,
//                        HttpMethod.POST.name()))
//                .logoutSuccessHandler(this.dopLogoutSuccessHandler);
        ;

        // 禁用缓存
        http.headers().cacheControl();
        // 由于使用的是JWT，我们这里不需要csrf
        http.csrf().disable();

        // 基于token，所以不需要session
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}