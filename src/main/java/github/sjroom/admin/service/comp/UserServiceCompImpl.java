package github.sjroom.admin.service.comp;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

import java.util.Date;
import java.util.List;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-02 16:44
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
