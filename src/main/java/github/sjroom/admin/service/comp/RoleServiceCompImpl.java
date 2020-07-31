package github.sjroom.admin.service.comp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.code.SjroomErrorCode;
import github.sjroom.core.mybatis.enums.StatusEnum;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.admin.bean.bo.RoleBo;
import github.sjroom.admin.bean.entity.Role;
import github.sjroom.admin.bean.vo.RolePageReqVo;
import github.sjroom.admin.bean.vo.RoleReqVo;
import github.sjroom.admin.bean.vo.RoleRespVo;
import github.sjroom.admin.service.IRoleService;
import github.sjroom.admin.service.IRoleServiceComp;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import github.sjroom.web.vo.IdListVo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:11
 */
@Slf4j
@Service
@Validated
public class RoleServiceCompImpl implements IRoleServiceComp {
	@Autowired
	private IRoleService roleService;

	@Override
	public RoleRespVo find(IdVo<Long> idVo) {
		RoleBo roleBo = roleService.findByBId(idVo.getId());
		return BeanUtil.copy(roleBo, RoleRespVo.class);
	}

	@Override
	public IPage page(RolePageReqVo reqVo) {
		IPage<RoleBo> roleBoIPage = roleService.findPage(this.buildParams(reqVo));
		this.buildResult(roleBoIPage.getRecords());
		return roleBoIPage;
	}

	@Override
	public List<RoleRespVo> list(RoleReqVo reqVo) {
		List<RoleBo> roleBos = roleService.findList(BeanUtil.copy(reqVo, RoleBo.class));
		return BeanUtil.copy(roleBos, RoleRespVo.class);
	}

	@Override
	public Long create(RoleReqVo reqVo) {
		RoleBo roleBo = this.validatedParams(reqVo);
		Role role = this.fetchEntityData(roleBo);
		roleService.save(role);
		return role.getRoleId();
	}

	@Override
	public void update(RoleReqVo reqVo) {
		RoleBo roleBo = this.validatedParams(reqVo);
		Role role = this.fetchEntityData(roleBo);
		role.setUpdatedAt(new Date());
		roleService.updateByBId(role);
	}

	@Override
	public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
		List<Role> roles = roleService.getBatchBIds(idStatusListVo.getIdList());
		if (CollectionUtil.isEmpty(roles)) {
			log.warn("RoleServiceCompImpl roleBos is empty");
			return;
		}
		roles.stream().forEach(role -> {
			role.setStatus(idStatusListVo.getStatus());
			role.setUpdatedAt(new Date());
		});
		roleService.updateBatchByBIds(roles);
		return;
	}

	@Override
	public void removeBatch(IdListVo<Long> idListVo) {
		if (CollectionUtil.isEmpty(idListVo.getIdList())) {
			log.warn("RoleServiceCompImpl removeBatch idListVo is empty");
			return;
		}
	
		List<RoleBo> roles = roleService.findByBIds(idListVo.getIdList());
		if (CollectionUtil.isNotEmpty(roles)) {
			roles = roles.stream().filter(x -> x.getStatus() == StatusEnum.UN_ENABLE).collect(Collectors.toList());
			Assert.throwOnFalse(roles.size() > 0, SjroomErrorCode.PARAM_ERROR, "必须有一个未启用状态，才能进行");
		}
	
		roleService.removeBatchBIds(idListVo.getIdList());
	}
	
	/**
	 * 构建参数
	 *
	 * @param reqVo
	 * @return
	 */
	private RoleBo buildParams(RolePageReqVo reqVo) {
		RoleBo roleBo = BeanUtil.copy(reqVo, RoleBo.class);
		return roleBo;
	}

	/**
	 * 构建返回参数
	 *
	 * @param roleBos
	 */
	private void buildResult(List<RoleBo> roleBos) {
		if (CollectionUtil.isEmpty(roleBos)) {
			log.warn("RoleServiceCompImpl buildResult is empty");
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
	private RoleBo validatedParams(RoleReqVo reqVo) {
		RoleBo roleBo = BeanUtil.copy(reqVo, RoleBo.class);
		return roleBo;
	}

	/**
	 * 获取实体数据
	 *
	 * @param roleBo
	 * @return
	 */
	private Role fetchEntityData(RoleBo roleBo) {
		Role role = BeanUtil.copy(roleBo, Role.class);
		return role;
	}
}
