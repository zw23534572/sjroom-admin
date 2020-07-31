package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.vo.UserRolePageReqVo;
import github.sjroom.admin.bean.vo.UserRoleReqVo;
import github.sjroom.admin.bean.vo.UserRoleRespVo;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import org.springframework.validation.annotation.Validated;
import github.sjroom.web.vo.IdListVo;
import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:10
 */
@Validated
public interface IUserRoleServiceComp {
	/**
	 * 查看
	 */
	UserRoleRespVo find(IdVo<Long> idVo);

	/**
	 * 分页
	 */
	IPage page(UserRolePageReqVo reqVo);

	/**
	 * 列表
	 */
	List<UserRoleRespVo> list(UserRoleReqVo reqVo);

	/**
	 * 创建
	 */
	Long create(UserRoleReqVo accountReqVo);

	/**
	 * 更新
	 */
	void update(UserRoleReqVo accountReqVo);

	/**
	 * 批量更新
	 */
	void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo);

	/**
	 * 批量移除
	 */
	void removeBatch(IdListVo<Long> idListVo);
}
