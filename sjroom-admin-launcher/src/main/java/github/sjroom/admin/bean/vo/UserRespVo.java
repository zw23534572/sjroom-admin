package github.sjroom.admin.bean.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * <B>说明：用户信息</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:51
 */
@Data
public class UserRespVo  {

	@ApiModelProperty("账号")
	private Long userId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("密码")
	private String password;

	@ApiModelProperty("密码明文")
	private String passwordPlaintext;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("手机号码")
	private String mobile;

	@ApiModelProperty("用户类型： 0:普通用户，1:管理员")
	private Integer type;

	@ApiModelProperty("0:禁用,1:正常")
	private Integer status;
}
