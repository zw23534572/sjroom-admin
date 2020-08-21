package github.sjroom.admin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import github.sjroom.core.mybatis.page.PageReqParam;

/**
 * 订单分页请求模型
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePageReqVo extends PageReqParam {

    // 业务按需添加分页参数
    @ApiModelProperty("角色名称")
    private String roleName;
}
