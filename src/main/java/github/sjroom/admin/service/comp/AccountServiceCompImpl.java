package github.sjroom.admin.service.comp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.admin.bean.bo.AccountBo;
import github.sjroom.admin.bean.entity.Account;
import github.sjroom.admin.bean.vo.AccountPageReqVo;
import github.sjroom.admin.bean.vo.AccountReqVo;
import github.sjroom.admin.bean.vo.AccountRespVo;
import github.sjroom.admin.service.IAccountService;
import github.sjroom.admin.service.IAccountServiceComp;
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
 * @date 2019-12-16 14:14
 */
@Slf4j
@Service
@Validated
public class AccountServiceCompImpl implements IAccountServiceComp {
	@Autowired
	private IAccountService accountService;

	@Override
	public AccountRespVo find(IdVo<Long> idVo) {
		AccountBo accountBo = accountService.findByBId(idVo.getId());
		return BeanUtil.copy(accountBo, AccountRespVo.class);
	}

	@Override
	public IPage page(AccountPageReqVo reqVo) {
		IPage<AccountBo> accountBoIPage = accountService.findPage(this.buildParams(reqVo));
		this.buildResult(accountBoIPage.getRecords());
		return accountBoIPage;
	}

	@Override
	public List<AccountRespVo> list(AccountReqVo reqVo) {
		List<AccountBo> accountBos = accountService.findList(BeanUtil.copy(reqVo, AccountBo.class));
		return BeanUtil.copy(accountBos, AccountRespVo.class);
	}

	@Override
	public Long create(AccountReqVo reqVo) {
		AccountBo accountBo = this.validatedParams(reqVo);
		Account account = this.fetchEntityData(accountBo);
		accountService.save(account);
		return account.getAccountId();
	}

	@Override
	public void update(AccountReqVo reqVo) {
		AccountBo accountBo = this.validatedParams(reqVo);
		Account account = this.fetchEntityData(accountBo);
		account.setUpdatedAt(new Date());
		accountService.updateByBId(account);
	}

	@Override
	public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
		List<Account> accounts = accountService.getBatchBIds(idStatusListVo.getIdList());
		if (CollectionUtil.isEmpty(accounts)) {
			log.warn("AccountServiceCompImpl accountBos is empty");
			return;
		}
		accounts.stream().forEach(account -> {
			account.setStatus(idStatusListVo.getStatus());
			account.setUpdatedAt(new Date());
		});
		accountService.updateBatchByBIds(accounts);
		return;
	}

	/**
	 * 构建参数
	 *
	 * @param reqVo
	 * @return
	 */
	private AccountBo buildParams(AccountPageReqVo reqVo) {
		AccountBo accountBo = BeanUtil.copy(reqVo, AccountBo.class);
		return accountBo;
	}

	/**
	 * 构建返回参数
	 *
	 * @param accountBos
	 */
	private void buildResult(List<AccountBo> accountBos) {
		if (CollectionUtil.isEmpty(accountBos)) {
			log.warn("AccountServiceCompImpl buildResult is empty");
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
	private AccountBo validatedParams(AccountReqVo reqVo) {
		AccountBo accountBo = BeanUtil.copy(reqVo, AccountBo.class);
		return accountBo;
	}

	/**
	 * 获取实体数据
	 *
	 * @param accountBo
	 * @return
	 */
	private Account fetchEntityData(AccountBo accountBo) {
		Account account = BeanUtil.copy(accountBo, Account.class);
		return account;
	}
}
