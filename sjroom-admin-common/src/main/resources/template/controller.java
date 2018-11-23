package ${config.controllerPackage};


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.github.credit.common.validator.ValidatorUtils;
import com.github.pagehelper.Page;

import ${config.basePackage}.common.annotation.SysLog;
import ${config.basePackage}.controller.AbstractController;
import ${config.requestEntityPackage}.${upperModelName}Request;
import ${config.responseEntityPackage}.${upperModelName}Response;
import ${config.entityPackage}.${upperModelName};
import ${config.servicePackage}.${upperModelName}Service;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-03-10 18:47
 */
@RestController
@RequestMapping("${lowerModelName}")
public class ${upperModelName}Controller extends AbstractController {

    @Autowired
    ${upperModelName}Service ${lowerModelName}Service;

    /**
     * 页面-查询列表
     */
    @RequestMapping("/list")
    public ModelAndView list(${upperModelName}Request ${lowerModelName}Request) {
        ModelAndView mv = render();
        Page<${upperModelName}Response> page = ${lowerModelName}Service.selectPage(${lowerModelName}Request);
        mv.addObject("page", page);
        mv.addObject("search", ${lowerModelName}Request);
        return mv;
    }

    /**
     * 页面-新增
     */
    @RequestMapping("/add")
    public ModelAndView add() {
        return render();
    }

    /**
     * 页面-编辑
     */
    @RequestMapping("/edit")
    public ModelAndView edit(Long id) {
        ModelAndView mv = render();
        ${upperModelName} ${lowerModelName} = ${lowerModelName}Service.selectById(id);
        mv.addObject("model", ${lowerModelName});
        return mv;
    }

    /**
     * 接口-新增
     */
    @SysLog("新增订单")
    @RequestMapping("/insert")
    @RequiresPermissions("sys:${lowerModelName}:insert")
    public boolean save(${upperModelName}Request ${lowerModelName}Request) {
        ValidatorUtils.validateEntity(${lowerModelName}Request);
        return ${lowerModelName}Service.insertOrUpdate(${lowerModelName}Request);
    }

    /**
     * 修改
     */
    @SysLog("修改订单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:${lowerModelName}:update")
    public boolean update(${upperModelName}Request ${lowerModelName}Request) {
        ValidatorUtils.validateEntity(${lowerModelName}Request);
        return ${lowerModelName}Service.insertOrUpdate(${lowerModelName}Request);
    }

    /**
     * 删除
     */
    @SysLog("删除订单")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:${lowerModelName}:delete")
    public boolean delete(long id) {
        return ${lowerModelName}Service.deleteById(id);
    }
}
