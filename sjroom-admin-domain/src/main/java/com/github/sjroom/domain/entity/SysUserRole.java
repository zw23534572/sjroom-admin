package com.github.sjroom.domain.entity;

import com.github.sjroom.jdbc.annotation.TableField;
import com.github.sjroom.jdbc.annotation.TableId;
import com.github.sjroom.jdbc.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_role")
public class SysUserRole {

    @TableId
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private Long roleId;

}
