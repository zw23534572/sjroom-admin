package github.sjroom.admin.service.comp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.ContextDTO;
import github.sjroom.admin.bean.bo.UserRoleBo;
import github.sjroom.admin.bean.entity.UserRole;
import github.sjroom.admin.code.SjroomErrorCode;
import github.sjroom.admin.service.IUserRoleService;
import github.sjroom.admin.service.MyUserDetailsService;
import github.sjroom.core.context.call.BusinessContextHolders;
import github.sjroom.core.exception.BusinessException;
import github.sjroom.core.mybatis.enums.StatusEnum;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.mybatis.page.PageUtil;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.admin.bean.bo.UserBo;
import github.sjroom.admin.bean.entity.User;
import github.sjroom.admin.bean.vo.UserPageReqVo;
import github.sjroom.admin.bean.vo.UserReqVo;
import github.sjroom.admin.bean.vo.UserRespVo;
import github.sjroom.admin.service.IUserService;
import github.sjroom.admin.service.IUserServiceComp;
import github.sjroom.core.utils.StringUtil;
import github.sjroom.secrity.utils.MD5Util;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import github.sjroom.web.vo.IdListVo;

import java.lang.invoke.LambdaConversionException;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:51
 */
@Slf4j
@Service
@Validated
public class UserServiceCompImpl implements IUserServiceComp {
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRoleService iUserRoleService;

    @Override
    public UserRespVo info() {

        ContextDTO contextDTO = (ContextDTO) BusinessContextHolders.getBusinessContext().getObj();
        // 获取所有权限
        UserRespVo userRespVo = BeanUtil.copy(contextDTO.getUserBo(), UserRespVo.class);
        userRespVo.setPermissions(contextDTO.getJwtUser().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));

        return userRespVo;
    }

    @Override
    public UserRespVo find(Long bid) {
        UserRespVo userRespVo = BeanUtil.copy(userService.findByBId(bid), UserRespVo.class);
        List<UserRoleBo> userRoles = iUserRoleService.findByUserIds(Collections.singleton(bid));
        userRespVo.setRoleIdList(userRoles.stream()
                .map(UserRoleBo::getRoleId).collect(Collectors.toList()));
        return userRespVo;
    }

    @Override
    public IPage page(UserPageReqVo reqVo) {
        IPage<UserBo> userBoIPage = userService.findPage(this.buildParams(reqVo));
        this.buildResult(userBoIPage.getRecords());

        IPage<UserRespVo> page = PageUtil.toPage(userBoIPage, UserRespVo.class);
        return page;
    }

    @Override
    public List<UserRespVo> list(UserReqVo reqVo) {
        List<UserBo> userBos = userService.findList(BeanUtil.copy(reqVo, UserBo.class));
        return BeanUtil.copy(userBos, UserRespVo.class);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Long create(UserReqVo reqVo) {
        UserBo userBo = this.validatedParams(reqVo);
        this.fetchEntityData(userBo);
        userService.save(userBo.getUser());
        iUserRoleService.saveBatch(userBo.getUserRoles());
        return userBo.getUser().getUserId();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void update(UserReqVo reqVo) {
        UserBo userBo = this.validatedParams(reqVo);
        this.fetchEntityData(userBo);

        userService.updateByBId(userBo.getUser());
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId,reqVo.getUserId());
        iUserRoleService.remove(lambdaQueryWrapper);
        iUserRoleService.saveBatch(userBo.getUserRoles());
    }

    @Override
    public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
        List<User> users = userService.getBatchBIds(idStatusListVo.getIdList());
        if (CollectionUtil.isEmpty(users)) {
            log.warn("UserServiceCompImpl userBos is empty");
            return;
        }
        users.stream().forEach(user -> {
            user.setStatus(idStatusListVo.getStatus());
            user.setUpdatedAt(new Date());
        });
        userService.updateBatchByBIds(users);
        return;
    }

    @Override
    public void removeBatch(IdListVo<Long> idListVo) {
        if (CollectionUtil.isEmpty(idListVo.getIdList())) {
            log.warn("UserServiceCompImpl removeBatch idListVo is empty");
            return;
        }

        List<UserBo> users = userService.findByBIds(idListVo.getIdList());
        if (CollectionUtil.isNotEmpty(users)) {
            users = users.stream().filter(x -> x.getStatus() == StatusEnum.UN_ENABLE).collect(Collectors.toList());
            Assert.throwOnFalse(users.size() > 0, SjroomErrorCode.PARAM_ERROR, "必须有一个未启用状态，才能进行");
        }

        userService.removeBatchBIds(idListVo.getIdList());
    }

    /**
     * 构建参数
     *
     * @param reqVo
     * @return
     */
    private UserBo buildParams(UserPageReqVo reqVo) {
        UserBo userBo = BeanUtil.copy(reqVo, UserBo.class);
        return userBo;
    }

    /**
     * 构建返回参数
     *
     * @param userBos
     */
    private void buildResult(List<UserBo> userBos) {
        if (CollectionUtil.isEmpty(userBos)) {
            log.warn("UserServiceCompImpl buildResult is empty");
            return;
        }
        // 实现业务逻辑
    }

    /**
     * 验证参数
     *
     * @param reqVo
     * @return
     */
    private UserBo validatedParams(UserReqVo reqVo) {
        UserBo userBo = BeanUtil.copy(reqVo, UserBo.class);
        return userBo;
    }

    /**
     * 获取实体数据
     *
     * @param userBo
     * @return
     */
    private void fetchEntityData(UserBo userBo) {
        User user = BeanUtil.copy(userBo, User.class);
        user.setUpdatedAt(new Date());
        if (StringUtil.isNotBlank(userBo.getPassword())){
            //密码修改，md5加密
            user.setPasswordPlaintext(userBo.getPassword());
            user.setPassword(MD5Util.md5(userBo.getPassword()));
        }
        userBo.setUser(user);

        //添加角色集合
        userBo.setUserRoles(new ArrayList<>());
        userBo.getRoleIdList().stream().forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(user.getUserId());
            userBo.getUserRoles().add(userRole);
        });
    }
}
