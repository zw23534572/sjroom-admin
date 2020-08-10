package github.sjroom.admin.bean.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;

/**
 * <B>说明：数据字典表</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-20 15:17
 */
@Data
public class DictRespVo  {

	@ApiModelProperty("业务主键")
	private Long dictId;

	@ApiModelProperty("字段code码,标识为一组字典集合")
	private String dictCode;

	@ApiModelProperty("字段code描述")
	private String dictCodeDesc;

	@ApiModelProperty("状态值: 如:1")
	private Integer dictValue;

	@ApiModelProperty("状态名称")
	private String dictText;

	@ApiModelProperty("状态: 0.禁用,1:启用;")
	private Integer status;
}
