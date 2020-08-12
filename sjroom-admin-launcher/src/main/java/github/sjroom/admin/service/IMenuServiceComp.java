package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.vo.*;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import org.springframework.validation.annotation.Validated;
import github.sjroom.web.vo.IdListVo;

import java.util.List;
import java.util.Set;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 13:41
 */
@Validated
public interface IMenuServiceComp {
    /**
     * 导航
     * @return
     */
    List<MenuTreeRespVo> nav(Set<Integer> types);

    /**
     * 查看
     */
    MenuRespVo find(Long bid);

    /**
     * 分页
     */
    IPage page(MenuPageReqVo reqVo);

    /**
     * 列表
     */
    List<MenuRespVo> list(MenuListReqVo reqVo);

    /**
     * 创建
     */
    Long create(MenuReqVo accountReqVo);

    /**
     * 更新
     */
    void update(MenuReqVo accountReqVo);

    /**
     * 批量更新
     */
    void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo);

    /**
     * 批量移除
     */
    void removeBatch(IdListVo<Long> idListVo);


}
