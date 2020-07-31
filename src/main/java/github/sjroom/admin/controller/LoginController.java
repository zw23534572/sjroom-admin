package github.sjroom.admin.controller;

import github.sjroom.admin.service.ILoginService;
import github.sjroom.admin.service.IUserServiceComp;
import github.sjroom.core.response.RespVo;
import github.sjroom.secrity.bean.UserReqVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 登陆
     *
     * @param reqVo
     * @return
     */
    @PostMapping("/login")
    public RespVo login(@RequestBody UserReqVo reqVo) throws Exception {
        return iLoginService.login(reqVo);
    }

    /**
     * 注销
     *
     * @return
     */
    @PostMapping("/logout")
    public void logout() throws Exception {
       
    }
}
