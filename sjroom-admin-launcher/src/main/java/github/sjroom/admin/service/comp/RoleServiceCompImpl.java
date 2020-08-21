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
import github.sjroom.core.mybatis.page.PageUtil;
import github.sjroom.core.mybatis.util.IdUtil;
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

    @Override
    public RoleRespVo find(Long bid) {
        RoleBo roleBo = roleService.findByBId(bid);
        RoleRespVo respVo = BeanUtil.copy(roleBo, RoleRespVo.class);

        List<RoleMenuBo> roleMenuBos = iRoleMenuService.findByRoleIds(Collections.singleton(roleBo.getRoleId()));
        if (CollectionUtil.isNotEmpty(roleMenuBos)) {
            Set<Long> menuIds = roleMenuBos.stream().map(RoleMenuBo::getMenuId).collect(Collectors.toSet());
            respVo.setMenuIdList(menuIds);
        } else {
            respVo.setMenuIdList(new HashSet<>());
        }

        return respVo;
    }

    @Override
    public IPage<RoleRespVo> page(RolePageReqVo reqVo) {
        IPage<RoleBo> roleBoIPage = roleService.findPage(this.buildParams(reqVo));
        this.buildResult(roleBoIPage.getRecords());
        return PageUtil.toPage(roleBoIPage, RoleRespVo.class);
    }

    @Override
    public List<RoleRespVo> list(RoleReqVo reqVo) {
        List<RoleBo> roleBos = roleService.findList(BeanUtil.copy(reqVo, RoleBo.class));
        return BeanUtil.copy(roleBos, RoleRespVo.class);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Long create(RoleReqVo reqVo) {
        reqVo.setRoleId(IdUtil.getBId());
        RoleBo roleBo = this.validatedParams(reqVo);
        List<RoleMenu> roleMenus = new ArrayList<>();
        Role role = this.fetchEntityData(roleBo, roleMenus);
        roleService.save(role);
        if (CollectionUtil.isNotEmpty(roleMenus)) {
            iRoleMenuService.saveBatch(roleMenus);
        }
        return role.getRoleId();
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void update(RoleReqVo reqVo) {
        RoleBo roleBo = this.validatedParams(reqVo);
        List<RoleMenu> roleMenus = new ArrayList<>();
        Role role = this.fetchEntityData(roleBo, roleMenus);
        role.setUpdatedAt(new Date());
        roleService.updateByBId(role);
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<RoleMenu>()
                .eq(RoleMenu::getRoleId, role.getRoleId());
        iRoleMenuService.remove(wrapper);
        if (CollectionUtil.isNotEmpty(roleMenus)) {
            iRoleMenuService.saveBatch(roleMenus);
        }
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
    private Role fetchEntityData(RoleBo roleBo, List<RoleMenu> roleMenus) {
        roleBo.getMenuIdList().stream().forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleBo.getRoleId());
            roleMenus.add(roleMenu);
        });
        return BeanUtil.copy(roleBo, Role.class);
    }
}
