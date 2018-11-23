package com.github.sjroom.domain.entity;


import com.github.sjroom.common.jdbc.annotation.TableField;
import com.github.sjroom.common.jdbc.annotation.TableId;
import com.github.sjroom.common.jdbc.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_menu")
public class SysRoleMenu {

    @TableId
    private Long id;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private Long menuId;

}
