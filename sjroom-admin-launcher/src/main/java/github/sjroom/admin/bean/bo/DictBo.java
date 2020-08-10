package github.sjroom.admin.bean.bo;

import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.Set;

import github.sjroom.core.mybatis.page.PageReqParam;
import lombok.EqualsAndHashCode;

/**
 * <B>说明：数据字典表</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-20 15:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictBo extends PageReqParam {
	/**
	 * 代理主键
	 */
	private Long id;
	/**
	 * 业务主键
	 */
	private Long dictId;
	/**
	 * 业务主键
	 */
	private Set<Long> dictIds;
	/**
	 * 字段code码,标识为一组字典集合
	 */
	private String dictCode;
	/**
	 * 字段code码,标识为一组字典集合
	 */
	private Set<String> dictCodes;
	/**
	 * 字段code描述
	 */
	private String dictCodeDesc;
	/**
	 * 状态值: 如:1
	 */
	private Integer dictValue;
	/**
	 * 状态名称
	 */
	private String dictText;
	/**
	 * 状态: 0.禁用,1:启用;
	 */
	private Integer status;
	/**
	 * 创建人
	 */
	private Long createdBy;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新人
	 */
	private Long updatedBy;
	/**
	 * 更新时间
	 */
	private Date updatedAt;
}
