package github.sjroom.admin.bean;

import github.sjroom.admin.bean.bo.UserBo;
import github.sjroom.secrity.bean.JwtUser;
import lombok.Builder;
import lombok.Data;

/**
 * 上下文传递
 */
@Data
@Builder
public class ContextDTO {

    /**
     * 授权用户信息
     */
    private JwtUser jwtUser;

    /**
     * 用户信息
     */
    private UserBo userBo;
}