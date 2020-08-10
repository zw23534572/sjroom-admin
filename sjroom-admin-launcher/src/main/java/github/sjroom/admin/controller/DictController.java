package github.sjroom.admin.controller;

import github.sjroom.admin.bean.vo.DictPageReqVo;
import github.sjroom.admin.bean.vo.DictReqVo;
import github.sjroom.admin.bean.vo.DictRespVo;
import github.sjroom.admin.service.IDictServiceComp;
import github.sjroom.core.mybatis.annotation.FillField;
import github.sjroom.core.mybatis.page.PageResult;
import github.sjroom.core.mybatis.page.PageUtil;
import github.sjroom.core.utils.BeanUtil;
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
 * <B>说明：数据字典表 控制器</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2020-07-20 15:17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dict")
@Api("数据字典表 控制器")
public class DictController {
	@Autowired
	private IDictServiceComp iDictServiceComp;
	
	@ApiOperation(value = "查看", notes = "传入id")
	@PostMapping("find")
	@FillField
	@PreAuthorize("hasRole('ROLE_SYS_DICT_SELECT')")
	public DictRespVo find(@Validated @RequestBody IdVo<Long> idVo) {
		return iDictServiceComp.find(idVo);
	}
	
	@ApiOperation("分页")
	@PostMapping("page")
	@FillField
	@PreAuthorize("hasRole('ROLE_SYS_DICT_SELECT')")
	public PageResult page(@Validated @RequestBody DictPageReqVo reqVo) {
		return PageUtil.toPageResult(iDictServiceComp.page(reqVo), DictRespVo.class);
	}
	
	@ApiOperation("列表")
	@PostMapping("list")
	@FillField
	public List<DictRespVo> list(@Validated @RequestBody DictPageReqVo reqVo) {
		return iDictServiceComp.list(reqVo);
	}
	
	@ApiOperation("创建")
	@PostMapping("create")
	@PreAuthorize("hasRole('ROLE_SYS_DICT_CREATE')")
	public Long create(@Validated @RequestBody DictReqVo dictReqVo) {
		return iDictServiceComp.create(dictReqVo);
	}
	
	@ApiOperation("更新")
	@PostMapping("update")
	@PreAuthorize("hasRole('ROLE_SYS_DICT_UPDATE')")
	public void update(@Validated @RequestBody DictReqVo dictReqVo) {
		iDictServiceComp.update(dictReqVo);
	}
	
	@ApiOperation(value = "批量更新", notes = "传入id")
	@PostMapping("batch-update")
	@PreAuthorize("hasRole('ROLE_SYS_DICT_UPDATE')")
	public void batchUpdate(@Validated @RequestBody IdStatusListVo idStatusListVo) {
		iDictServiceComp.updateBatch(idStatusListVo);
	}

	@ApiOperation(value = "批量删除", notes = "传入id")
	@PostMapping("batch-remove")
	@PreAuthorize("hasRole('ROLE_SYS_DICT_REMOVE')")
	public void removeUpdate(@Validated @RequestBody IdListVo<Long> idListVo) {
		iDictServiceComp.removeBatch(idListVo);
	}
}
