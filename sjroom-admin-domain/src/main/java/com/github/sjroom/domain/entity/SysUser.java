package com.github.sjroom.domain.entity;

import com.github.sjroom.jdbc.annotation.TableField;
import com.github.sjroom.jdbc.annotation.TableId;
import com.github.sjroom.jdbc.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user")
public class SysUser implements Cloneable {

    @TableId
    private Long id;

    /**
     * 账号
     */
    @TableField("account")
    private String account;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 密码 加盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 图片url
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 职位
     */
    @TableField("position")
    private String position;

    /**
     * 部门
     */
    @TableField("department")
    private String department;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField("create_user")
    private String createUser;

    /**
     * 创建人
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField("update_user")
    private String updateUser;

    /**
     * yn: 1有效 ，0无效
     */
    @TableField("yn")
    private Byte yn;

    /**
     * 用户状态
     */
    @TableField("status")
    private Byte status;

    @Override
    public SysUser clone() {
        try {
            return (SysUser) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SysUser() {
    }

    public SysUser(Long id) {
        this.id = id;
    }

    public SysUser(String account) {
        this.account = account;
    }

    public SysUser(Long id, String userName, String account) {
        this.id = id;
        this.userName = userName;
        this.account = account;
    }
}
