package github.sjroom.admin.dao.comp;

import github.sjroom.admin.dao.IUserRoleDaoComp;
import github.sjroom.admin.dao.IUserRoleDao;
import github.sjroom.admin.bean.entity.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <B>说明：用户与角色对应关系</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:10
 */
@Service
@Slf4j
public class UserRoleDaoCompImpl implements IUserRoleDaoComp {

    @Autowired
    private IUserRoleDao userRoleDao;

}
