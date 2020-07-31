package github.sjroom.admin.service;

import com.alibaba.fastjson.JSON;
import github.sjroom.admin.bean.ContextDTO;
import github.sjroom.admin.bean.bo.UserBo;
import github.sjroom.core.context.call.BusinessContext;
import github.sjroom.core.context.call.BusinessContextHolders;
import github.sjroom.core.response.RespVo;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.secrity.bean.JwtUser;
import github.sjroom.secrity.bean.UserReqVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    RespVo login(@RequestBody UserReqVo reqVo) throws Exception;

    /**
     * 注销
     *
     * @throws Exception
     */
    void logout() throws Exception;

    /**
     * 加载数据
     *
     * @param username
     * @return
     */
    ContextDTO loadContextData(String username);
}
