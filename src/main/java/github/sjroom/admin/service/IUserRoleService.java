package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.UserRoleBo;
import github.sjroom.admin.bean.entity.UserRole;
import github.sjroom.core.mybatis.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-03 14:22
 */
public interface IUserRoleService extends BaseService<UserRole> {

	/**
	 * 查看
	 *
	 * @param userRoleId 业务主键Id
	 * @return
	 */
	UserRoleBo findByBId(Long userRoleId);

	/**
	 * 列表
	 *
	 * @param userRoleIds 业务主键Id
	 * @return
	 */
	List<UserRoleBo> findByBIds(Set<Long> userRoleIds);

	/**
	 * 列表
	 *
	 * @param userIds 用户ID集合
	 * @return
	 */
	List<UserRoleBo> findByUserIds(Set<Long> userIds);

	/**
	 * 列表
	 *
	 * @param userRoleBo 业务model
	 * @return
	 */
	List<UserRoleBo> findList(UserRoleBo userRoleBo);

	/**
	 * 列表
	 *
	 * @param userRoleBo 业务model
	 * @return 键值对
	 */
	Map<Long, UserRoleBo> findMap(UserRoleBo userRoleBo);

	/**
	 * 分页
	 *
	 * @param userRoleBo
	 * @return
	 */
	IPage<UserRoleBo> findPage(UserRoleBo userRoleBo);

}
