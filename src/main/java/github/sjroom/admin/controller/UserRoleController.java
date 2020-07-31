package github.sjroom.admin.controller;

import github.sjroom.admin.bean.vo.UserRolePageReqVo;
import github.sjroom.admin.bean.vo.UserRoleReqVo;
import github.sjroom.admin.bean.vo.UserRoleRespVo;
import github.sjroom.admin.service.IUserRoleServiceComp;
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
 * <B>说明：用户与角色对应关系 控制器</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2020-07-24 11:10
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user-role")
@Api("用户与角色对应关系 控制器")
public class UserRoleController {
	@Autowired
	private IUserRoleServiceComp iUserRoleServiceComp;
	
	@ApiOperation(value = "查看", notes = "传入id")
	@PostMapping("find")
	@FillField
	@PreAuthorize("hasRole('ROLE_SYS_USER_ROLE_SELECT')")
	public UserRoleRespVo find(@Validated @RequestBody IdVo<Long> idVo) {
		return iUserRoleServiceComp.find(idVo);
	}
	
	@ApiOperation("分页")
	@PostMapping("page")
	@FillField
	@PreAuthorize("hasRole('ROLE_SYS_USER_ROLE_SELECT')")
	public PageResult page(@Validated @RequestBody UserRolePageReqVo reqVo) {
		return PageUtil.toPageResult(iUserRoleServiceComp.page(reqVo), UserRoleRespVo.class);
	}
	
	@ApiOperation("列表")
	@PostMapping("list")
	@FillField
	@PreAuthorize("hasRole('ROLE_SYS_USER_ROLE_SELECT')")
	public List<UserRoleRespVo> list(@Validated @RequestBody UserRoleReqVo reqVo) {
		return iUserRoleServiceComp.list(reqVo);
	}
	
	@ApiOperation("创建")
	@PostMapping("create")
	@PreAuthorize("hasRole('ROLE_SYS_USER_ROLE_CREATE')")
	public Long create(@Validated @RequestBody UserRoleReqVo userRoleReqVo) {
		return iUserRoleServiceComp.create(userRoleReqVo);
	}
	
	@ApiOperation("更新")
	@PostMapping("update")
	@PreAuthorize("hasRole('ROLE_SYS_USER_ROLE_UPDATE')")
	public void update(@Validated @RequestBody UserRoleReqVo userRoleReqVo) {
		iUserRoleServiceComp.update(userRoleReqVo);
	}
	
	@ApiOperation(value = "批量更新", notes = "传入id")
	@PostMapping("batch-update")
	@PreAuthorize("hasRole('ROLE_SYS_USER_ROLE_UPDATE')")
	public void batchUpdate(@Validated @RequestBody IdStatusListVo idStatusListVo) {
		iUserRoleServiceComp.updateBatch(idStatusListVo);
	}

	@ApiOperation(value = "批量删除", notes = "传入id")
	@PostMapping("batch-remove")
	@PreAuthorize("hasRole('ROLE_SYS_USER_ROLE_REMOVE')")
	public void removeUpdate(@Validated @RequestBody IdListVo<Long> idListVo) {
		iUserRoleServiceComp.removeBatch(idListVo);
	}
}
