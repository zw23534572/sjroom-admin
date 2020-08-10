package github.sjroom.admin.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.sjroom.core.mybatis.core.BaseEntity;
import github.sjroom.core.mybatis.annotation.TableBId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * <B>说明：用户与角色对应关系</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-03 14:22
 */
@TableName("sys_user_role")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseEntity {

    /**
     * 代理主键
     */
    @TableBId
    private Long userRoleId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;
}
