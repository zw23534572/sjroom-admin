package github.sjroom.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.bean.bo.DictBo;
import github.sjroom.admin.bean.entity.Dict;
import github.sjroom.admin.dao.IDictDao;
import github.sjroom.admin.service.IDictService;
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
 * @date 2020-07-20 15:17
 */
@Slf4j
@Service
@Validated
public class DictServiceImpl extends BaseServiceImpl<IDictDao, Dict> implements IDictService {

    @Autowired
    IDictDao iDictDao;

    @Override
    public DictBo findByBId(Long bid) {
        Dict dict = super.getByBId(bid);
        return BeanUtil.copy(dict, DictBo.class);
    }

    @Override
    public List<DictBo> findByBIds(Set<Long> dictIds) {
        List<Dict> dicts = super.getBatchBIds(dictIds);
        return BeanUtil.copy(dicts, DictBo.class);
    }

    @Override
    public List<DictBo> findList(DictBo dictBo) {
        List<Dict> dicts = super.list(this.query(dictBo));
        return BeanUtil.copy(dicts, DictBo.class);
    }

    @Override
    public Map<Integer, String> fillFieldName(Set<String> dictCodes) {
        DictBo dictBo = new DictBo();
        dictBo.setDictCodes(dictCodes);
        List<DictBo> dictBos = this.findList(dictBo);
        if (CollectionUtil.isEmpty(dictBos)) {
            log.warn("DictServiceImpl find dictBos is empty");
            return Collections.emptyMap();
        }
        return dictBos.stream().collect(Collectors.toMap(DictBo::getDictValue, DictBo::getDictText));
    }

    @Override
    public IPage<DictBo> findPage(DictBo model) {
        IPage<Dict> modelPage = super.page(PageUtil.toPage(model), this.query(model));
        return PageUtil.toPage(modelPage, DictBo.class);
    }

    private LambdaQueryWrapper<Dict> query(DictBo model) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<Dict>();
        wrapper.eq(ObjectUtil.isNotNull(model.getDictId()), Dict::getDictId, model.getDictId());
        wrapper.eq(StringUtil.isNotBlank(model.getDictCode()), Dict::getDictCode, model.getDictCode());
        wrapper.in(CollectionUtil.isNotEmpty(model.getDictCodes()), Dict::getDictCode, model.getDictCodes());
        wrapper.eq(StringUtil.isNotBlank(model.getDictCodeDesc()), Dict::getDictCodeDesc, model.getDictCodeDesc());
        wrapper.eq(ObjectUtil.isNotNull(model.getDictValue()), Dict::getDictValue, model.getDictValue());
        wrapper.eq(StringUtil.isNotBlank(model.getDictText()), Dict::getDictText, model.getDictText());
        wrapper.eq(ObjectUtil.isNotNull(model.getStatus()), Dict::getStatus, model.getStatus());
        return wrapper;
    }
}
