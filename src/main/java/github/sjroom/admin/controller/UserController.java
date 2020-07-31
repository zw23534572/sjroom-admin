package github.sjroom.admin.controller;

import github.sjroom.admin.bean.vo.UserPageReqVo;
import github.sjroom.admin.bean.vo.UserReqVo;
import github.sjroom.admin.bean.vo.UserRespVo;
import github.sjroom.admin.service.IUserServiceComp;
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
 * <B>说明：用户信息 控制器</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2020-07-24 11:51
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user")
@Api("用户信息 控制器")
public class UserController {
	@Autowired
	private IUserServiceComp iUserServiceComp;
	
	@ApiOperation(value = "查看", notes = "传入id")
	@PostMapping("find")
	@FillField
	@PreAuthorize("hasRole('ROLE_SYS_USER_SELECT')")
	public UserRespVo find(@Validated @RequestBody IdVo<Long> idVo) {
		return iUserServiceComp.find(idVo);
	}
	
	@ApiOperation("分页")
	@PostMapping("page")
	@FillField
	@PreAuthorize("hasRole('ROLE_SYS_USER_SELECT')")
	public PageResult page(@Validated @RequestBody UserPageReqVo reqVo) {
		return PageUtil.toPageResult(iUserServiceComp.page(reqVo), UserRespVo.class);
	}
	
	@ApiOperation("列表")
	@PostMapping("list")
	@FillField
	@PreAuthorize("hasRole('ROLE_SYS_USER_SELECT')")
	public List<UserRespVo> list(@Validated @RequestBody UserReqVo reqVo) {
		return iUserServiceComp.list(reqVo);
	}
	
	@ApiOperation("创建")
	@PostMapping("create")
	@PreAuthorize("hasRole('ROLE_SYS_USER_CREATE')")
	public Long create(@Validated @RequestBody UserReqVo userReqVo) {
		return iUserServiceComp.create(userReqVo);
	}
	
	@ApiOperation("更新")
	@PostMapping("update")
	@PreAuthorize("hasRole('ROLE_SYS_USER_UPDATE')")
	public void update(@Validated @RequestBody UserReqVo userReqVo) {
		iUserServiceComp.update(userReqVo);
	}
	
	@ApiOperation(value = "批量更新", notes = "传入id")
	@PostMapping("batch-update")
	@PreAuthorize("hasRole('ROLE_SYS_USER_UPDATE')")
	public void batchUpdate(@Validated @RequestBody IdStatusListVo idStatusListVo) {
		iUserServiceComp.updateBatch(idStatusListVo);
	}

	@ApiOperation(value = "批量删除", notes = "传入id")
	@PostMapping("batch-remove")
	@PreAuthorize("hasRole('ROLE_SYS_USER_REMOVE')")
	public void removeUpdate(@Validated @RequestBody IdListVo<Long> idListVo) {
		iUserServiceComp.removeBatch(idListVo);
	}
}
