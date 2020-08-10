package github.sjroom.admin.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import github.sjroom.core.mybatis.core.BaseEntity;
import github.sjroom.core.mybatis.annotation.TableBId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

/**
 * <B>说明：数据字典表</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-20 15:17
 */
@TableName("sys_dict")
@Data
@EqualsAndHashCode(callSuper = true)
public class Dict extends BaseEntity {

    /**
     * 业务主键
     */
    @TableBId
    private Long dictId;
    /**
     * 字段code码,标识为一组字典集合
     */
    private String dictCode;
    /**
     * 字段code描述
     */
    private String dictCodeDesc;
    /**
     * 状态值: 如:1
     */
    private Integer dictValue;
    /**
     * 状态名称
     */
    private String dictText;
    /**
     * 状态: 0.禁用,1:启用;
     */
    private Integer status;
}
