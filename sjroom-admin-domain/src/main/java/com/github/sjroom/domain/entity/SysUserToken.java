package com.github.sjroom.domain.entity;


import com.github.sjroom.common.jdbc.annotation.TableField;
import com.github.sjroom.common.jdbc.annotation.TableId;
import com.github.sjroom.common.jdbc.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_token")
public class SysUserToken {

    @TableId
    private Long id;

    @TableField("user_id")
    private Long userId;

    /**
     * token
     */
    @TableField("token")
    private String token;

    @TableField("session_id")
    private String sessionId;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    private Date expireTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

}
