package github.sjroom.admin.bean.bo;

import lombok.Data;
import java.util.Date;
import java.util.Set;

import github.sjroom.core.mybatis.page.PageReqParam;
import lombok.EqualsAndHashCode;

/**
 * <B>说明：角色与菜单对应关系</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-04 20:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleMenuBo extends PageReqParam {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 代理主键
	 */
	private Long roleMenuId;
	/**
	 * 角色ID(sys_role.role_id)
	 */
	private Long roleId;
	private Set<Long> roleIds;
	/**
	 * 菜单ID(sys_menu.menu_id)
	 */
	private Long menuId;
	private Set<Long> menuIds;
	/**
	 * 状态1:启用,2.禁用
	 */
	private Integer status;
	/**
	 * 创建人
	 */
	private Long createdBy;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新人
	 */
	private Long updatedBy;
	/**
	 * 更新时间
	 */
	private Date updatedAt;
}
