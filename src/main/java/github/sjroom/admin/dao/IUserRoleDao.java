package github.sjroom.admin.dao;

import github.sjroom.admin.bean.bo.UserRoleBo;
import github.sjroom.admin.bean.entity.UserRole;
import github.sjroom.core.mybatis.mapper.IMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <B>说明：用户与角色对应关系</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:10
 */
@Mapper
public interface IUserRoleDao extends IMapper<UserRole> {

}
