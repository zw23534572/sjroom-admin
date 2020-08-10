package github.sjroom.admin.bean.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * <B>说明：用户与角色对应关系</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-03 14:22
 */
@Data
public class UserRoleRespVo  {

	@ApiModelProperty("代理主键")
	private Long userRoleId;

	@ApiModelProperty("用户ID")
	private Long userId;

	@ApiModelProperty("角色ID")
	private Long roleId;

	@ApiModelProperty("状态1:启用,2.禁用")
	private Integer status;
}
