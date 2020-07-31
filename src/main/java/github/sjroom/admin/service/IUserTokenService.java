package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.UserTokenBo;
import github.sjroom.admin.bean.entity.UserToken;
import github.sjroom.core.mybatis.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-23 21:58
 */
public interface IUserTokenService extends BaseService<UserToken> {

	/**
	 * 查看
	 *
	 * @param userTokenId 业务主键Id
	 * @return
	 */
	UserTokenBo findByBId(Long userTokenId);

	/**
	 * 列表
	 *
	 * @param userTokenIds 业务主键Id
	 * @return
	 */
	List<UserTokenBo> findByBIds(Set<Long> userTokenIds);

	/**
	 * 列表
	 *
	 * @param userTokenBo 业务model
	 * @return
	 */
	List<UserTokenBo> findList(UserTokenBo userTokenBo);

	/**
	 * 列表
	 *
	 * @param userTokenBo 业务model
	 * @return 键值对
	 */
	Map<Long, UserTokenBo> findMap(UserTokenBo userTokenBo);

	/**
	 * 分页
	 *
	 * @param userTokenBo
	 * @return
	 */
	IPage<UserTokenBo> findPage(UserTokenBo userTokenBo);

}
