package github.sjroom.admin.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import github.sjroom.core.mybatis.page.PageReqParam;

import javax.validation.constraints.NotBlank;

/**
 * 订单分页请求模型
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-20 15:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictPageReqVo extends PageReqParam {

    // 业务按需添加分页参数
    @ApiModelProperty("字段code码,标识为一组字典集合")
    private String dictCode;
}
