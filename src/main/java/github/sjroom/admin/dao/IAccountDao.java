package github.sjroom.admin.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.AccountBo;
import github.sjroom.admin.bean.entity.Account;
import github.sjroom.core.mybatis.mapper.IMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-06-15 16:23
 */
@Mapper
public interface IAccountDao extends IMapper<Account> {

	/**
	 * 查询分页信息
	 *
	 * @param reqVo
	 * @return
	 */
	IPage<Account> findPage(IPage page, @Param(value = "model") AccountBo reqVo);
}
