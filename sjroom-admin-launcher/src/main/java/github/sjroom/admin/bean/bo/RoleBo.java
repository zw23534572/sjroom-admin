package github.sjroom.admin.bean.bo;

import github.sjroom.admin.bean.vo.MenuReqVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;
import java.util.Set;

import github.sjroom.core.mybatis.page.PageReqParam;
import lombok.EqualsAndHashCode;

/**
 * <B>说明：角色</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleBo extends PageReqParam {
	/**
	 * 
	 */
	private Long id;
	/**
	 * 代理主键
	 */
	private Long roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态1:启用,0.禁用
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

	@ApiModelProperty("菜单集合列表")
	private Set<Long> menuIdList;

	@ApiModelProperty("菜单集合列表")
	private List<MenuBo> menus;
}
