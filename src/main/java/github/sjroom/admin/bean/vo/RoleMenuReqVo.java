package github.sjroom.admin.bean.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
/**
 * <B>说明：角色与菜单对应关系</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-04 20:44
 */
@Data
public class RoleMenuReqVo  {

    @ApiModelProperty("角色ID(sys_role.role_id)")
	@NotNull
    private Long roleId;

    @ApiModelProperty("菜单ID集合")
	@NotNull
    private Set<Long> menuIds;
}
