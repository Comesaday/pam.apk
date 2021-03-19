package cn.comesaday.avt.matter.service;

import cn.comesaday.avt.matter.manager.MatterManager;
import cn.comesaday.avt.matter.manager.MatterSettingManager;
import cn.comesaday.avt.matter.model.Matter;
import cn.comesaday.avt.matter.model.MatterSetting;
import cn.comesaday.avt.matter.vo.MatterSettingVo;
import cn.comesaday.avt.setting.dict.manager.DictManager;
import cn.comesaday.avt.setting.dict.model.Dict;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <描述> MatterService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-18 16:23
 */
@Transactional
@Service
public class MatterService extends BaseService<Matter, Long> {

    @Autowired
    private MatterManager matterManager;

    @Autowired
    private DictManager dictManager;

    @Autowired
    private MatterSettingManager matterSettingManager;

    public List<Matter> findAll(Matter matter) {
        Example<Matter> example = Example.of(matter);
        return matterManager.findAll(example);
    }

    public Matter findOne(Long matterId) {
        return matterManager.findById(matterId).orElse(new Matter());
    }

    public void saveOrUpdate(Matter matter) {
        matterManager.save(matter);
    }

    public Matter findByModelId(String modelId) {
        Matter matter = new Matter();
        matter.setModelId(modelId);
        Example<Matter> example = Example.of(matter);
        return matterManager.findOne(example).orElse(new Matter());
    }

    public void setting(MatterSettingVo settingVo) {
        Long matterId = settingVo.getMatterId();
        List<Long> elementIds = settingVo.getElementIds();
        // 为空不配置
        if (CollectionUtils.isEmpty(elementIds)) {
            return;
        }
        MatterSetting setting = new MatterSetting();
        for (int index = NumConstant.I0; index < elementIds.size(); index++) {
            Long elementId = elementIds.get(index);
            setting.setMatterId(matterId);
            setting.setDictId(elementId);
            Example<MatterSetting> example = Example.of(setting);
            setting = matterSettingManager.findOne(example).orElse(new MatterSetting());
            Dict dict = dictManager.getOne(elementId);
            setting.setDictCode(dict.getCode());
            setting.setDictName(dict.getName());
            setting.setStyle(dict.getStyle());
            setting.setSort(index + NumConstant.I1);
            matterSettingManager.save(setting);
        }
    }

    public void removeSetting(MatterSettingVo settingVo) {
        Long matterId = settingVo.getMatterId();
        List<Long> elementIds = settingVo.getElementIds();
        // 为空不配置
        if (CollectionUtils.isEmpty(elementIds)) {
            return;
        }
        matterSettingManager.deleteAll();
    }
}
