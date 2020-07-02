package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.vo.AccountPageReqVo;
import github.sjroom.admin.bean.vo.AccountReqVo;
import github.sjroom.admin.bean.vo.AccountRespVo;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2019-12-16 14:14
 */
@Validated
public interface IAccountServiceComp {
	/**
	 * 查看
	 */
	AccountRespVo find(IdVo<Long> idVo);

	/**
	 * 分页
	 */
	IPage page(AccountPageReqVo reqVo);

	/**
	 * 列表
	 */
	List<AccountRespVo> list(AccountReqVo reqVo);

	/**
	 * 创建
	 */
	Long create(AccountReqVo accountReqVo);

	/**
	 * 更新
	 */
	void update(AccountReqVo accountReqVo);

	/**
	 * 批量更新
	 */
	void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo);
}
