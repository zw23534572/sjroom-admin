package github.sjroom.admin.dao.comp;

import github.sjroom.admin.dao.IUserTokenDaoComp;
import github.sjroom.admin.dao.IUserTokenDao;
import github.sjroom.admin.bean.entity.UserToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <B>说明：系统用户Token</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-23 21:58
 */
@Service
@Slf4j
public class UserTokenDaoCompImpl implements IUserTokenDaoComp {

    @Autowired
    private IUserTokenDao userTokenDao;

}
