package github.sjroom.admin.controller.sys;

import com.google.common.collect.Sets;
import github.sjroom.admin.bean.vo.*;
import github.sjroom.admin.code.MenuTypeEnum;
import github.sjroom.admin.service.IMenuServiceComp;
import github.sjroom.core.mybatis.annotation.FillField;
import github.sjroom.core.mybatis.page.PageResult;
import github.sjroom.core.mybatis.page.PageUtil;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdListVo;
import github.sjroom.web.vo.IdVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <B>说明：菜单管理 控制器</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2020-07-24 13:41
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sys/menu")
@Api("菜单管理 控制器")
public class MenuController {
    @Autowired
    private IMenuServiceComp iMenuServiceComp;

    @ApiOperation(value = "导航栏-菜单目录", notes = "传入id")
    @PostMapping("nav")
    public List<MenuTreeRespVo> nav() {
        return iMenuServiceComp.nav(Sets.newHashSet(MenuTypeEnum.CATALOG.getValue(), MenuTypeEnum.MENU.getValue()));
    }

    @ApiOperation(value = "导航栏-目录", notes = "传入id")
    @PostMapping("nav/dir")
    public List<MenuTreeRespVo> navDir() {
        return iMenuServiceComp.nav(Sets.newHashSet(MenuTypeEnum.CATALOG.getValue()));
    }

    @ApiOperation(value = "查看", notes = "传入id")
    @PostMapping("find")
    @PreAuthorize("hasRole('ROLE_SYS_MENU_SELECT')")
    public MenuRespVo find(@Validated @RequestBody IdVo<Long> idVo) {
        return iMenuServiceComp.find(idVo);
    }

    @ApiOperation("分页")
    @PostMapping("page")
    @PreAuthorize("hasRole('ROLE_SYS_MENU_SELECT')")
    public PageResult page(@Validated @RequestBody MenuPageReqVo reqVo) {
        return PageUtil.toPageResult(iMenuServiceComp.page(reqVo), MenuRespVo.class);
    }

    @ApiOperation("列表")
    @PostMapping("list")
    @PreAuthorize("hasRole('ROLE_SYS_MENU_SELECT')")
    public List<MenuRespVo> list(@RequestBody MenuListReqVo reqVo) {
        return iMenuServiceComp.list(reqVo);
    }

    @ApiOperation("创建")
    @PostMapping("create")
    @PreAuthorize("hasRole('ROLE_SYS_MENU_CREATE')")
    public Long create(@Validated @RequestBody MenuReqVo menuReqVo) {
        return iMenuServiceComp.create(menuReqVo);
    }

    @ApiOperation("更新")
    @PostMapping("update")
    @PreAuthorize("hasRole('ROLE_SYS_MENU_UPDATE')")
    public void update(@Validated @RequestBody MenuReqVo menuReqVo) {
        iMenuServiceComp.update(menuReqVo);
    }

    @ApiOperation(value = "批量更新", notes = "传入id")
    @PostMapping("batch-update")
    @PreAuthorize("hasRole('ROLE_SYS_MENU_UPDATE')")
    public void batchUpdate(@Validated @RequestBody IdStatusListVo idStatusListVo) {
        iMenuServiceComp.updateBatch(idStatusListVo);
    }

    @ApiOperation(value = "批量删除", notes = "传入id")
    @PostMapping("batch-remove")
    @PreAuthorize("hasRole('ROLE_SYS_MENU_REMOVE')")
    public void removeUpdate(@Validated @RequestBody IdListVo<Long> idListVo) {
        iMenuServiceComp.removeBatch(idListVo);
    }

}
