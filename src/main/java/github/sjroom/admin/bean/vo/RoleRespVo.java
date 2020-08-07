package github.sjroom.admin.bean.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * <B>说明：角色</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:11
 */
@Data
public class RoleRespVo {

    @ApiModelProperty("代理主键")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态1:启用,0.禁用")
    private Integer status;

    @ApiModelProperty("菜单集合列表")
    private List<MenuReqVo> menus;
}
