package github.sjroom.admin.bean.entity;

import github.sjroom.core.mybatis.annotation.FillFieldName;
import lombok.Data;


/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2019-12-16 14:14
 */
@Data
public class Dict {

	/**
	 * 字典ID
	 */
	private Integer dictId;
	/**
	 * 字典名称
	 */
	@FillFieldName
	private String dictName;
}
