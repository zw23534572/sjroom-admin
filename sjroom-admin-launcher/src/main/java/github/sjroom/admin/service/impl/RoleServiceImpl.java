package github.sjroom.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.RoleBo;
import github.sjroom.admin.bean.entity.Role;
import github.sjroom.admin.dao.IRoleDao;
import github.sjroom.admin.service.IRoleService;
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
 * @date 2020-07-24 11:11
 */
@Slf4j
@Service
@Validated
public class RoleServiceImpl extends BaseServiceImpl<IRoleDao, Role> implements IRoleService {

	@Override
	public RoleBo findByBId(Long bid) {
		Role role = super.getByBId(bid);
		return BeanUtil.copy(role, RoleBo.class);
	}

	@Override
	public List<RoleBo> findByBIds(Set<Long> roleIds) {
		List<Role> roles = super.getBatchBIds(roleIds);
		return BeanUtil.copy(roles, RoleBo.class);
	}

	@Override
	public List<RoleBo> findList(RoleBo roleBo) {
		List<Role> roles = super.list(this.query(roleBo));
		return BeanUtil.copy(roles, RoleBo.class);
	}

	@Override
	public Map<Long, RoleBo> findMap(RoleBo roleBo) {
		List<RoleBo> roleBos = this.findList(roleBo);
		if (CollectionUtil.isEmpty(roleBos)) {
			log.warn("RoleServiceImpl find roleBos is empty");
			return Collections.emptyMap();
		}
		return roleBos.stream().collect(Collectors.toMap(RoleBo::getRoleId, Function.identity()));
	}

	@Override
	public IPage<RoleBo> findPage(RoleBo model) {
		IPage<Role> modelPage = super.page(PageUtil.toPage(model), this.query(model));
		return PageUtil.toPage(modelPage, RoleBo.class);
	}

	@Override
	public Map<Long, String> fillFieldName(Set<Long> bIds) {
		List<RoleBo> roleBos = this.findByBIds(bIds);
		if (CollectionUtil.isEmpty(roleBos)) {
			log.warn("RoleServiceImpl find roleBos is empty");
			return Collections.emptyMap();
		}
		return roleBos.stream().collect(Collectors.toMap(RoleBo::getRoleId, RoleBo::getRoleName));
	}
	
	private LambdaQueryWrapper<Role> query(RoleBo model) {
	    LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<Role>();
			wrapper.eq(ObjectUtil.isNotNull(model.getRoleId()), Role::getRoleId, model.getRoleId());
			wrapper.likeRight(StringUtil.isNotBlank(model.getRoleName()), Role::getRoleName, model.getRoleName());
			wrapper.eq(StringUtil.isNotBlank(model.getRemark()), Role::getRemark, model.getRemark());
			wrapper.eq(ObjectUtil.isNotNull(model.getStatus()), Role::getStatus, model.getStatus());
		return wrapper;
	}

}
