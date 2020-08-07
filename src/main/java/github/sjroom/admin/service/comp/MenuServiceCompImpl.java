package github.sjroom.admin.service.comp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import github.sjroom.admin.bean.bo.RoleBo;
import github.sjroom.admin.bean.bo.RoleMenuBo;
import github.sjroom.admin.bean.entity.RoleMenu;
import github.sjroom.admin.bean.vo.RoleRespVo;
import github.sjroom.admin.code.SjroomErrorCode;
import github.sjroom.admin.service.IRoleMenuService;
import github.sjroom.admin.service.IRoleService;
import github.sjroom.core.mybatis.enums.StatusEnum;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.admin.bean.bo.MenuBo;
import github.sjroom.admin.bean.entity.Menu;
import github.sjroom.admin.bean.vo.MenuPageReqVo;
import github.sjroom.admin.bean.vo.MenuReqVo;
import github.sjroom.admin.bean.vo.MenuRespVo;
import github.sjroom.admin.service.IMenuService;
import github.sjroom.admin.service.IMenuServiceComp;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import github.sjroom.web.vo.IdListVo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 13:41
 */
@Slf4j
@Service
@Validated
public class MenuServiceCompImpl implements IMenuServiceComp {
    @Autowired
    private IMenuService menuService;

    @Override
    public MenuRespVo find(IdVo<Long> idVo) {
        return BeanUtil.copy(menuService.findByBId(idVo.getId()), MenuRespVo.class);
    }

    @Override
    public IPage page(MenuPageReqVo reqVo) {
        IPage<MenuBo> menuBoIPage = menuService.findPage(this.buildParams(reqVo));
        this.buildResult(menuBoIPage.getRecords());
        return menuBoIPage;
    }

    @Override
    public List<MenuRespVo> list(MenuReqVo reqVo) {
        List<MenuBo> menuBos = menuService.findList(BeanUtil.copy(reqVo, MenuBo.class));
        return BeanUtil.copy(menuBos, MenuRespVo.class);
    }

    @Override
    public Long create(MenuReqVo reqVo) {
        MenuBo menuBo = this.validatedParams(reqVo);
        Menu menu = this.fetchEntityData(menuBo);
        menuService.save(menu);
        return menu.getMenuId();
    }

    @Override
    public void update(MenuReqVo reqVo) {
        MenuBo menuBo = this.validatedParams(reqVo);
        Menu menu = this.fetchEntityData(menuBo);
        menu.setUpdatedAt(new Date());
        menuService.updateByBId(menu);
    }

    @Override
    public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
        List<Menu> menus = menuService.getBatchBIds(idStatusListVo.getIdList());
        if (CollectionUtil.isEmpty(menus)) {
            log.warn("MenuServiceCompImpl menuBos is empty");
            return;
        }
        menus.stream().forEach(menu -> {
            menu.setStatus(idStatusListVo.getStatus());
            menu.setUpdatedAt(new Date());
        });
        menuService.updateBatchByBIds(menus);
        return;
    }

    @Override
    public void removeBatch(IdListVo<Long> idListVo) {
        if (CollectionUtil.isEmpty(idListVo.getIdList())) {
            log.warn("MenuServiceCompImpl removeBatch idListVo is empty");
            return;
        }

        List<MenuBo> menus = menuService.findByBIds(idListVo.getIdList());
        if (CollectionUtil.isNotEmpty(menus)) {
            menus = menus.stream().filter(x -> x.getStatus() == StatusEnum.UN_ENABLE).collect(Collectors.toList());
            Assert.throwOnFalse(menus.size() > 0, SjroomErrorCode.PARAM_ERROR, "必须有一个未启用状态，才能进行");
        }

        menuService.removeBatchBIds(idListVo.getIdList());
    }

    /**
     * 构建参数
     *
     * @param reqVo
     * @return
     */
    private MenuBo buildParams(MenuPageReqVo reqVo) {
        MenuBo menuBo = BeanUtil.copy(reqVo, MenuBo.class);
        return menuBo;
    }

    /**
     * 构建返回参数
     *
     * @param menuBos
     */
    private void buildResult(List<MenuBo> menuBos) {
        if (CollectionUtil.isEmpty(menuBos)) {
            log.warn("MenuServiceCompImpl buildResult is empty");
            return;
        }
        // 实现业务逻辑
    }

    /**
     * 验证参数
     *
     * @param reqVo
     * @return
     */
    private MenuBo validatedParams(MenuReqVo reqVo) {
        MenuBo menuBo = BeanUtil.copy(reqVo, MenuBo.class);
        return menuBo;
    }

    /**
     * 获取实体数据
     *
     * @param menuBo
     * @return
     */
    private Menu fetchEntityData(MenuBo menuBo) {
        Menu menu = BeanUtil.copy(menuBo, Menu.class);
        return menu;
    }
}
