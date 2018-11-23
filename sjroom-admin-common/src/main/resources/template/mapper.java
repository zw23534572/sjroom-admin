package ${config.mapperPackage};

import com.github.credit.common.jdbc.core.BaseMapper;
import ${config.entityPackage}.${upperModelName};
import ${config.requestEntityPackage}.${upperModelName}Request;
import ${config.responseEntityPackage}.${upperModelName}Response;
import java.util.List;

/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
public interface ${upperModelName}Mapper extends BaseMapper<${upperModelName}> {
    List<${upperModelName}Response> selectPage(${upperModelName}Request ${lowerModelName}Request);
}
