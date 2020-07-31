package github.sjroom.admin.service.comp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.code.SjroomErrorCode;
import github.sjroom.core.mybatis.enums.StatusEnum;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.admin.bean.bo.UserBo;
import github.sjroom.admin.bean.entity.User;
import github.sjroom.admin.bean.vo.UserPageReqVo;
import github.sjroom.admin.bean.vo.UserReqVo;
import github.sjroom.admin.bean.vo.UserRespVo;
import github.sjroom.admin.service.IUserService;
import github.sjroom.admin.service.IUserServiceComp;
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
 * @date 2020-07-24 11:51
 */
@Slf4j
@Service
@Validated
public class UserServiceCompImpl implements IUserServiceComp {
	@Autowired
	private IUserService userService;

	@Override
	public UserRespVo find(IdVo<Long> idVo) {
		UserBo userBo = userService.findByBId(idVo.getId());
		return BeanUtil.copy(userBo, UserRespVo.class);
	}

	@Override
	public IPage page(UserPageReqVo reqVo) {
		IPage<UserBo> userBoIPage = userService.findPage(this.buildParams(reqVo));
		this.buildResult(userBoIPage.getRecords());
		return userBoIPage;
	}

	@Override
	public List<UserRespVo> list(UserReqVo reqVo) {
		List<UserBo> userBos = userService.findList(BeanUtil.copy(reqVo, UserBo.class));
		return BeanUtil.copy(userBos, UserRespVo.class);
	}

	@Override
	public Long create(UserReqVo reqVo) {
		UserBo userBo = this.validatedParams(reqVo);
		User user = this.fetchEntityData(userBo);
		userService.save(user);
		return user.getUserId();
	}

	@Override
	public void update(UserReqVo reqVo) {
		UserBo userBo = this.validatedParams(reqVo);
		User user = this.fetchEntityData(userBo);
		user.setUpdatedAt(new Date());
		userService.updateByBId(user);
	}

	@Override
	public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
		List<User> users = userService.getBatchBIds(idStatusListVo.getIdList());
		if (CollectionUtil.isEmpty(users)) {
			log.warn("UserServiceCompImpl userBos is empty");
			return;
		}
		users.stream().forEach(user -> {
			user.setStatus(idStatusListVo.getStatus());
			user.setUpdatedAt(new Date());
		});
		userService.updateBatchByBIds(users);
		return;
	}

	@Override
	public void removeBatch(IdListVo<Long> idListVo) {
		if (CollectionUtil.isEmpty(idListVo.getIdList())) {
			log.warn("UserServiceCompImpl removeBatch idListVo is empty");
			return;
		}
	
		List<UserBo> users = userService.findByBIds(idListVo.getIdList());
		if (CollectionUtil.isNotEmpty(users)) {
			users = users.stream().filter(x -> x.getStatus() == StatusEnum.UN_ENABLE).collect(Collectors.toList());
			Assert.throwOnFalse(users.size() > 0, SjroomErrorCode.PARAM_ERROR, "必须有一个未启用状态，才能进行");
		}
	
		userService.removeBatchBIds(idListVo.getIdList());
	}
	
	/**
	 * 构建参数
	 *
	 * @param reqVo
	 * @return
	 */
	private UserBo buildParams(UserPageReqVo reqVo) {
		UserBo userBo = BeanUtil.copy(reqVo, UserBo.class);
		return userBo;
	}

	/**
	 * 构建返回参数
	 *
	 * @param userBos
	 */
	private void buildResult(List<UserBo> userBos) {
		if (CollectionUtil.isEmpty(userBos)) {
			log.warn("UserServiceCompImpl buildResult is empty");
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
	private UserBo validatedParams(UserReqVo reqVo) {
		UserBo userBo = BeanUtil.copy(reqVo, UserBo.class);
		return userBo;
	}

	/**
	 * 获取实体数据
	 *
	 * @param userBo
	 * @return
	 */
	private User fetchEntityData(UserBo userBo) {
		User user = BeanUtil.copy(userBo, User.class);
		return user;
	}
}
