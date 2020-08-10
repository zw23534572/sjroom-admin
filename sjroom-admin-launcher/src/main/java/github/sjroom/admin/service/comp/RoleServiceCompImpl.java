package github.sjroom.admin.service.comp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.RoleBo;
import github.sjroom.admin.bean.bo.RoleMenuBo;
import github.sjroom.admin.bean.entity.Role;
import github.sjroom.admin.bean.entity.RoleMenu;
import github.sjroom.admin.bean.vo.*;
import github.sjroom.admin.code.SjroomErrorCode;
import github.sjroom.admin.service.IMenuService;
import github.sjroom.admin.service.IRoleMenuService;
import github.sjroom.admin.service.IRoleService;
import github.sjroom.admin.service.IRoleServiceComp;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.exception.BusinessException;
import github.sjroom.core.mybatis.enums.StatusEnum;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.web.vo.IdListVo;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:11
 */
@Slf4j
@Service
@Validated
public class RoleServiceCompImpl implements IRoleServiceComp {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleMenuService iRoleMenuService;
    @Autowired
    private IMenuService iMenuService;

    @Override
    public RoleRespVo find(IdVo<Long> idVo) {
        RoleBo roleBo = roleService.findByBId(idVo.getId());
        List<RoleMenuBo> roleMenuBos = iRoleMenuService.findByRoleIds(Collections.singleton(roleBo.getRoleId()));
        if (CollectionUtil.isNotEmpty(roleMenuBos)) {
            Set<Long> menuIds = roleMenuBos.stream().map(RoleMenuBo::getMenuId).collect(Collectors.toSet());
            roleBo.setMenus(iMenuService.findByBIds(menuIds));
        }

        RoleRespVo respVo = BeanUtil.copy(roleBo, RoleRespVo.class);
        respVo.setMenus(BeanUtil.copy(roleBo.getMenus(), MenuReqVo.class));
        return respVo;
    }

    @Override
    public IPage page(RolePageReqVo reqVo) {
        IPage<RoleBo> roleBoIPage = roleService.findPage(this.buildParams(reqVo));
        this.buildResult(roleBoIPage.getRecords());
        return roleBoIPage;
    }

    @Override
    public List<RoleRespVo> list(RoleReqVo reqVo) {
        List<RoleBo> roleBos = roleService.findList(BeanUtil.copy(reqVo, RoleBo.class));
        return BeanUtil.copy(roleBos, RoleRespVo.class);
    }

    @Override
    public Long create(RoleReqVo reqVo) {
        RoleBo roleBo = this.validatedParams(reqVo);
        Role role = this.fetchEntityData(roleBo);
        roleService.save(role);
        return role.getRoleId();
    }

    @Override
    public void update(RoleReqVo reqVo) {
        RoleBo roleBo = this.validatedParams(reqVo);
        Role role = this.fetchEntityData(roleBo);
        role.setUpdatedAt(new Date());
        roleService.updateByBId(role);
    }

    @Override
    public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
        List<Role> roles = roleService.getBatchBIds(idStatusListVo.getIdList());
        if (CollectionUtil.isEmpty(roles)) {
            log.warn("RoleServiceCompImpl roleBos is empty");
            return;
        }
        roles.stream().forEach(role -> {
            role.setStatus(idStatusListVo.getStatus());
            role.setUpdatedAt(new Date());
        });
        roleService.updateBatchByBIds(roles);
        return;
    }

    @Override
    public void removeBatch(IdListVo<Long> idListVo) {
        if (CollectionUtil.isEmpty(idListVo.getIdList())) {
            log.warn("RoleServiceCompImpl removeBatch idListVo is empty");
            return;
        }

        List<RoleBo> roles = roleService.findByBIds(idListVo.getIdList());
        if (CollectionUtil.isNotEmpty(roles)) {
            roles = roles.stream().filter(x -> x.getStatus() == StatusEnum.UN_ENABLE).collect(Collectors.toList());
            Assert.throwOnFalse(roles.size() > 0, SjroomErrorCode.PARAM_ERROR, "必须有一个未启用状态，才能进行");
        }

        roleService.removeBatchBIds(idListVo.getIdList());
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void bindMenu(RoleMenuReqVo reqVo) {
        List<RoleMenu> roleMenus = new ArrayList<>();
        reqVo.getMenuIds().forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(reqVo.getRoleId());
            roleMenus.add(roleMenu);
        });
        LambdaQueryWrapper queryWrapper = new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, reqVo.getRoleId());
        iRoleMenuService.remove(queryWrapper);
        iRoleMenuService.saveBatch(roleMenus);
    }

    /**
     * 构建参数
     *
     * @param reqVo
     * @return
     */
    private RoleBo buildParams(RolePageReqVo reqVo) {
        RoleBo roleBo = BeanUtil.copy(reqVo, RoleBo.class);
        return roleBo;
    }

    /**
     * 构建返回参数
     *
     * @param roleBos
     */
    private void buildResult(List<RoleBo> roleBos) {
        if (CollectionUtil.isEmpty(roleBos)) {
            log.warn("RoleServiceCompImpl buildResult is empty");
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
    private RoleBo validatedParams(RoleReqVo reqVo) {
        RoleBo roleBo = BeanUtil.copy(reqVo, RoleBo.class);
        return roleBo;
    }

    /**
     * 获取实体数据
     *
     * @param roleBo
     * @return
     */
    private Role fetchEntityData(RoleBo roleBo) {
        Role role = BeanUtil.copy(roleBo, Role.class);
        return role;
    }
}
