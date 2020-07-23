package github.sjroom.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.DictBo;
import github.sjroom.admin.bean.entity.Dict;
import github.sjroom.core.mybatis.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-20 15:17
 */
public interface IDictService extends BaseService<Dict> {

    /**
     * 查看
     *
     * @param dictId 业务主键Id
     * @return
     */
    DictBo findByBId(Long dictId);

    /**
     * 列表
     *
     * @param dictIds 业务主键Id
     * @return
     */
    List<DictBo> findByBIds(Set<Long> dictIds);

    /**
     * 列表
     *
     * @param dictBo 业务model
     * @return
     */
    List<DictBo> findList(DictBo dictBo);

    /**
     * 列表
     *
     * @param dictCodes code码
     * @return 键值对
     */
    Map<Integer, String> fillFieldName(Set<String> dictCodes);

    /**
     * 分页
     *
     * @param dictBo
     * @return
     */
    IPage<DictBo> findPage(DictBo dictBo);
}
