package github.sjroom.admin.controller;

import github.sjroom.admin.bean.vo.RoleMenuReqVo;
import github.sjroom.admin.bean.vo.RolePageReqVo;
import github.sjroom.admin.bean.vo.RoleReqVo;
import github.sjroom.admin.bean.vo.RoleRespVo;
import github.sjroom.admin.service.IRoleServiceComp;
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
 * <B>说明：角色 控制器</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2020-07-24 11:11
 */
@Slf4j
@Validated
@RestController
@RequestMapping("role")
@Api("角色 控制器")
public class RoleController {
	@Autowired
	private IRoleServiceComp iRoleServiceComp;
	
	@ApiOperation(value = "查看", notes = "传入id")
	@PostMapping("find")
	@FillField
	@PreAuthorize("hasRole('ROLE_SYS_ROLE_SELECT')")
	public RoleRespVo find(@Validated @RequestBody IdVo<Long> idVo) {
		return iRoleServiceComp.find(idVo);
	}
	
	@ApiOperation("分页")
	@PostMapping("page")
	@FillField
	@PreAuthorize("hasRole('ROLE_SYS_ROLE_SELECT')")
	public PageResult page(@Validated @RequestBody RolePageReqVo reqVo) {
		return PageUtil.toPageResult(iRoleServiceComp.page(reqVo), RoleRespVo.class);
	}
	
	@ApiOperation("列表")
	@PostMapping("list")
	@FillField
	@PreAuthorize("hasRole('ROLE_SYS_ROLE_SELECT')")
	public List<RoleRespVo> list(@Validated @RequestBody RoleReqVo reqVo) {
		return iRoleServiceComp.list(reqVo);
	}
	
	@ApiOperation("创建")
	@PostMapping("create")
	@PreAuthorize("hasRole('ROLE_SYS_ROLE_CREATE')")
	public Long create(@Validated @RequestBody RoleReqVo roleReqVo) {
		return iRoleServiceComp.create(roleReqVo);
	}
	
	@ApiOperation("更新")
	@PostMapping("update")
	@PreAuthorize("hasRole('ROLE_SYS_ROLE_UPDATE')")
	public void update(@Validated @RequestBody RoleReqVo roleReqVo) {
		iRoleServiceComp.update(roleReqVo);
	}
	
	@ApiOperation(value = "批量更新", notes = "传入id")
	@PostMapping("batch-update")
	@PreAuthorize("hasRole('ROLE_SYS_ROLE_UPDATE')")
	public void batchUpdate(@Validated @RequestBody IdStatusListVo idStatusListVo) {
		iRoleServiceComp.updateBatch(idStatusListVo);
	}

	@ApiOperation(value = "批量删除", notes = "传入id")
	@PostMapping("batch-remove")
	@PreAuthorize("hasRole('ROLE_SYS_ROLE_REMOVE')")
	public void removeUpdate(@Validated @RequestBody IdListVo<Long> idListVo) {
		iRoleServiceComp.removeBatch(idListVo);
	}

	@ApiOperation("绑定菜单")
	@PostMapping("bind/menu")
	@PreAuthorize("hasRole('ROLE_BIND_MENU')")
	public void bindMenu(@Validated @RequestBody RoleMenuReqVo reqVo) {
		iRoleServiceComp.bindMenu(reqVo);
	}
}
