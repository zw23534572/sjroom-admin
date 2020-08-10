package github.sjroom.admin.dao;

import github.sjroom.admin.bean.bo.MenuBo;
import github.sjroom.admin.bean.entity.Menu;
import github.sjroom.core.mybatis.mapper.IMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <B>说明：菜单管理</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 13:41
 */
@Mapper
public interface IMenuDao extends IMapper<Menu> {

}
