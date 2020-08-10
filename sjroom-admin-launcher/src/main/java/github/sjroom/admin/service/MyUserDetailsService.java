package github.sjroom.admin.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import github.sjroom.admin.bean.ContextDTO;
import github.sjroom.admin.bean.bo.MenuBo;
import github.sjroom.admin.bean.bo.RoleMenuBo;
import github.sjroom.admin.bean.bo.UserBo;
import github.sjroom.admin.bean.bo.UserRoleBo;
import github.sjroom.admin.bean.entity.User;
import github.sjroom.core.context.call.BusinessContext;
import github.sjroom.core.context.call.BusinessContextHolders;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.core.utils.StringUtil;
import github.sjroom.secrity.bean.JwtUser;
import github.sjroom.secrity.code.ISecrityErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    private IUserRoleService iUserRoleService;
    @Autowired
    private IRoleMenuService iRoleMenuService;
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IMenuService iMenuService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().eq(User::getUserName, username);
        User user = iUserService.getOne(queryWrapper);
        Assert.throwOnFalse(ObjectUtil.isNotEmpty(user), ISecrityErrorCode.TOKEN_USER_NAME_DB_NULL);

        // 根据userid，获取对应的角色ID集合
        List<UserRoleBo> userRoles = iUserRoleService.findByUserIds(Collections.singleton(user.getUserId()));
        Set<Long> roleIds = userRoles.stream().map(UserRoleBo::getRoleId).collect(Collectors.toSet());

        Set<String> perms = new HashSet<>(); //菜单按钮对应的权限列表
        if (CollectionUtil.isNotEmpty(roleIds)) {
            // 查询角色对应的菜单ID集合
            List<RoleMenuBo> roleMenuBos = iRoleMenuService.findByRoleIds(roleIds);
            Set<Long> menuIds = roleMenuBos.stream().map(RoleMenuBo::getMenuId).collect(Collectors.toSet());
            // 查询菜单数据
            List<MenuBo> menuBos = iMenuService.findByParentIds(menuIds);
            // 添加菜单按钮对应的权限
            menuBos.forEach(menu -> {
                if (StringUtil.isNotBlank(menu.getPerms())) {
                    Arrays.stream(menu.getPerms().split(",")).forEach(perm -> {
                        perms.add(perm);
                    });
                }
            });
        }

        // 该用户对应的所有菜单按钮权限添加
        List<GrantedAuthority> authorities = new ArrayList<>();
        perms.forEach(perm -> {
            authorities.add(new SimpleGrantedAuthority(perm));
        });

        // 拼装对应的权限用户
        JwtUser jwtUser = new JwtUser(user.getUserId(), user.getUserName(), user.getPassword(), authorities);
        log.info("loadContextData: {}  passwordEncoder:{}", JSON.toJSONString(jwtUser), passwordEncoder.encode(jwtUser.getPassword()));
        ContextDTO contextDTO = ContextDTO.builder()
                .userBo(BeanUtil.copy(user, UserBo.class))
                .jwtUser(jwtUser)
                .roles(iRoleService.getBatchBIds(roleIds))
                .build();

        // 将用户信息，角色信息存入当前上下文，以便于后期使用
        BusinessContext<ContextDTO> businessContext = new BusinessContext<ContextDTO>();
        businessContext.setUserId(user.getUserId());
        businessContext.setRoleIds(roleIds);
        businessContext.setUserName(user.getUserName());
        businessContext.setObj(contextDTO);
        BusinessContextHolders.setContext(businessContext);
        return jwtUser;
    }
}
