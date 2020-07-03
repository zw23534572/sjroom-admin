package github.sjroom.admin.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.sjroom.core.mybatis.core.BaseEntity;
import github.sjroom.core.mybatis.annotation.TableBId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-02 16:44
 */
@TableName("sys_user")
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    /**
     * 账号
     */
    @TableBId
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码加盐
     */
    private String salt;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 0:禁用,1:正常
     */
    private Integer status;
}
