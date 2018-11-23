package ${config.requestEntityPackage};

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;
import com.github.credit.common.request.PageRequest;
import java.util.Date;
<% for(var item in dbTableFieldInfoList) {%>
<% if(strutil.startWith(item.comment,"@status")) { %>
import java.util.List;
<% } %>
<% } %>
/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ${upperModelName}Request extends PageRequest {
<% for(var item in dbTableFieldInfoList) {
        var isIgore=false;
        for(var igoreitem in config.ignoreFieldArr){
            if(item.column==igoreitem){
                isIgore = true;
                break;
            }
        }
        if(isIgore) continue;
    %>

    /**
     * ${item.comment}
     */
    <% if (item.isNull==false) {%>
    @NotBlank(message = "${item.column}不能为空")
    <% }%>
    private ${item.propertyType} ${item.property};
    <% if(strutil.startWith(item.comment,"@status")) { %>

    /**
     * ${item.comment},集合查询
     */
    private List<${item.propertyType}> ${item.property}List;
    <% }%>
<% }%>
}
