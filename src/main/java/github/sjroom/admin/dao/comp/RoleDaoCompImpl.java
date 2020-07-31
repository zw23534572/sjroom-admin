package github.sjroom.admin.dao.comp;

import github.sjroom.admin.dao.IRoleDaoComp;
import github.sjroom.admin.dao.IRoleDao;
import github.sjroom.admin.bean.entity.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <B>说明：角色</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 11:11
 */
@Service
@Slf4j
public class RoleDaoCompImpl implements IRoleDaoComp {

    @Autowired
    private IRoleDao roleDao;

}
