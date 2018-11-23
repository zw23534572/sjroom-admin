package ${config.responseEntityPackage};

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
<% for(var item in dbTableFieldInfoList) {%>
    <% if(strutil.startWith(item.comment,"@status")) { %>
import ${config.enumPackage}${upperModelName}${sputil.capitalize(item.property)}Enum;
    <% }%>
<% }%>


/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ${upperModelName}Response  {
<% for(var item in dbTableFieldInfoList) {%>
    /**
     * ${item.comment}
     */
    private ${item.propertyType} ${item.property};
    <% if(strutil.startWith(item.comment,"@status")) { %>

    /**
     * ${item.comment},状态描述
     */
    public String get${sputil.capitalize(item.property)}Desc(){
        return ${upperModelName}${sputil.capitalize(item.property)}Enum.getMsg(this.${item.property});
    }
    <% }%>
<% }%>
}
