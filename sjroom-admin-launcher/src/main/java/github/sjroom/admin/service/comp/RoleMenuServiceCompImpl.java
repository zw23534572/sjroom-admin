package github.sjroom.admin.service.comp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.code.SjroomErrorCode;
import github.sjroom.core.mybatis.enums.StatusEnum;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.admin.bean.bo.RoleMenuBo;
import github.sjroom.admin.bean.entity.RoleMenu;
import github.sjroom.admin.bean.vo.RoleMenuReqVo;
import github.sjroom.admin.bean.vo.RoleMenuRespVo;
import github.sjroom.admin.service.IRoleMenuService;
import github.sjroom.admin.service.IRoleMenuServiceComp;
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
 * @date 2020-08-04 20:44
 */
@Slf4j
@Service
@Validated
public class RoleMenuServiceCompImpl implements IRoleMenuServiceComp {
	@Autowired
	private IRoleMenuService roleMenuService;

	@Override
	public RoleMenuRespVo find(IdVo<Long> idVo) {
		RoleMenuBo roleMenuBo = roleMenuService.findByBId(idVo.getId());
		return BeanUtil.copy(roleMenuBo, RoleMenuRespVo.class);
	}

	@Override
	public List<RoleMenuRespVo> list(RoleMenuReqVo reqVo) {
		List<RoleMenuBo> roleMenuBos = roleMenuService.findList(BeanUtil.copy(reqVo, RoleMenuBo.class));
		return BeanUtil.copy(roleMenuBos, RoleMenuRespVo.class);
	}

	@Override
	public Long create(RoleMenuReqVo reqVo) {
		RoleMenuBo roleMenuBo = this.validatedParams(reqVo);
		RoleMenu roleMenu = this.fetchEntityData(roleMenuBo);
		roleMenuService.save(roleMenu);
		return roleMenu.getRoleMenuId();
	}

	@Override
	public void update(RoleMenuReqVo reqVo) {
		RoleMenuBo roleMenuBo = this.validatedParams(reqVo);
		RoleMenu roleMenu = this.fetchEntityData(roleMenuBo);
		roleMenu.setUpdatedAt(new Date());
		roleMenuService.updateByBId(roleMenu);
	}

	@Override
	public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
		List<RoleMenu> roleMenus = roleMenuService.getBatchBIds(idStatusListVo.getIdList());
		if (CollectionUtil.isEmpty(roleMenus)) {
			log.warn("RoleMenuServiceCompImpl roleMenuBos is empty");
			return;
		}
		roleMenus.stream().forEach(roleMenu -> {
			roleMenu.setUpdatedAt(new Date());
		});
		roleMenuService.updateBatchByBIds(roleMenus);
		return;
	}

	@Override
	public void removeBatch(IdListVo<Long> idListVo) {
		if (CollectionUtil.isEmpty(idListVo.getIdList())) {
			log.warn("RoleMenuServiceCompImpl removeBatch idListVo is empty");
			return;
		}
	
		List<RoleMenuBo> roleMenus = roleMenuService.findByBIds(idListVo.getIdList());
		if (CollectionUtil.isNotEmpty(roleMenus)) {
			roleMenus = roleMenus.stream().filter(x -> x.getStatus() == StatusEnum.UN_ENABLE).collect(Collectors.toList());
			Assert.throwOnFalse(roleMenus.size() > 0, SjroomErrorCode.PARAM_ERROR, "必须有一个未启用状态，才能进行");
		}
	
		roleMenuService.removeBatchBIds(idListVo.getIdList());
	}
	
	/**
	 * 构建返回参数
	 *
	 * @param roleMenuBos
	 */
	private void buildResult(List<RoleMenuBo> roleMenuBos) {
		if (CollectionUtil.isEmpty(roleMenuBos)) {
			log.warn("RoleMenuServiceCompImpl buildResult is empty");
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
	private RoleMenuBo validatedParams(RoleMenuReqVo reqVo) {
		RoleMenuBo roleMenuBo = BeanUtil.copy(reqVo, RoleMenuBo.class);
		return roleMenuBo;
	}

	/**
	 * 获取实体数据
	 *
	 * @param roleMenuBo
	 * @return
	 */
	private RoleMenu fetchEntityData(RoleMenuBo roleMenuBo) {
		RoleMenu roleMenu = BeanUtil.copy(roleMenuBo, RoleMenu.class);
		return roleMenu;
	}
}
