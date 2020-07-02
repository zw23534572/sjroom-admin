package github.sjroom.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.core.mybatis.page.PageUtil;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.core.utils.ObjectUtil;
import github.sjroom.core.utils.StringUtil;
import github.sjroom.admin.bean.bo.AccountBo;
import github.sjroom.admin.bean.entity.Account;
import github.sjroom.admin.dao.IAccountDao;
import github.sjroom.admin.service.IAccountService;
import github.sjroom.core.mybatis.service.impl.BaseServiceImpl;
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
 * @version 1.0.0
 * @since 2019-12-16 14:14
 */
@Slf4j
@Service
@Validated
public class AccountServiceImpl extends BaseServiceImpl<IAccountDao, Account> implements IAccountService {

	@Autowired
	IAccountDao iAccountDao;

	@Override
	public AccountBo findByBId(Long bid) {
		Account account = super.getByBId(bid);
		return BeanUtil.copy(account, AccountBo.class);
	}

	@Override
	public List<AccountBo> findByBIds(Set<Long> accountIds) {
		List<Account> accounts = super.getBatchBIds(accountIds);
		return BeanUtil.copy(accounts, AccountBo.class);
	}

	@Override
	public List<AccountBo> findList(AccountBo accountBo) {
		List<Account> accounts = super.list(this.query(accountBo));
		return BeanUtil.copy(accounts, AccountBo.class);
	}

	@Override
	public Map<Long, AccountBo> findMap(AccountBo accountBo) {
		List<AccountBo> accountBos = this.findList(accountBo);
		if (CollectionUtil.isEmpty(accountBos)) {
			log.warn("AccountServiceImpl find accountBos is empty");
			return Collections.emptyMap();
		}
		return accountBos.stream().collect(Collectors.toMap(AccountBo::getAccountId, Function.identity()));
	}

	@Override
	public IPage<AccountBo> findPage(AccountBo model) {
		IPage<Account> modelPage = iAccountDao.findPage(PageUtil.toPage(model), model);
		return PageUtil.toPage(modelPage, AccountBo.class);
	}

	private LambdaQueryWrapper<Account> query(AccountBo model) {
	    LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<Account>();
			wrapper.eq(ObjectUtil.isNotNull(model.getAccountId()), Account::getAccountId, model.getAccountId());
			wrapper.eq(ObjectUtil.isNotNull(model.getAccountType()), Account::getAccountType, model.getAccountType());
			wrapper.eq(StringUtil.isNotBlank(model.getMobile()), Account::getMobile, model.getMobile());
			wrapper.eq(StringUtil.isNotBlank(model.getEmail()), Account::getEmail, model.getEmail());
			wrapper.eq(StringUtil.isNotBlank(model.getLoginName()), Account::getLoginName, model.getLoginName());
			wrapper.eq(StringUtil.isNotBlank(model.getPassword()), Account::getPassword, model.getPassword());
			wrapper.eq(StringUtil.isNotBlank(model.getSalt()), Account::getSalt, model.getSalt());
			wrapper.eq(StringUtil.isNotBlank(model.getRealName()), Account::getRealName, model.getRealName());
			wrapper.eq(StringUtil.isNotBlank(model.getAvatar()), Account::getAvatar, model.getAvatar());
			wrapper.eq(ObjectUtil.isNotNull(model.getLanguage()), Account::getLanguage, model.getLanguage());
			wrapper.eq(ObjectUtil.isNotNull(model.getLengthUnit()), Account::getLengthUnit, model.getLengthUnit());
			wrapper.eq(ObjectUtil.isNotNull(model.getVolumeUnit()), Account::getVolumeUnit, model.getVolumeUnit());
			wrapper.eq(ObjectUtil.isNotNull(model.getStatus()), Account::getStatus, model.getStatus());
			wrapper.eq(ObjectUtil.isNotNull(model.getDistribution()), Account::getDistribution, model.getDistribution());
			wrapper.eq(ObjectUtil.isNotNull(model.getLanded()), Account::getLanded, model.getLanded());
			wrapper.eq(ObjectUtil.isNotNull(model.getLoginStatus()), Account::getLoginStatus, model.getLoginStatus());
			wrapper.eq(ObjectUtil.isNotNull(model.getLastLoginTime()), Account::getLastLoginTime, model.getLastLoginTime());
		return wrapper;
	}
}
