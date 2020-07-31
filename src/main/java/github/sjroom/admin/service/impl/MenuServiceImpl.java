package github.sjroom.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.MenuBo;
import github.sjroom.admin.bean.entity.Menu;
import github.sjroom.admin.dao.IMenuDao;
import github.sjroom.admin.service.IMenuService;
import github.sjroom.core.mybatis.page.PageUtil;
import github.sjroom.core.mybatis.service.impl.BaseServiceImpl;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * <B>说明：服务实现</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 13:41
 */
@Slf4j
@Service
@Validated
public class MenuServiceImpl extends BaseServiceImpl<IMenuDao, Menu> implements IMenuService {

	@Override
	public MenuBo findByBId(Long bid) {
		Menu menu = super.getByBId(bid);
		return BeanUtil.copy(menu, MenuBo.class);
	}

	@Override
	public List<MenuBo> findByBIds(Set<Long> menuIds) {
		List<Menu> menus = super.getBatchBIds(menuIds);
		return BeanUtil.copy(menus, MenuBo.class);
	}

	@Override
	public List<MenuBo> findList(MenuBo menuBo) {
		List<Menu> menus = super.list(this.query(menuBo));
		return BeanUtil.copy(menus, MenuBo.class);
	}

	@Override
	public Map<Long, MenuBo> findMap(MenuBo menuBo) {
		List<MenuBo> menuBos = this.findList(menuBo);
		if (CollectionUtil.isEmpty(menuBos)) {
			log.warn("MenuServiceImpl find menuBos is empty");
			return Collections.emptyMap();
		}
		return menuBos.stream().collect(Collectors.toMap(MenuBo::getMenuId, Function.identity()));
	}

	@Override
	public IPage<MenuBo> findPage(MenuBo model) {
		IPage<Menu> modelPage = super.page(PageUtil.toPage(model), this.query(model));
		return PageUtil.toPage(modelPage, MenuBo.class);
	}

	@Override
	public Map<Long, String> fillFieldName(Set<Long> bIds) {
		List<MenuBo> menuBos = this.findByBIds(bIds);
		if (CollectionUtil.isEmpty(menuBos)) {
			log.warn("MenuServiceImpl find menuBos is empty");
			return Collections.emptyMap();
		}
		return menuBos.stream().collect(Collectors.toMap(MenuBo::getMenuId, MenuBo::getMenuName));
	}
	
	private LambdaQueryWrapper<Menu> query(MenuBo model) {
	    LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<Menu>();
			wrapper.eq(ObjectUtil.isNotNull(model.getMenuId()), Menu::getMenuId, model.getMenuId());
			wrapper.eq(ObjectUtil.isNotNull(model.getParentId()), Menu::getParentId, model.getParentId());
			wrapper.eq(StringUtil.isNotBlank(model.getMenuName()), Menu::getMenuName, model.getMenuName());
			wrapper.eq(StringUtil.isNotBlank(model.getMenuCode()), Menu::getMenuCode, model.getMenuCode());
			wrapper.eq(StringUtil.isNotBlank(model.getUrl()), Menu::getUrl, model.getUrl());
			wrapper.eq(StringUtil.isNotBlank(model.getPerms()), Menu::getPerms, model.getPerms());
			wrapper.eq(ObjectUtil.isNotNull(model.getType()), Menu::getType, model.getType());
			wrapper.eq(StringUtil.isNotBlank(model.getIcon()), Menu::getIcon, model.getIcon());
			wrapper.eq(ObjectUtil.isNotNull(model.getOrderNum()), Menu::getOrderNum, model.getOrderNum());
			wrapper.eq(ObjectUtil.isNotNull(model.getStatus()), Menu::getStatus, model.getStatus());
		return wrapper;
	}

}
