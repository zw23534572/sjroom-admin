package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.vo.RolePageReqVo;
import github.sjroom.admin.bean.vo.RoleReqVo;
import github.sjroom.admin.bean.vo.RoleRespVo;
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
 * @date 2020-07-24 11:11
 */
@Validated
public interface IRoleServiceComp {
	/**
	 * 查看
	 */
	RoleRespVo find(IdVo<Long> idVo);

	/**
	 * 分页
	 */
	IPage page(RolePageReqVo reqVo);

	/**
	 * 列表
	 */
	List<RoleRespVo> list(RoleReqVo reqVo);

	/**
	 * 创建
	 */
	Long create(RoleReqVo accountReqVo);

	/**
	 * 更新
	 */
	void update(RoleReqVo accountReqVo);

	/**
	 * 批量更新
	 */
	void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo);

	/**
	 * 批量移除
	 */
	void removeBatch(IdListVo<Long> idListVo);
}
