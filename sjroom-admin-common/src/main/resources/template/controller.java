package ${config.controllerPackage};

import org.springframework.beans.factory.annotation.Autowired;

import ${config.basePackage}.common.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ${config.basePackage}.common.validator.ValidatorUtils;
import ${config.basePackage}.common.response.PageResult;
import ${config.requestEntityPackage}.${upperModelName}Request;
import ${config.entityPackage}.${upperModelName};
import ${config.servicePackage}.${upperModelName}Service;
import ${config.basePackage}.service.SysRoleService;
import ${config.basePackage}.base.BaseController;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-03-10 18:47
 */
@RestController
@RequestMapping("${lowerModelName}")
public class ${upperModelName}Controller extends BaseController {

    @Autowired
    ${upperModelName}Service ${lowerModelName}Service;
    @Autowired
    SysRoleService sysRoleService;

    /**
     * GET: 页面-查询列表
     */
    @RequestMapping("/list")
    public PageResult list(${upperModelName}Request pageRequest) {
        //只有超级管理员，才能查看所有管理员列表
        if (!sysRoleService.isAdminRole(getUserId())) {
            pageRequest.setCreateUser(getCreateUser());
        }
        return ${lowerModelName}Service.selectPage(pageRequest);
    }

    /**
     * GET: 用户信息
     */
    @RequestMapping("/info/{id}")
    public ${upperModelName} info(@PathVariable("id") Long id) {
        return ${lowerModelName}Service.selectById(id);
    }

    /**
     * POST: 接口-新增
     */
    @RequestMapping("/insert")
    @RequiresPermissions("sys:${lowerModelName}:insert")
    public boolean save(${upperModelName}Request ${lowerModelName}Request) {
        ValidatorUtils.validateEntity(${lowerModelName}Request);
        return ${lowerModelName}Service.insertOrUpdate(${lowerModelName}Request);
    }

    /**
     * POST: 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:${lowerModelName}:update")
    public boolean update(${upperModelName}Request ${lowerModelName}Request) {
        ValidatorUtils.validateEntity(${lowerModelName}Request);
        return ${lowerModelName}Service.insertOrUpdate(${lowerModelName}Request);
    }

    /**
     * POST: 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:${lowerModelName}:delete")
    public boolean delete(long id) {
        return ${lowerModelName}Service.deleteById(id);
    }
}
