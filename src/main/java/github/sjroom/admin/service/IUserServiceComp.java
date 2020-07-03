package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.vo.UserPageReqVo;
import github.sjroom.admin.bean.vo.UserReqVo;
import github.sjroom.admin.bean.vo.UserRespVo;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-02 16:44
 */
@Validated
public interface IUserServiceComp {
	/**
	 * 查看
	 */
	UserRespVo find(IdVo<Long> idVo);

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
}
