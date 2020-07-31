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
 * @date 2020-07-24 13:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuPageReqVo extends PageReqParam {

    // 业务按需添加分页参数
}
