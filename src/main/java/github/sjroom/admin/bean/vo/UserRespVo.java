package github.sjroom.admin.bean.vo;

import github.sjroom.admin.code.DictCode;
import github.sjroom.admin.service.IDictService;
import github.sjroom.core.mybatis.annotation.FillFieldName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-02 16:44
 */
@Data
public class UserRespVo {

    @ApiModelProperty("账号")
    private Long userId;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("密码明文")
    private String passwordPlaintext;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String mobile;

    @ApiModelProperty("0:禁用,1:正常")
    @FillFieldName(invoke = IDictService.class, invokeArg = DictCode.STATUS)
    private Integer status;

    @ApiModelProperty("0:禁用,1:正常")
    @FillFieldName(invoke = IDictService.class, invokeArg = DictCode.LOGIN_STATUS)
    private Integer loginStatus;
}
