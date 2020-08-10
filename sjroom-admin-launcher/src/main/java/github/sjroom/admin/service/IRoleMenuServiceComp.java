package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.vo.RoleMenuReqVo;
import github.sjroom.admin.bean.vo.RoleMenuRespVo;
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
 * @date 2020-08-04 20:44
 */
@Validated
public interface IRoleMenuServiceComp {
	/**
	 * 查看
	 */
	RoleMenuRespVo find(IdVo<Long> idVo);

	/**
	 * 列表
	 */
	List<RoleMenuRespVo> list(RoleMenuReqVo reqVo);

	/**
	 * 创建
	 */
	Long create(RoleMenuReqVo accountReqVo);

	/**
	 * 更新
	 */
	void update(RoleMenuReqVo accountReqVo);

	/**
	 * 批量更新
	 */
	void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo);

	/**
	 * 批量移除
	 */
	void removeBatch(IdListVo<Long> idListVo);
}
