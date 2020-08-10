package github.sjroom.admin.bean.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import github.sjroom.core.mybatis.page.PageReqParam;

/**
 * 订单分页请求模型
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-03 14:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRolePageReqVo extends PageReqParam {

    // 业务按需添加分页参数
}
