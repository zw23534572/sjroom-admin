package github.sjroom.admin.service;

import com.alibaba.fastjson.JSON;
import github.sjroom.admin.filter.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Manson
 * @date 2019-02-11
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JwtUser jwtUser = new JwtUser();
        jwtUser.setUsername("manson");
        jwtUser.setPassword(passwordEncoder.encode("manson"));
        log.info("登录成功！用户: {}", JSON.toJSONString(jwtUser));
        return jwtUser;
    }
}
