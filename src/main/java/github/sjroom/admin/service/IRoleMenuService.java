package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.RoleMenuBo;
import github.sjroom.admin.bean.entity.RoleMenu;
import github.sjroom.core.mybatis.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-04 20:44
 */
public interface IRoleMenuService extends BaseService<RoleMenu> {

	/**
	 * 查看
	 *
	 * @param roleMenuId 业务主键Id
	 * @return
	 */
	RoleMenuBo findByBId(Long roleMenuId);

	/**
	 * 列表
	 *
	 * @param roleMenuIds 业务主键Id
	 * @return
	 */
	List<RoleMenuBo> findByBIds(Set<Long> roleMenuIds);

	/**
	 * 列表
	 *
	 * @param roleIds 角色ID集合
	 * @return
	 */
	List<RoleMenuBo> findByRoleIds(Set<Long> roleIds);

	/**
	 * 列表
	 *
	 * @param menuIds 菜单集合
	 * @return
	 */
	List<RoleMenuBo> findByMenuIds(Set<Long> menuIds);

	/**
	 * 列表
	 *
	 * @param roleMenuBo 业务model
	 * @return
	 */
	List<RoleMenuBo> findList(RoleMenuBo roleMenuBo);

	/**
	 * 列表
	 *
	 * @param roleMenuBo 业务model
	 * @return 键值对
	 */
	Map<Long, RoleMenuBo> findMap(RoleMenuBo roleMenuBo);

	/**
	 * 分页
	 *
	 * @param roleMenuBo
	 * @return
	 */
	IPage<RoleMenuBo> findPage(RoleMenuBo roleMenuBo);

}
