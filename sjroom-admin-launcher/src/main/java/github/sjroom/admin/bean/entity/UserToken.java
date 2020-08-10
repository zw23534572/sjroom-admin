package github.sjroom.admin.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.sjroom.core.mybatis.core.BaseEntity;
import github.sjroom.core.mybatis.annotation.TableBId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * <B>说明：系统用户Token</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-23 21:58
 */
@TableName("sys_user_token")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserToken extends BaseEntity {

    /**
     * 代理主键
     */
    @TableBId
    private Long userTokenId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 状态1:启用,2.禁用
     */
    private Integer status;
}
