package ${config.serviceImplPackage};
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.credit.common.AbstractService;
import com.github.credit.common.YNStatus;
import ${config.mapperPackage}.${upperModelName}Mapper;
import ${config.requestEntityPackage}.${upperModelName}Request;
import ${config.responseEntityPackage}.${upperModelName}Response;
import ${config.entityPackage}.${upperModelName};
import ${config.servicePackage}.${upperModelName}Service;
import ${config.basePackage}.common.response.PageResult;
import ${config.basePackage}.common.PageResultUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * <B>说明：${dbTableInfo.comment}</B><BR>
 *
 * @author ${config.author}
 * @version 1.0.0.
 * @date ${date}
 */
@Service
public class ${upperModelName}ServiceImpl extends AbstractService implements ${upperModelName}Service {

    @Autowired
    ${upperModelName}Mapper ${lowerModelName}Mapper;

    @Override
    public ${upperModelName} selectById(Long id) {
        logger.debug("${upperModelName}ServiceImpl selectById:{}", id);
        return ${lowerModelName}Mapper.selectById(id);
    }

    @Override
    public ${upperModelName} selectOne(${upperModelName} ${lowerModelName}) {
        logger.debug("${upperModelName}ServiceImpl selectOne:{}", ${lowerModelName});
        return ${lowerModelName}Mapper.selectOne(${lowerModelName});
    }

    @Override
    public List<${upperModelName}> selectList(${upperModelName} ${lowerModelName}) {
        logger.debug("${upperModelName}ServiceImpl selectList:{}", ${lowerModelName});
        return ${lowerModelName}Mapper.selectList(${lowerModelName});
    }

    @Override
    public boolean insertOrUpdate(${upperModelName}Request ${lowerModelName}Request) {
        logger.debug("${upperModelName}ServiceImpl saveOrUpdate ${lowerModelName}Request:{}", ${lowerModelName}Request);
        int result;
        ${upperModelName} ${lowerModelName} = new ${upperModelName}();
        BeanUtils.copyProperties(${lowerModelName}Request, ${lowerModelName});
        logger.debug("${upperModelName}ServiceImpl saveOrUpdate ${lowerModelName}:{}", ${lowerModelName});
        if (${lowerModelName}.getId() == null) {
            result = ${lowerModelName}Mapper.insert(${lowerModelName});
        } else {
            result = ${lowerModelName}Mapper.updateById(${lowerModelName});
        }
        return result > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        logger.debug("${upperModelName}ServiceImpl deleteById:{}", id);
        int result = ${lowerModelName}Mapper.deleteById(id);
        return result > 0;
    }

    @Override
    public PageResult<${upperModelName}Response> selectPage(${upperModelName}Request ${lowerModelName}Request) {
        logger.debug("${upperModelName}ServiceImpl selectPage:{}", ${lowerModelName}Request);
        Page<${upperModelName}Response> page = PageHelper.startPage(${lowerModelName}Request.getPageNo(), ${lowerModelName}Request.getPageSize());
        ${lowerModelName}Mapper.selectPage(${lowerModelName}Request);
        return PageResultUtils.convert(page);
    }

<% for(var item in dbTableFieldInfoList) {%>
<% if(strutil.startWith(item.comment,"@no")) { %>
    @Override
    public ${upperModelName} selectByNo(String no) {
        logger.debug("${upperModelName}ServiceImpl selectByNo:{}", no);
        ${upperModelName} ${lowerModelName} = new ${upperModelName}();
        ${lowerModelName}.setOrderNo(no);
        return ${lowerModelName}Mapper.selectOne(${lowerModelName});
    }
<% }%>

<% if(strutil.startWith(item.comment,"@yn")) { %>
    @Override
    public boolean deleteLogicById(Long id) {
        logger.debug("${upperModelName}ServiceImpl deleteLogicById:{}", id);
        ${upperModelName} ${lowerModelName} = new ${upperModelName}();
        ${lowerModelName}.setId(id);
        ${lowerModelName}.setYn(YNStatus.NO.getCode());
        int result = ${lowerModelName}Mapper.updateById(${lowerModelName});
        return result > 0;
    }
<% }%>
<% }%>

}

