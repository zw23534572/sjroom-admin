package github.sjroom.admin.bean.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-02 16:44
 */
@Data
public class UserReqVo  {

    @ApiModelProperty("账号")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("密码加盐")
    private String salt;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("0:禁用,1:正常")
    private Integer status;
}
