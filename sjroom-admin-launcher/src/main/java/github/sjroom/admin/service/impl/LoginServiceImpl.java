package github.sjroom.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import github.sjroom.admin.bean.ContextDTO;
import github.sjroom.admin.bean.bo.UserBo;
import github.sjroom.admin.bean.entity.Role;
import github.sjroom.admin.bean.entity.User;
import github.sjroom.admin.bean.entity.UserRole;
import github.sjroom.admin.bean.entity.UserToken;
import github.sjroom.admin.service.*;
import github.sjroom.core.code.BaseErrorCode;
import github.sjroom.core.context.call.BusinessContext;
import github.sjroom.core.context.call.BusinessContextHolders;
import github.sjroom.core.response.RespVo;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.HttpUtil;
import github.sjroom.core.utils.JsonUtil;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.secrity.bean.JwtUser;
import github.sjroom.secrity.bean.JwtUserVo;
import github.sjroom.secrity.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * <B>说明：服务实现</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-02 16:44
 */
@Slf4j
@Service
@Validated
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private IUserTokenService iUserTokenService;


    @Override
    public RespVo login(JwtUserVo reqVo) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURL().toString().replace("login", "oauth/login");
        String result = HttpUtil.httpPost(url, JsonUtil.toJson(reqVo));
        RespVo respVo = JsonUtil.parse(result, RespVo.class);

        if (respVo.getStateCode().equals(BaseErrorCode.SUCCESS)) {
            JSONObject jsonObject = (JSONObject) respVo.getData();
            UserToken userToken = new UserToken();
            userToken.setExpireTime(new Date(jsonObject.getLong(JwtTokenUtil.HEADER_EXPIRATION_DATE)));
            userToken.setToken(jsonObject.getString(JwtTokenUtil.HEADER_TOKEN));
            userToken.setUserId(jsonObject.getLong(JwtTokenUtil.CLAIM_ID));
            userToken.setCreatedBy(userToken.getUserId());
            userToken.setUpdatedBy(userToken.getUserId());
            iUserTokenService.save(userToken);
        }

        log.info("LoginServiceImpl login result: {}", respVo);
        return respVo;
    }

    @Override
    public void logout() throws Exception {
        BusinessContextHolders.getXUserId();
    }


}
