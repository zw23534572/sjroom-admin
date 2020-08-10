package github.sjroom.admin.dao.comp;

import github.sjroom.admin.dao.IDictDaoComp;
import github.sjroom.admin.dao.IDictDao;
import github.sjroom.admin.bean.entity.Dict;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <B>说明：数据字典表</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-20 15:17
 */
@Service
@Slf4j
public class DictDaoCompImpl implements IDictDaoComp {

    @Autowired
    private IDictDao dictDao;

}
