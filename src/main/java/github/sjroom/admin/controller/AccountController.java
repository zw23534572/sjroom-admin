package github.sjroom.admin.controller;

import github.sjroom.core.mybatis.page.PageResult;
import github.sjroom.core.mybatis.page.PageUtil;
import github.sjroom.admin.bean.vo.AccountPageReqVo;
import github.sjroom.admin.bean.vo.AccountReqVo;
import github.sjroom.admin.bean.vo.AccountRespVo;
import github.sjroom.admin.service.IAccountServiceComp;
import github.sjroom.core.mybatis.annotation.FillField;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <B>说明： 控制器</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2020-06-15 16:23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("api/account")
@Api(" 控制器")
public class AccountController {
	@Autowired
	private IAccountServiceComp iAccountServiceComp;

	@ApiOperation(value = "查看", notes = "传入id")
	@PostMapping("find")
	@FillField
	public AccountRespVo find(@Validated @RequestBody IdVo<Long> idVo) {
		return iAccountServiceComp.find(idVo);
	}

	@ApiOperation("分页")
	@PostMapping("page")
	@FillField
	public PageResult page(@Validated @RequestBody AccountPageReqVo reqVo) {
		return PageUtil.toPageResult(iAccountServiceComp.page(reqVo), AccountRespVo.class);
	}

	@ApiOperation("列表")
	@PostMapping("list")
	@FillField
	public List<AccountRespVo> list(@Validated @RequestBody AccountReqVo reqVo) {
		return iAccountServiceComp.list(reqVo);
	}

	@ApiOperation("创建")
	@PostMapping("create")
	public Long create(@Validated @RequestBody AccountReqVo accountReqVo) {
		return iAccountServiceComp.create(accountReqVo);
	}

	@ApiOperation("更新")
	@PostMapping("update")
	public void update(@Validated @RequestBody AccountReqVo accountReqVo) {
		iAccountServiceComp.update(accountReqVo);
	}

	@ApiOperation(value = "批量更新", notes = "传入id")
	@PostMapping("batch-update")
	public void batchUpdate(@Validated @RequestBody IdStatusListVo idStatusListVo) {
		iAccountServiceComp.updateBatch(idStatusListVo);
	}
}
