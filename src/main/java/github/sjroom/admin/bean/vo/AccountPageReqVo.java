package github.sjroom.admin.bean.vo;

import github.sjroom.core.mybatis.page.PageReqParam;
import github.sjroom.web.validation.annotation.Range;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 订单分页请求模型
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-06-15 16:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountPageReqVo extends PageReqParam {

	// 业务按需添加分页参数
	@Range(format = "[\"aa\",\"bb\"]")
	String page;
}
