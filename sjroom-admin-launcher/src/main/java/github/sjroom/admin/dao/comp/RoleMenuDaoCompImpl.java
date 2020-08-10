package github.sjroom.admin.dao.comp;

import github.sjroom.admin.dao.IRoleMenuDaoComp;
import github.sjroom.admin.dao.IRoleMenuDao;
import github.sjroom.admin.bean.entity.RoleMenu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <B>说明：角色与菜单对应关系</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-08-04 20:44
 */
@Service
@Slf4j
public class RoleMenuDaoCompImpl implements IRoleMenuDaoComp {

    @Autowired
    private IRoleMenuDao roleMenuDao;

}
