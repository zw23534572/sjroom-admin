package com.github.sjroom.domain.vo;

import com.github.sjroom.common.validator.group.AddGroup;
import com.github.sjroom.common.validator.group.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;
import java.util.List;

@Data
public class SysUserVO {
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = AddGroup.class)
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Email(message = "邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
    private String email;

    /**
     * 微信ID
     */
    private String weixinId;

    /**
     * 钉钉ID
     */
    private String dingdingId;

    /**
     * 钉钉头像
     */
    private String avatar;

    /**
     * 钉钉职位
     */
    private String position;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private Byte yn;

    /**
     * 角色ID列表
     */
    private List<Long> roleIdList;

    /**
     * 创建者ID
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

}
