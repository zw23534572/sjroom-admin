package github.sjroom.admin.bean.bo;

import github.sjroom.admin.bean.entity.User;
import github.sjroom.admin.bean.entity.UserRole;
import lombok.Data;
import java.util.Date;
import java.util.List;

import github.sjroom.core.mybatis.page.PageReqParam;
import lombok.EqualsAndHashCode;

/**
 * <B>说明：用户信息</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:51
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserBo extends PageReqParam {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 账号
	 */
	private Long userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 密码明文
	 */
	private String passwordPlaintext;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 用户类型： 0:普通用户，1:管理员
	 */
	private Integer type;
	/**
	 * 0:禁用,1:正常
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 创建人
	 */
	private String createdBy;
	/**
	 * 更新时间
	 */
	private Date updatedAt;
	/**
	 * 更新人
	 */
	private String updatedBy;
	/**
	 * 用户列表
	 */
	private User user;
	/**
	 * 用户角色关系列表
	 */
	private List<Long> roleIdList;
	/**
	 * 用户角色关系列表
	 */
	private List<UserRole> userRoles;

}
