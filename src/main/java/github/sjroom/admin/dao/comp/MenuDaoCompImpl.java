package github.sjroom.admin.dao.comp;

import github.sjroom.admin.dao.IMenuDaoComp;
import github.sjroom.admin.dao.IMenuDao;
import github.sjroom.admin.bean.entity.Menu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <B>说明：菜单管理</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-24 13:41
 */
@Service
@Slf4j
public class MenuDaoCompImpl implements IMenuDaoComp {

    @Autowired
    private IMenuDao menuDao;

}
