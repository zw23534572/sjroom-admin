package github.sjroom.admin.service.comp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.code.SjroomErrorCode;
import github.sjroom.core.mybatis.enums.StatusEnum;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.admin.bean.bo.UserRoleBo;
import github.sjroom.admin.bean.entity.UserRole;
import github.sjroom.admin.bean.vo.UserRolePageReqVo;
import github.sjroom.admin.bean.vo.UserRoleReqVo;
import github.sjroom.admin.bean.vo.UserRoleRespVo;
import github.sjroom.admin.service.IUserRoleService;
import github.sjroom.admin.service.IUserRoleServiceComp;
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
 * @date 2020-08-03 14:22
 */
@Slf4j
@Service
@Validated
public class UserRoleServiceCompImpl implements IUserRoleServiceComp {
	@Autowired
	private IUserRoleService userRoleService;

	@Override
	public UserRoleRespVo find(IdVo<Long> idVo) {
		UserRoleBo userRoleBo = userRoleService.findByBId(idVo.getId());
		return BeanUtil.copy(userRoleBo, UserRoleRespVo.class);
	}

	@Override
	public IPage page(UserRolePageReqVo reqVo) {
		IPage<UserRoleBo> userRoleBoIPage = userRoleService.findPage(this.buildParams(reqVo));
		this.buildResult(userRoleBoIPage.getRecords());
		return userRoleBoIPage;
	}

	@Override
	public List<UserRoleRespVo> list(UserRoleReqVo reqVo) {
		List<UserRoleBo> userRoleBos = userRoleService.findList(BeanUtil.copy(reqVo, UserRoleBo.class));
		return BeanUtil.copy(userRoleBos, UserRoleRespVo.class);
	}

	@Override
	public Long create(UserRoleReqVo reqVo) {
		UserRoleBo userRoleBo = this.validatedParams(reqVo);
		UserRole userRole = this.fetchEntityData(userRoleBo);
		userRoleService.save(userRole);
		return userRole.getUserRoleId();
	}

	@Override
	public void update(UserRoleReqVo reqVo) {
		UserRoleBo userRoleBo = this.validatedParams(reqVo);
		UserRole userRole = this.fetchEntityData(userRoleBo);
		userRole.setUpdatedAt(new Date());
		userRoleService.updateByBId(userRole);
	}

	@Override
	public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
		List<UserRole> userRoles = userRoleService.getBatchBIds(idStatusListVo.getIdList());
		if (CollectionUtil.isEmpty(userRoles)) {
			log.warn("UserRoleServiceCompImpl userRoleBos is empty");
			return;
		}
		userRoles.stream().forEach(userRole -> {
			userRole.setUpdatedAt(new Date());
		});
		userRoleService.updateBatchByBIds(userRoles);
		return;
	}

	@Override
	public void removeBatch(IdListVo<Long> idListVo) {
		if (CollectionUtil.isEmpty(idListVo.getIdList())) {
			log.warn("UserRoleServiceCompImpl removeBatch idListVo is empty");
			return;
		}
	
		List<UserRoleBo> userRoles = userRoleService.findByBIds(idListVo.getIdList());
		if (CollectionUtil.isNotEmpty(userRoles)) {
			userRoles = userRoles.stream().filter(x -> x.getStatus() == StatusEnum.UN_ENABLE).collect(Collectors.toList());
			Assert.throwOnFalse(userRoles.size() > 0, SjroomErrorCode.PARAM_ERROR, "必须有一个未启用状态，才能进行");
		}
	
		userRoleService.removeBatchBIds(idListVo.getIdList());
	}
	
	/**
	 * 构建参数
	 *
	 * @param reqVo
	 * @return
	 */
	private UserRoleBo buildParams(UserRolePageReqVo reqVo) {
		UserRoleBo userRoleBo = BeanUtil.copy(reqVo, UserRoleBo.class);
		return userRoleBo;
	}

	/**
	 * 构建返回参数
	 *
	 * @param userRoleBos
	 */
	private void buildResult(List<UserRoleBo> userRoleBos) {
		if (CollectionUtil.isEmpty(userRoleBos)) {
			log.warn("UserRoleServiceCompImpl buildResult is empty");
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
	private UserRoleBo validatedParams(UserRoleReqVo reqVo) {
		UserRoleBo userRoleBo = BeanUtil.copy(reqVo, UserRoleBo.class);
		return userRoleBo;
	}

	/**
	 * 获取实体数据
	 *
	 * @param userRoleBo
	 * @return
	 */
	private UserRole fetchEntityData(UserRoleBo userRoleBo) {
		UserRole userRole = BeanUtil.copy(userRoleBo, UserRole.class);
		return userRole;
	}
}
