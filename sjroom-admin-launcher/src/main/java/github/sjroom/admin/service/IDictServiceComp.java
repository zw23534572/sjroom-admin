package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.vo.DictPageReqVo;
import github.sjroom.admin.bean.vo.DictReqVo;
import github.sjroom.admin.bean.vo.DictRespVo;
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
 * @date 2020-07-20 15:17
 */
@Validated
public interface IDictServiceComp {
	/**
	 * 查看
	 */
	DictRespVo find(IdVo<Long> idVo);

	/**
	 * 分页
	 */
	IPage page(DictPageReqVo reqVo);

	/**
	 * 列表
	 */
	List<DictRespVo> list(DictPageReqVo reqVo);

	/**
	 * 创建
	 */
	Long create(DictReqVo accountReqVo);

	/**
	 * 更新
	 */
	void update(DictReqVo accountReqVo);

	/**
	 * 批量更新
	 */
	void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo);

	/**
	 * 批量移除
	 */
	void removeBatch(IdListVo<Long> idListVo);
}
