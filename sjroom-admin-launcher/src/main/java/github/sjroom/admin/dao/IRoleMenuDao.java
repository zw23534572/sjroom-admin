package github.sjroom.admin.dao;

import github.sjroom.admin.bean.bo.RoleMenuBo;
import github.sjroom.admin.bean.entity.RoleMenu;
import github.sjroom.core.mybatis.mapper.IMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <B>说明：角色与菜单对应关系</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-04 20:44
 */
@Mapper
public interface IRoleMenuDao extends IMapper<RoleMenu> {

}
