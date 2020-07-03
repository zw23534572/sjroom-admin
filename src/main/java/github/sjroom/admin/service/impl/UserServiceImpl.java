package github.sjroom.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.UserBo;
import github.sjroom.admin.bean.entity.User;
import github.sjroom.admin.dao.IUserDao;
import github.sjroom.admin.service.IUserService;
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
 * @date 2020-07-02 16:44
 */
@Slf4j
@Service
@Validated
public class UserServiceImpl extends BaseServiceImpl<IUserDao, User> implements IUserService {

	@Autowired
	IUserDao iUserDao;

	@Override
	public UserBo findByBId(Long bid) {
		User user = super.getByBId(bid);
		return BeanUtil.copy(user, UserBo.class);
	}

	@Override
	public List<UserBo> findByBIds(Set<Long> userIds) {
		List<User> users = super.getBatchBIds(userIds);
		return BeanUtil.copy(users, UserBo.class);
	}

	@Override
	public List<UserBo> findList(UserBo userBo) {
		List<User> users = super.list(this.query(userBo));
		return BeanUtil.copy(users, UserBo.class);
	}

	@Override
	public Map<Long, UserBo> findMap(UserBo userBo) {
		List<UserBo> userBos = this.findList(userBo);
		if (CollectionUtil.isEmpty(userBos)) {
			log.warn("UserServiceImpl find userBos is empty");
			return Collections.emptyMap();
		}
		return userBos.stream().collect(Collectors.toMap(UserBo::getUserId, Function.identity()));
	}

	@Override
	public IPage<UserBo> findPage(UserBo model) {
		IPage<User> modelPage = super.page(PageUtil.toPage(model), this.query(model));
		return PageUtil.toPage(modelPage, UserBo.class);
	}

	private LambdaQueryWrapper<User> query(UserBo model) {
	    LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>();
			wrapper.eq(ObjectUtil.isNotNull(model.getUserId()), User::getUserId, model.getUserId());
			wrapper.eq(StringUtil.isNotBlank(model.getUserName()), User::getUserName, model.getUserName());
			wrapper.eq(StringUtil.isNotBlank(model.getPassword()), User::getPassword, model.getPassword());
			wrapper.eq(StringUtil.isNotBlank(model.getSalt()), User::getSalt, model.getSalt());
			wrapper.eq(StringUtil.isNotBlank(model.getEmail()), User::getEmail, model.getEmail());
			wrapper.eq(StringUtil.isNotBlank(model.getMobile()), User::getMobile, model.getMobile());
			wrapper.eq(ObjectUtil.isNotNull(model.getStatus()), User::getStatus, model.getStatus());
		return wrapper;
	}
}
