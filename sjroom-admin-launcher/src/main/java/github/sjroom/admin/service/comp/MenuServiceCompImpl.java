package github.sjroom.admin.service.comp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import github.sjroom.admin.bean.bo.RoleBo;
import github.sjroom.admin.bean.bo.RoleMenuBo;
import github.sjroom.admin.bean.entity.RoleMenu;
import github.sjroom.admin.bean.vo.*;
import github.sjroom.admin.code.Constant;
import github.sjroom.admin.code.MenuTypeEnum;
import github.sjroom.admin.code.SjroomErrorCode;
import github.sjroom.admin.code.YesOrNoEnum;
import github.sjroom.admin.service.IRoleMenuService;
import github.sjroom.admin.service.IRoleService;
import github.sjroom.admin.util.TreeUtils;
import github.sjroom.core.mybatis.enums.StatusEnum;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.utils.*;
import github.sjroom.admin.bean.bo.MenuBo;
import github.sjroom.admin.bean.entity.Menu;
import github.sjroom.admin.service.IMenuService;
import github.sjroom.admin.service.IMenuServiceComp;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import github.sjroom.web.vo.IdListVo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 13:41
 */
@Slf4j
@Service
@Validated
public class MenuServiceCompImpl implements IMenuServiceComp {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IRoleMenuService iRoleMenuService;
    @Autowired
    private IRoleService iRoleService;

    @Override
    public List<MenuTreeRespVo> nav(Set<Integer> types) {
        MenuBo menuBo = new MenuBo();
        menuBo.setStatus(YesOrNoEnum.YES.getValue());
        menuBo.setTypes(types);
        List<MenuBo> menuBos = menuService.findList(menuBo);
        List<MenuTreeRespVo> menuTreeRespVos = BeanUtil.copy(menuBos, MenuTreeRespVo.class);
        return TreeUtils.getTreeList(Constant.PARENT_ID, menuTreeRespVos);
    }

    @Override
    public MenuRespVo find(Long bid) {
        return BeanUtil.copy(menuService.findByBId(bid), MenuRespVo.class);
    }

    @Override
    public IPage page(MenuPageReqVo reqVo) {
        IPage<MenuBo> menuBoIPage = menuService.findPage(this.buildParams(reqVo));
        this.buildResult(menuBoIPage.getRecords());
        return menuBoIPage;
    }

    @Override
    public List<MenuRespVo> list(MenuListReqVo reqVo) {
        List<MenuBo> menuBos = menuService.findList(BeanUtil.copy(reqVo, MenuBo.class));
        return BeanUtil.copy(menuBos, MenuRespVo.class);
    }

    @Override
    public Long create(MenuReqVo reqVo) {
        MenuBo menuBo = this.validatedParams(reqVo);
        Menu menu = this.fetchEntityData(menuBo);
        menuService.save(menu);
        return menu.getMenuId();
    }

    @Override
    public void update(MenuReqVo reqVo) {
        Assert.throwOnFalse(ObjectUtil.isNotEmpty(reqVo.getMenuId()), SjroomErrorCode.PARAM_ERROR_01, "menuId");
        MenuBo menuBo = this.validatedParams(reqVo);
        Menu menu = this.fetchEntityData(menuBo);
        menu.setUpdatedAt(new Date());
        menuService.updateByBId(menu);
    }

