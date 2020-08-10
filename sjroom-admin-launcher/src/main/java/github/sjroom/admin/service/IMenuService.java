package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.MenuBo;
import github.sjroom.admin.bean.entity.Menu;
import github.sjroom.core.mybatis.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 13:41
 */
public interface IMenuService extends BaseService<Menu> {

	/**
	 * 查看
	 *
	 * @param menuId 业务主键Id
	 * @return
	 */
	MenuBo findByBId(Long menuId);

	/**
	 * 列表
	 *
	 * @param menuIds 业务主键Id
	 * @return
	 */
	List<MenuBo> findByBIds(Set<Long> menuIds);

	/**
	 * 列表
	 *
	 * @param menuParentIds 菜单父级ID
	 * @return
	 */
	List<MenuBo> findByParentIds(Set<Long> menuParentIds);

	/**
	 * 列表
	 *
	 * @param menuBo 业务model
	 * @return
	 */
	List<MenuBo> findList(MenuBo menuBo);

	/**
	 * 列表
	 *
	 * @param menuBo 业务model
	 * @return 键值对
	 */
	Map<Long, MenuBo> findMap(MenuBo menuBo);

	/**
	 * 分页
	 *
	 * @param menuBo
	 * @return
	 */
	IPage<MenuBo> findPage(MenuBo menuBo);

	/**
	 * 注解：@fillFieldName，填充字段的专用方法
	 *
	 * @param bIds 业务model
	 * @return 键值对
	 */
	Map<Long, String> fillFieldName(Set<Long> bIds);
}
