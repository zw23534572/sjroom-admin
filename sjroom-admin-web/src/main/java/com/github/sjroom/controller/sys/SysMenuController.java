package com.github.sjroom.controller.sys;

import com.github.sjroom.base.BaseController;
import com.github.sjroom.common.Constant;
import com.github.sjroom.common.annotation.RequiresPermissions;
import com.github.sjroom.common.request.PageRequest;
import com.github.sjroom.common.response.PageResult;
import com.github.sjroom.common.response.R;
import com.github.sjroom.common.util.AssertUtil;
import com.github.sjroom.dao.SysMenuDao;
import com.github.sjroom.dao.SysRoleMenuDao;
import com.github.sjroom.domain.entity.SysMenu;
import com.github.sjroom.domain.request.SysMenuRequest;
import com.github.sjroom.domain.response.SysMenuResponse;
import com.github.sjroom.service.ShiroService;
import com.github.sjroom.service.SysMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private ShiroService shiroService;

    /**
     * 导航菜单
     */
    @RequestMapping("/nav")
    public R nav() {
        List<SysMenuResponse> menuList = sysMenuService.getUserMenuList(getUserId());
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        return R.ok().put("menuList", menuList).put("permissions", permissions);
    }

    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenuResponse> list() {
        logger.info("SysMenuController list start");
        PageResult pageResult = sysMenuService.selectPage(new PageRequest(1,10000));
        logger.info("SysMenuController list start : {}", pageResult.getData());
        return pageResult.getData();
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    public List<SysMenuResponse> select() {
        //查询列表数据
        List<SysMenuResponse> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenuResponse root = new SysMenuResponse();
        root.setId(0L);
        root.setName("导航栏");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        return menuList;
    }

    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    public SysMenuResponse info(@PathVariable("menuId") Long menuId) {
        return sysMenuService.getSysMenuVOById(menuId);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public int save(@RequestBody SysMenuRequest sysMenuRequest) {
        //数据校验
        verifyForm(sysMenuRequest);
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuRequest, sysMenu);
        sysMenu.setId(null);
        if (sysMenuRequest.getType() == Constant.MenuType.MENU.getValue()) {
            sysMenu.setIcon("/images/f06.png");
        }
        sysMenuService.insertOrUpdate(sysMenu);
        return 1;
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public int update(@RequestBody SysMenuRequest sysMenuRequest) {
        //数据校验
        verifyForm(sysMenuRequest);
        SysMenu sysMenu = new SysMenu();
        BeanUtils.copyProperties(sysMenuRequest, sysMenu);
        sysMenuService.insertOrUpdate(sysMenu);
        return 1;
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public int delete(long menuId) {

        List<String> roleNameList = sysRoleMenuDao.queryRoleNameByMenuId(menuId);
        if (roleNameList != null && roleNameList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (String roleName : roleNameList) {
                sb.append(roleName + " ");
            }
            AssertUtil.throwBusinessException("无法删除，角色[" + sb.toString() + "]在使用.");
        }

        //判断是否有子菜单或按钮
        List<SysMenuResponse> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            AssertUtil.throwBusinessException("请先删除子菜单或按钮");
        }
        sysMenuService.deleteById(menuId);
        return 1;
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuRequest sysMenuRequest) {
        AssertUtil.notBlank(sysMenuRequest.getName(), "菜单名称");
        AssertUtil.notNull(sysMenuRequest.getParentId(), "上级菜单");

        //菜单
        if (sysMenuRequest.getType() == Constant.MenuType.MENU.getValue()) {
            AssertUtil.notBlank(sysMenuRequest.getUrl(), "菜单URL");
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (sysMenuRequest.getParentId() != 0) {
            SysMenu parentMenu = sysMenuDao.selectById(sysMenuRequest.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (sysMenuRequest.getType() == Constant.MenuType.CATALOG.getValue() ||
                sysMenuRequest.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                AssertUtil.throwBusinessException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (sysMenuRequest.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                AssertUtil.throwBusinessException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
}
