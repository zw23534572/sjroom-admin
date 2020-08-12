package github.sjroom.admin.bean.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
/**
 * <B>说明：用户信息</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:51
 */
@Data
public class UserReqVo  {

    @ApiModelProperty("账号")
	@NotNull
    private Long userId;

    @ApiModelProperty("用户名称")
	@NotBlank
    private String userName;

    @ApiModelProperty("密码")
	@NotBlank
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("0:禁用,1:正常")
    private Integer status;

    @ApiModelProperty("用户角色列表")
    private List<Long> roleIdList;
}
