package github.sjroom.admin.bean.bo;

import lombok.Data;
import java.util.Date;
import github.sjroom.core.mybatis.page.PageReqParam;
import lombok.EqualsAndHashCode;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-02 16:44
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
	 * 0:禁用,1:正常
	 */
	private Integer status;
}
