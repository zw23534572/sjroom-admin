package github.sjroom.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.UserTokenBo;
import github.sjroom.admin.bean.entity.UserToken;
import github.sjroom.admin.dao.IUserTokenDao;
import github.sjroom.admin.service.IUserTokenService;
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
 * @date 2020-07-23 21:58
 */
@Slf4j
@Service
@Validated
public class UserTokenServiceImpl extends BaseServiceImpl<IUserTokenDao, UserToken> implements IUserTokenService {

    @Override
    public UserTokenBo findByBId(Long bid) {
        UserToken userToken = super.getByBId(bid);
        return BeanUtil.copy(userToken, UserTokenBo.class);
    }

    @Override
    public List<UserTokenBo> findByBIds(Set<Long> userTokenIds) {
        List<UserToken> userTokens = super.getBatchBIds(userTokenIds);
        return BeanUtil.copy(userTokens, UserTokenBo.class);
    }

    @Override
    public List<UserTokenBo> findList(UserTokenBo userTokenBo) {
        List<UserToken> userTokens = super.list(this.query(userTokenBo));
        return BeanUtil.copy(userTokens, UserTokenBo.class);
    }

    @Override
    public Map<Long, UserTokenBo> findMap(UserTokenBo userTokenBo) {
        List<UserTokenBo> userTokenBos = this.findList(userTokenBo);
        if (CollectionUtil.isEmpty(userTokenBos)) {
            log.warn("UserTokenServiceImpl find userTokenBos is empty");
            return Collections.emptyMap();
        }
        return userTokenBos.stream().collect(Collectors.toMap(UserTokenBo::getUserTokenId, Function.identity()));
    }

    @Override
    public IPage<UserTokenBo> findPage(UserTokenBo model) {
        IPage<UserToken> modelPage = super.page(PageUtil.toPage(model), this.query(model));
        return PageUtil.toPage(modelPage, UserTokenBo.class);
    }

    private LambdaQueryWrapper<UserToken> query(UserTokenBo model) {
        LambdaQueryWrapper<UserToken> wrapper = new LambdaQueryWrapper<UserToken>();
        wrapper.eq(ObjectUtil.isNotNull(model.getUserTokenId()), UserToken::getUserTokenId, model.getUserTokenId());
        wrapper.eq(ObjectUtil.isNotNull(model.getUserId()), UserToken::getUserId, model.getUserId());
        wrapper.eq(StringUtil.isNotBlank(model.getToken()), UserToken::getToken, model.getToken());
        wrapper.eq(ObjectUtil.isNotNull(model.getExpireTime()), UserToken::getExpireTime, model.getExpireTime());
        wrapper.eq(ObjectUtil.isNotNull(model.getStatus()), UserToken::getStatus, model.getStatus());
        return wrapper;
    }

}
