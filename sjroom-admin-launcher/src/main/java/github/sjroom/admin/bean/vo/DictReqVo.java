package github.sjroom.admin.bean.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
/**
 * <B>说明：数据字典表</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-23 09:28
 */
@Data
public class DictReqVo  {

    @ApiModelProperty("字段code码,标识为一组字典集合")
	@NotBlank
    private String dictCode;

    @ApiModelProperty("字段code描述")
	@NotBlank
    private String dictCodeDesc;

    @ApiModelProperty("状态值: 如:1")
	@NotNull
    private Integer dictValue;

    @ApiModelProperty("状态名称")
	@NotBlank
    private String dictText;
}
