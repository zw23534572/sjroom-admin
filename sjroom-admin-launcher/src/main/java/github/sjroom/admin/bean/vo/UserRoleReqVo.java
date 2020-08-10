package github.sjroom.admin.bean.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class UserRoleReqVo  {

    @ApiModelProperty("代理主键")
	@NotNull
    private Long userRoleId;

    @ApiModelProperty("用户ID")
	@NotNull
    private Long userId;

    @ApiModelProperty("角色ID")
	@NotNull
    private Long roleId;

    @ApiModelProperty("状态1:启用,2.禁用")
    private Integer status;
}
