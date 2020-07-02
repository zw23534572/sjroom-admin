package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.AccountBo;
import github.sjroom.admin.bean.entity.Account;
import github.sjroom.core.mybatis.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-12-16 14:14
 */
public interface IAccountService extends BaseService<Account> {

	/**
	 * 查看
	 *
	 * @param accountId 业务主键Id
	 * @return
	 */
	AccountBo findByBId(Long accountId);

	/**
	 * 列表
	 *
	 * @param accountIds 业务主键Id
	 * @return
	 */
	List<AccountBo> findByBIds(Set<Long> accountIds);

	/**
	 * 列表
	 *
	 * @param accountBo 业务model
	 * @return
	 */
	List<AccountBo> findList(AccountBo accountBo);

	/**
	 * 列表
	 *
	 * @param accountBo 业务model
	 * @return 键值对
	 */
	Map<Long, AccountBo> findMap(AccountBo accountBo);

	/**
	 * 分页
	 *
	 * @param accountBo
	 * @return
	 */
	IPage<AccountBo> findPage(AccountBo accountBo);
}
