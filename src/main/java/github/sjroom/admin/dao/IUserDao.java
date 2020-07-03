package github.sjroom.admin.dao;

import github.sjroom.admin.bean.bo.UserBo;
import github.sjroom.admin.bean.entity.User;
import github.sjroom.core.mybatis.mapper.IMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-02 16:44
 */
@Mapper
public interface IUserDao extends IMapper<User> {

}
