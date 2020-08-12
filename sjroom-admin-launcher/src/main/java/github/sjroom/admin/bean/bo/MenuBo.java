package github.sjroom.admin.bean.bo;

import github.sjroom.admin.bean.vo.RoleRespVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

import github.sjroom.core.mybatis.page.PageReqParam;
import lombok.EqualsAndHashCode;

/**
 * <B>说明：菜单管理</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 13:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuBo extends PageReqParam {
    /**
     *
     */
    private Long id;
    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;
    private Set<Long> parentIds;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单URL
     */
    private String url;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private Integer type;
    private Set<Integer> types;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 状态1:启用,0.禁用
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

    @ApiModelProperty("角色列表")
    private List<RoleBo> roles;

    private List<MenuBo> children;
}
