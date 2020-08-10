package github.sjroom.admin.service.comp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import github.sjroom.admin.code.SjroomErrorCode;
import github.sjroom.core.mybatis.enums.StatusEnum;
import github.sjroom.core.exception.Assert;
import github.sjroom.core.utils.BeanUtil;
import github.sjroom.core.utils.CollectionUtil;
import github.sjroom.admin.bean.bo.DictBo;
import github.sjroom.admin.bean.entity.Dict;
import github.sjroom.admin.bean.vo.DictPageReqVo;
import github.sjroom.admin.bean.vo.DictReqVo;
import github.sjroom.admin.bean.vo.DictRespVo;
import github.sjroom.admin.service.IDictService;
import github.sjroom.admin.service.IDictServiceComp;
import github.sjroom.web.vo.IdStatusListVo;
import github.sjroom.web.vo.IdVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import github.sjroom.web.vo.IdListVo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2020-07-20 15:17
 */
@Slf4j
@Service
@Validated
public class DictServiceCompImpl implements IDictServiceComp {
    @Autowired
    private IDictService dictService;

    @Override
    public DictRespVo find(IdVo<Long> idVo) {
        DictBo dictBo = dictService.findByBId(idVo.getId());
        return BeanUtil.copy(dictBo, DictRespVo.class);
    }

    @Override
    public IPage page(DictPageReqVo reqVo) {
        IPage<DictBo> dictBoIPage = dictService.findPage(this.buildParams(reqVo));
        this.buildResult(dictBoIPage.getRecords());
        return dictBoIPage;
    }

    @Override
    public List<DictRespVo> list(DictPageReqVo reqVo) {
        List<DictBo> dictBos = dictService.findList(BeanUtil.copy(reqVo, DictBo.class));
        return BeanUtil.copy(dictBos, DictRespVo.class);
    }

    @Override
    public Long create(DictReqVo reqVo) {
        DictBo dictBo = this.validatedParams(reqVo);

        DictBo queryDictBo = new DictBo();
        queryDictBo.setDictCode(reqVo.getDictCode());
        queryDictBo.setDictValue(reqVo.getDictValue());
        Assert.throwOnFalse(CollectionUtil.isEmpty(dictService.findList(queryDictBo)),
                SjroomErrorCode.SYSTEM_ERROR_ADD_01, "dictCode与dictValue");

        Dict dict = this.fetchEntityData(dictBo);
        dictService.save(dict);
        return dict.getDictId();
    }

    @Override
    public void update(DictReqVo reqVo) {
        DictBo dictBo = this.validatedParams(reqVo);
        Dict dict = this.fetchEntityData(dictBo);
        dict.setUpdatedAt(new Date());
        dictService.updateByBId(dict);
    }

    @Override
    public void updateBatch(IdStatusListVo<Long, Integer> idStatusListVo) {
        List<Dict> dicts = dictService.getBatchBIds(idStatusListVo.getIdList());
        if (CollectionUtil.isEmpty(dicts)) {
            log.warn("DictServiceCompImpl dictBos is empty");
            return;
        }
        dicts.stream().forEach(dict -> {
            dict.setStatus(idStatusListVo.getStatus());
            dict.setUpdatedAt(new Date());
        });
        dictService.updateBatchByBIds(dicts);
        return;
    }

    @Override
    public void removeBatch(IdListVo<Long> idListVo) {
        if (CollectionUtil.isEmpty(idListVo.getIdList())) {
            log.warn("DictServiceCompImpl removeBatch idListVo is empty");
            return;
        }

        List<DictBo> dicts = dictService.findByBIds(idListVo.getIdList());
        if (CollectionUtil.isNotEmpty(dicts)) {
            dicts = dicts.stream().filter(x -> x.getStatus() == StatusEnum.UN_ENABLE).collect(Collectors.toList());
            Assert.throwOnFalse(dicts.size() > 0, SjroomErrorCode.PARAM_ERROR, "必须有一个未启用状态，才能进行");
        }

        dictService.removeBatchBIds(idListVo.getIdList());
    }

    /**
     * 构建参数
     *
     * @param reqVo
     * @return
     */
    private DictBo buildParams(DictPageReqVo reqVo) {
        DictBo dictBo = BeanUtil.copy(reqVo, DictBo.class);
        return dictBo;
    }

    /**
     * 构建返回参数
     *
     * @param dictBos
     */
    private void buildResult(List<DictBo> dictBos) {
        if (CollectionUtil.isEmpty(dictBos)) {
            log.warn("DictServiceCompImpl buildResult is empty");
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
    private DictBo validatedParams(DictReqVo reqVo) {
        DictBo dictBo = BeanUtil.copy(reqVo, DictBo.class);
        return dictBo;
    }

    /**
     * 获取实体数据
     *
     * @param dictBo
     * @return
     */
    private Dict fetchEntityData(DictBo dictBo) {
        Dict dict = BeanUtil.copy(dictBo, Dict.class);
        return dict;
    }
}
