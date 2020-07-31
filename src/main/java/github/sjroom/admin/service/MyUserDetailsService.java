package github.sjroom.admin.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import github.sjroom.admin.bean.ContextDTO;
import github.sjroom.admin.bean.bo.UserBo;
import github.sjroom.admin.bean.entity.User;
import github.sjroom.core.context.call.BusinessContext;
import github.sjroom.core.context.call.BusinessContextHolders;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.secrity.bean.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        Wrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getUserName, username);
        user = iUserService.getOne(queryWrapper);
        if (ObjectUtil.isNotEmpty(user)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_SYS_DICT_SELECT"));
            authorities.add(new SimpleGrantedAuthority("ROLE_SYS_DICT_CREATE"));
            authorities.add(new SimpleGrantedAuthority("ROLE_SYS_DICT_UPDATE"));
            authorities.add(new SimpleGrantedAuthority("ROLE_SYS_DICT_REMOVE"));

            JwtUser jwtUser = new JwtUser(user.getUserId(), user.getUserName(), user.getPassword(), authorities);
            log.info("MyUserDetailsService: {}  passwordEncoder:{}", JSON.toJSONString(jwtUser), passwordEncoder.encode(jwtUser.getPassword()));
            ContextDTO contextDTO = ContextDTO.builder()
                    .userBo(BeanUtil.copy(user, UserBo.class))
                    .jwtUser(jwtUser)
                    .build();
            BusinessContext businessContext = new BusinessContext();
            businessContext.setUserId(user.getUserId());
            businessContext.setObj(contextDTO);
            BusinessContextHolders.setContext(businessContext);
            return jwtUser;
        }
        return null;
    }
}
