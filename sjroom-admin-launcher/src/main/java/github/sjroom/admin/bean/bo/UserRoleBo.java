package github.sjroom.admin.bean.bo;

import lombok.Data;
import java.util.Date;
import github.sjroom.core.mybatis.page.PageReqParam;
import lombok.EqualsAndHashCode;

/**
 * <B>说明：用户与角色对应关系</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-03 14:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRoleBo extends PageReqParam {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 代理主键
	 */
	private Long userRoleId;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 角色ID
	 */
	private Long roleId;
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
