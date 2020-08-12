package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.vo.UserPageReqVo;
import github.sjroom.admin.bean.vo.UserReqVo;
import github.sjroom.admin.bean.vo.UserRespVo;
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
 * @date 2020-07-24 11:51
 */
@Validated
public interface IUserServiceComp {
	/**
	 * 查看
	 */
	UserRespVo info();

	/**
	 * 查看
	 */
	UserRespVo find(Long bid);

	/**
	 * 分页
	 */
	IPage page(UserPageReqVo reqVo);

	/**
	 * 列表
	 */
	List<UserRespVo> list(UserReqVo reqVo);

	/**
	 * 创建
	 */
	Long create(UserReqVo accountReqVo);

	/**
	 * 更新
	 */
	void update(UserReqVo accountReqVo);

	/**
	 * 批量更新
	 */
	void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo);

	/**
	 * 批量移除
	 */
	void removeBatch(IdListVo<Long> idListVo);
}
