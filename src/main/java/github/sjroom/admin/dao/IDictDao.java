package github.sjroom.admin.dao;

import github.sjroom.admin.bean.bo.DictBo;
import github.sjroom.admin.bean.entity.Dict;
import github.sjroom.core.mybatis.mapper.IMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <B>说明：数据字典表</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-20 15:17
 */
@Mapper
public interface IDictDao extends IMapper<Dict> {

}
