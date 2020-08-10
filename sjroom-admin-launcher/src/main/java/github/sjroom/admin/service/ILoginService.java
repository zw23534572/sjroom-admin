package github.sjroom.admin.service;

import github.sjroom.admin.bean.ContextDTO;
import github.sjroom.core.response.RespVo;
import github.sjroom.secrity.bean.JwtUserVo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-02 16:44
 */
public interface ILoginService {

    /**
     * 登陆
     *
     * @param reqVo
     * @return
     */
    RespVo login(@RequestBody JwtUserVo reqVo) throws Exception;

    /**
     * 注销
     *
     * @throws Exception
     */
    void logout() throws Exception;

}
