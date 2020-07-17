package github.sjroom.admin.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import github.sjroom.admin.bean.entity.User;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.secrity.bean.JwtUser;
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
    private IUserService iUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setUserName(username);
        Wrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserName, username);
        user = iUserService.getOne(queryWrapper);
        if (ObjectUtil.isNotEmpty(user)) {
            JwtUser jwtUser = new JwtUser(1l, user.getUserName(), user.getPassword(), "ROLE_USER");
            log.info("MyUserDetailsService: {}  passwordEncoder:{}", JSON.toJSONString(jwtUser), passwordEncoder.encode(jwtUser.getPassword()));
            return jwtUser;
        }
        return null;
    }
}
