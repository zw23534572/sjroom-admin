package github.sjroom.admin.filter;

import lombok.Data;

/**
 * Created by echisan
 * on 2018/6/23
 */
@Data
public class UserReqVo {

    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 是否记住
     */
    private Integer rememberMe;
}
