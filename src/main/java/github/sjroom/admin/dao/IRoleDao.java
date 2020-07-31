package github.sjroom.admin.dao;

import github.sjroom.admin.bean.bo.RoleBo;
import github.sjroom.admin.bean.entity.Role;
import github.sjroom.core.mybatis.mapper.IMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <B>说明：角色</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:11
 */
@Mapper
public interface IRoleDao extends IMapper<Role> {

}
