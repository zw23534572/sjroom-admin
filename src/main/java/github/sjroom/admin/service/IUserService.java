package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.UserBo;
import github.sjroom.admin.bean.entity.User;
import github.sjroom.core.mybatis.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-02 16:44
 */
public interface IUserService extends BaseService<User> {

	/**
	 * 查看
	 *
	 * @param userId 业务主键Id
	 * @return
	 */
	UserBo findByBId(Long userId);

	/**
	 * 列表
	 *
	 * @param userIds 业务主键Id
	 * @return
	 */
	List<UserBo> findByBIds(Set<Long> userIds);

	/**
	 * 列表
	 *
	 * @param userBo 业务model
	 * @return
	 */
	List<UserBo> findList(UserBo userBo);

	/**
	 * 列表
	 *
	 * @param bIds 业务model
	 * @return 键值对
	 */
	Map<Long, String> fillFieldName(Set<Long> bIds);

	/**
	 * 分页
	 *
	 * @param userBo
	 * @return
	 */
	IPage<UserBo> findPage(UserBo userBo);
}
