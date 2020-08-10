package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.RoleBo;
import github.sjroom.admin.bean.entity.Role;
import github.sjroom.core.mybatis.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:11
 */
public interface IRoleService extends BaseService<Role> {

	/**
	 * 查看
	 *
	 * @param roleId 业务主键Id
	 * @return
	 */
	RoleBo findByBId(Long roleId);

	/**
	 * 列表
	 *
	 * @param roleIds 业务主键Id
	 * @return
	 */
	List<RoleBo> findByBIds(Set<Long> roleIds);

	/**
	 * 列表
	 *
	 * @param roleBo 业务model
	 * @return
	 */
	List<RoleBo> findList(RoleBo roleBo);

	/**
	 * 列表
	 *
	 * @param roleBo 业务model
	 * @return 键值对
	 */
	Map<Long, RoleBo> findMap(RoleBo roleBo);

	/**
	 * 分页
	 *
	 * @param roleBo
	 * @return
	 */
	IPage<RoleBo> findPage(RoleBo roleBo);

	/**
	 * 注解：@fillFieldName，填充字段的专用方法
	 *
	 * @param bIds 业务model
	 * @return 键值对
	 */
	Map<Long, String> fillFieldName(Set<Long> bIds);
}
