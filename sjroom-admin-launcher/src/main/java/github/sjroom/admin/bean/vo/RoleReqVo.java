package github.sjroom.admin.bean.vo;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
/**
 * <B>说明：角色</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:11
 */
@Data
public class RoleReqVo  {

    @ApiModelProperty("角色Id")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态1:启用,0.禁用")
    private Integer status;

    @ApiModelProperty("菜单集合列表")
    private Set<Long> menuIdList;
}
