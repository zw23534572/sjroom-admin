package github.sjroom.admin.controller;

import github.sjroom.admin.service.ILoginService;
import github.sjroom.core.response.RespVo;
import github.sjroom.secrity.bean.JwtUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
@Api(" 登陆控制器")
public class LoginController {

    @Autowired
    private ILoginService iLoginService;

    @PostMapping("/login")
    @ApiOperation(value = "登陆")
    public RespVo login(@RequestBody JwtUserVo reqVo) throws Exception {
        return iLoginService.login(reqVo);
    }

    @PostMapping("/sys/logout")
    @ApiOperation(value = "注销")
    public void logout() throws Exception {
       log.info("LoginController logout");
    }
}
