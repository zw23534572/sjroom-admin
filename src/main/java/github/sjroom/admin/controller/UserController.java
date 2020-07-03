package github.sjroom.admin.controller;

import github.sjroom.admin.bean.vo.UserPageReqVo;
import github.sjroom.admin.bean.vo.UserReqVo;
import github.sjroom.admin.bean.vo.UserRespVo;
import github.sjroom.admin.service.IUserServiceComp;
import github.sjroom.core.mybatis.annotation.FillField;
import github.sjroom.core.mybatis.page.PageResult;
import github.sjroom.core.mybatis.page.PageUtil;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * @since 2020-07-02 16:44
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user")
@Api(" 控制器")
public class UserController {
    @Autowired
    private IUserServiceComp iUserServiceComp;

    @ApiOperation(value = "查看", notes = "传入id")
    @PostMapping("find")
    @FillField
    public UserRespVo find(@Validated @RequestBody IdVo<Long> idVo) {
        return iUserServiceComp.find(idVo);
    }

    @ApiOperation("分页")
    @PostMapping("page")
    @FillField
    public PageResult page(@Validated @RequestBody UserPageReqVo reqVo) {
        return PageUtil.toPageResult(iUserServiceComp.page(reqVo), UserRespVo.class);
    }

    @ApiOperation("列表")
    @PostMapping("list")
    @PreAuthorize("hasRole('ROLE_USER')")
    @FillField
    public List<UserRespVo> list(@Validated @RequestBody UserReqVo reqVo) {
        return iUserServiceComp.list(reqVo);
    }

    @ApiOperation("创建")
    @PostMapping("create")
    public Long create(@Validated @RequestBody UserReqVo userReqVo) {
        return iUserServiceComp.create(userReqVo);
    }

    @ApiOperation("更新")
    @PostMapping("update")
    public void update(@Validated @RequestBody UserReqVo userReqVo) {
        iUserServiceComp.update(userReqVo);
    }

    @ApiOperation(value = "批量更新", notes = "传入id")
    @PostMapping("batch-update")
    public void batchUpdate(@Validated @RequestBody IdStatusListVo idStatusListVo) {
        iUserServiceComp.updateBatch(idStatusListVo);
    }
}
