package github.sjroom.admin.dao.comp;

import github.sjroom.admin.dao.IUserDaoComp;
import github.sjroom.admin.dao.IUserDao;
import github.sjroom.admin.bean.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-02 16:44
 */
@Service
@Slf4j
public class UserDaoCompImpl implements IUserDaoComp {

    @Autowired
    private IUserDao userDao;

}
