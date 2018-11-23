package ${config.servicePackage};

import java.util.List;
import ${config.basePackage}.common.response.PageResult;
import ${config.entityPackage}.${upperModelName};
import ${config.requestEntityPackage}.${upperModelName}Request;
import ${config.responseEntityPackage}.${upperModelName}Response;

/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
public interface ${upperModelName}Service {
    /** 自动生成 start */
    ${upperModelName} selectById(Long id);
    ${upperModelName} selectOne(${upperModelName} t);
    List<${upperModelName}> selectList(${upperModelName} t);
    boolean insertOrUpdate(${upperModelName}Request ${lowerModelName}Request);
    boolean deleteById(Long id);
    PageResult<${upperModelName}Response> selectPage(${upperModelName}Request ${lowerModelName}Request);
<% for(var item in dbTableFieldInfoList) { %>
    <% if(strutil.startWith(item.comment,"@yn")) { %>
    /** @yn 注解生成 */
    boolean deleteLogicById(Long id);
    <%}%>
    <% if(strutil.startWith(item.comment,"@no")) { %>
    /** @no 注解生成 */
    ${upperModelName} selectByNo(String no);
    <%}%>
<%}%>
    /** 自动生成 end */
}