    @Override
    public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
        List<Menu> menus = menuService.getBatchBIds(idStatusListVo.getIdList());
        if (CollectionUtil.isEmpty(menus)) {
            log.warn("MenuServiceCompImpl menuBos is empty");
            return;
        }
        menus.stream().forEach(menu -> {
            menu.setStatus(idStatusListVo.getStatus());
            menu.setUpdatedAt(new Date());
        });
        menuService.updateBatchByBIds(menus);
        return;
    }

    @Override
    public void removeBatch(IdListVo<Long> idListVo) {
        if (CollectionUtil.isEmpty(idListVo.getIdList())) {
            log.warn("MenuServiceCompImpl removeBatch idListVo is empty");
            return;
        }

        List<MenuBo> menuBos = menuService.findByBIds(idListVo.getIdList());
        menuBos = menuBos.stream()
                .filter(x -> x.getStatus() == StatusEnum.UN_ENABLE)
                .collect(Collectors.toList());
        //只有未启用的数据，才能进行删除
        Assert.throwOnFalse(CollectionUtil.isNotEmpty(menuBos), SjroomErrorCode.MENU_DELETE_ERROR_001);

        List<RoleMenuBo> roleMenus = iRoleMenuService.findByMenuIds(idListVo.getIdList());
        if (CollectionUtil.isNotEmpty(roleMenus)) {
            Set<Long> roleIds = roleMenus.stream().map(RoleMenuBo::getRoleId).collect(Collectors.toSet());
            List<RoleBo> roleBos = iRoleService.findByBIds(roleIds);
            Set<String> roleNames = roleBos.stream().map(RoleBo::getRoleName).collect(Collectors.toSet());
            //无法删除，角色{0}在使用.
            Assert.throwFail(SjroomErrorCode.MENU_DELETE_ERROR_002, roleNames);
        }

        //判断是否有子菜单或按钮
        List<MenuBo> menuChildBos = menuService.findByParentIds(idListVo.getIdList());
        if (CollectionUtil.isNotEmpty(menuChildBos)) {
            Set<Long> menuParentIds = menuChildBos.stream().map(MenuBo::getParentId).collect(Collectors.toSet());
            menuBos = menuBos.stream()
                    .filter(x -> menuParentIds.contains(x.getMenuId()))
                    .collect(Collectors.toList());
            Assert.throwOnFalse(CollectionUtil.isNotEmpty(menuBos),
                    SjroomErrorCode.MENU_DELETE_ERROR_003, //请先删除{0}子菜单或按钮
                    menuBos.stream().map(MenuBo::getMenuName).collect(Collectors.toSet()));
        }

        // 删除删除
        menuService.removeBatchBIds(idListVo.getIdList());
    }

    /**
     * 构建参数
     *
     * @param reqVo
     * @return
     */
    private MenuBo buildParams(MenuPageReqVo reqVo) {
        MenuBo menuBo = BeanUtil.copy(reqVo, MenuBo.class);
        return menuBo;
    }

    /**
     * 构建返回参数
     *
     * @param menuBos
     */
    private void buildResult(List<MenuBo> menuBos) {
        if (CollectionUtil.isEmpty(menuBos)) {
            log.warn("MenuServiceCompImpl buildResult is empty");
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
    private MenuBo validatedParams(MenuReqVo reqVo) {
        MenuBo menuBo = BeanUtil.copy(reqVo, MenuBo.class);

        //菜单URL
        if (reqVo.getType() == MenuTypeEnum.MENU.getValue()) {
            Assert.throwOnFalse(StringUtil.isNotBlank(reqVo.getUrl()), SjroomErrorCode.PARAM_ERROR, "菜单URL");
        }

        //上级菜单类型
        int parentType = MenuTypeEnum.CATALOG.getValue();
        if (reqVo.getParentId() != 0) {
            MenuBo parentMenuBo = menuService.findByBId(reqVo.getParentId());
            parentType = parentMenuBo.getType();
        }

        //目录、菜单
        if (reqVo.getType() == MenuTypeEnum.CATALOG.getValue() ||
                reqVo.getType() == MenuTypeEnum.MENU.getValue()) {
            Assert.throwOnFalse(parentType == MenuTypeEnum.CATALOG.getValue(),
                    SjroomErrorCode.SYSTEM_ERROR, "上级菜单只能为目录类型");
        }

        //按钮
        if (reqVo.getType() == MenuTypeEnum.BUTTON.getValue()) {
            Assert.throwOnFalse(parentType == MenuTypeEnum.MENU.getValue(),
                    SjroomErrorCode.SYSTEM_ERROR, "上级菜单只能为菜单类型");
        }
        return menuBo;
    }

    /**
     * 获取实体数据
     *
     * @param menuBo
     * @return
     */
    private Menu fetchEntityData(MenuBo menuBo) {
        Menu menu = BeanUtil.copy(menuBo, Menu.class);

        //获取排序号
        if (ObjectUtil.isEmpty(menu.getOrderNum())) {
            List<MenuBo> parentMenuMenuBos = menuService.findByParentIds(Collections.singleton(menu.getParentId()));
            //获取当前父级下，最大的orderNum ，并且+1
            Integer maxOrderNum = parentMenuMenuBos
                    .stream()
                    .filter(i -> !i.getMenuId().equals(menu.getMenuId()))
                    .mapToInt(i -> i.getOrderNum()).max().getAsInt() + 1;
            menu.setOrderNum(maxOrderNum + 1);
        }

        return menu;
    }
}
