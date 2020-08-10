package github.sjroom.admin.bean.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * <B>说明：菜单管理</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 13:41
 */
@Data
public class MenuReqVo {

    @ApiModelProperty("菜单ID")
    @NotNull
    private Long menuId;

    @ApiModelProperty("父菜单ID，一级菜单为0")
    @NotNull
    private Long parentId;

    @ApiModelProperty("菜单名称")
    @NotBlank
    private String menuName;

    @ApiModelProperty("菜单URL")
    private String url;

    @ApiModelProperty("类型   0：目录   1：菜单   2：按钮")
    private Integer type;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("排序")
    private Integer orderNum;

    @ApiModelProperty("状态1:启用,0.禁用")
    private Integer status;
}
