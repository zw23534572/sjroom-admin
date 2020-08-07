package github.sjroom.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.RoleMenuBo;
import github.sjroom.admin.bean.entity.RoleMenu;
import github.sjroom.admin.dao.IRoleMenuDao;
import github.sjroom.admin.service.IRoleMenuService;
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
 * @date 2020-08-04 20:44
 */
@Slf4j
@Service
@Validated
public class RoleMenuServiceImpl extends BaseServiceImpl<IRoleMenuDao, RoleMenu> implements IRoleMenuService {

    @Override
    public RoleMenuBo findByBId(Long bid) {
        RoleMenu roleMenu = super.getByBId(bid);
        return BeanUtil.copy(roleMenu, RoleMenuBo.class);
    }

    @Override
    public List<RoleMenuBo> findByBIds(Set<Long> roleMenuIds) {
        List<RoleMenu> roleMenus = super.getBatchBIds(roleMenuIds);
        return BeanUtil.copy(roleMenus, RoleMenuBo.class);
    }

    @Override
    public List<RoleMenuBo> findByRoleIds(Set<Long> roleIds) {
        RoleMenuBo roleMenuBo = new RoleMenuBo();
        roleMenuBo.setRoleIds(roleIds);
        return this.findList(roleMenuBo);
    }

    @Override
    public List<RoleMenuBo> findByMenuIds(Set<Long> menuIds) {
        RoleMenuBo roleMenuBo = new RoleMenuBo();
        roleMenuBo.setMenuIds(menuIds);
        return this.findList(roleMenuBo);
    }

    @Override
    public List<RoleMenuBo> findList(RoleMenuBo roleMenuBo) {
        List<RoleMenu> roleMenus = super.list(this.query(roleMenuBo));
        return BeanUtil.copy(roleMenus, RoleMenuBo.class);
    }

    @Override
    public Map<Long, RoleMenuBo> findMap(RoleMenuBo roleMenuBo) {
        List<RoleMenuBo> roleMenuBos = this.findList(roleMenuBo);
        if (CollectionUtil.isEmpty(roleMenuBos)) {
            log.warn("RoleMenuServiceImpl find roleMenuBos is empty");
            return Collections.emptyMap();
        }
        return roleMenuBos.stream().collect(Collectors.toMap(RoleMenuBo::getRoleMenuId, Function.identity()));
    }

    @Override
    public IPage<RoleMenuBo> findPage(RoleMenuBo model) {
        IPage<RoleMenu> modelPage = super.page(PageUtil.toPage(model), this.query(model));
        return PageUtil.toPage(modelPage, RoleMenuBo.class);
    }

    private LambdaQueryWrapper<RoleMenu> query(RoleMenuBo model) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<RoleMenu>();
        wrapper.eq(ObjectUtil.isNotNull(model.getRoleMenuId()), RoleMenu::getRoleMenuId, model.getRoleMenuId());
        wrapper.eq(ObjectUtil.isNotNull(model.getRoleId()), RoleMenu::getRoleId, model.getRoleId());
        wrapper.in(CollectionUtil.isNotEmpty(model.getRoleIds()), RoleMenu::getRoleId, model.getRoleIds());
        wrapper.in(CollectionUtil.isNotEmpty(model.getMenuIds()), RoleMenu::getMenuId, model.getMenuIds());
        wrapper.eq(ObjectUtil.isNotNull(model.getMenuId()), RoleMenu::getMenuId, model.getMenuId());
        wrapper.eq(ObjectUtil.isNotNull(model.getStatus()), RoleMenu::getStatus, model.getStatus());
        return wrapper;
    }

}
