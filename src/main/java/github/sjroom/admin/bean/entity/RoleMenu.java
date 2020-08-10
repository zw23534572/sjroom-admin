package github.sjroom.admin.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.sjroom.core.mybatis.core.BaseEntity;
import github.sjroom.core.mybatis.annotation.TableBId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * <B>说明：角色与菜单对应关系</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-04 20:44
 */
@TableName("sys_role_menu")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenu extends BaseEntity {

    /**
     * 代理主键
     */
    @TableBId
    private Long roleMenuId;
    /**
     * 角色ID(sys_role.role_id)
     */
    private Long roleId;
    /**
     * 菜单ID(sys_menu.menu_id)
     */
    private Long menuId;
}
