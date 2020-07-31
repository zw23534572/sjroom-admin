package github.sjroom.admin.dao;

import github.sjroom.admin.bean.bo.UserTokenBo;
import github.sjroom.admin.bean.entity.UserToken;
import github.sjroom.core.mybatis.mapper.IMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <B>说明：系统用户Token</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-23 21:58
 */
@Mapper
public interface IUserTokenDao extends IMapper<UserToken> {

}
