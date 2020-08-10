package github.sjroom.admin.bean.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * <B>说明：角色与菜单对应关系</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-04 20:44
 */
@Data
public class RoleMenuRespVo  {

	@ApiModelProperty("代理主键")
	private Long roleMenuId;

	@ApiModelProperty("角色ID(sys_role.role_id)")
	private Long roleId;

	@ApiModelProperty("菜单ID(sys_menu.menu_id)")
	private Long menuId;

	@ApiModelProperty("状态1:启用,2.禁用")
	private Integer status;
}
