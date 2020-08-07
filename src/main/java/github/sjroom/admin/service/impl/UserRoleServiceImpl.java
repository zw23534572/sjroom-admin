package github.sjroom.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.UserRoleBo;
import github.sjroom.admin.bean.entity.UserRole;
import github.sjroom.admin.dao.IUserRoleDao;
import github.sjroom.admin.service.IUserRoleService;
import github.sjroom.core.mybatis.page.PageUtil;
import github.sjroom.core.mybatis.service.impl.BaseServiceImpl;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * <B>说明：服务实现</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-03 14:22
 */
@Slf4j
@Service
@Validated
public class UserRoleServiceImpl extends BaseServiceImpl<IUserRoleDao, UserRole> implements IUserRoleService {

    @Override
    public UserRoleBo findByBId(Long bid) {
        UserRole userRole = super.getByBId(bid);
        return BeanUtil.copy(userRole, UserRoleBo.class);
    }

    @Override
    public List<UserRoleBo> findByBIds(Set<Long> userRoleIds) {
        List<UserRole> userRoles = super.getBatchBIds(userRoleIds);
        return BeanUtil.copy(userRoles, UserRoleBo.class);
    }

    @Override
    public List<UserRoleBo> findByUserIds(Set<Long> userIds) {
        Wrapper<UserRole> userRoleWrapper = new LambdaQueryWrapper<UserRole>()
                .in(UserRole::getUserId, userIds);
        return BeanUtil.copy(super.list(userRoleWrapper), UserRoleBo.class);
    }

    @Override
    public List<UserRoleBo> findList(UserRoleBo userRoleBo) {
        List<UserRole> userRoles = super.list(this.query(userRoleBo));
        return BeanUtil.copy(userRoles, UserRoleBo.class);
    }

    @Override
    public Map<Long, UserRoleBo> findMap(UserRoleBo userRoleBo) {
        List<UserRoleBo> userRoleBos = this.findList(userRoleBo);
        if (CollectionUtil.isEmpty(userRoleBos)) {
            log.warn("UserRoleServiceImpl find userRoleBos is empty");
            return Collections.emptyMap();
        }
        return userRoleBos.stream().collect(Collectors.toMap(UserRoleBo::getUserRoleId, Function.identity()));
    }

    @Override
    public IPage<UserRoleBo> findPage(UserRoleBo model) {
        IPage<UserRole> modelPage = super.page(PageUtil.toPage(model), this.query(model));
        return PageUtil.toPage(modelPage, UserRoleBo.class);
    }

    private LambdaQueryWrapper<UserRole> query(UserRoleBo model) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<UserRole>();
        wrapper.eq(ObjectUtil.isNotNull(model.getUserRoleId()), UserRole::getUserRoleId, model.getUserRoleId());
        wrapper.eq(ObjectUtil.isNotNull(model.getUserId()), UserRole::getUserId, model.getUserId());
        wrapper.eq(ObjectUtil.isNotNull(model.getRoleId()), UserRole::getRoleId, model.getRoleId());
        wrapper.eq(ObjectUtil.isNotNull(model.getStatus()), UserRole::getStatus, model.getStatus());
        return wrapper;
    }

}
