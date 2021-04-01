package cn.comesaday.avt.matter.service;

import cn.comesaday.avt.frame.enums.MatterEnum;
import cn.comesaday.avt.matter.manager.MatterManager;
import cn.comesaday.avt.matter.model.Matter;
import cn.comesaday.avt.matter.model.MatterSetting;
import cn.comesaday.avt.matter.vo.MatterSettingVo;
import cn.comesaday.avt.matter.vo.MatterVo;
import cn.comesaday.avt.setting.dict.manager.DictManager;
import cn.comesaday.avt.setting.dict.model.Dict;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.exception.PamException;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    private MatterSettingService matterSettingService;

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
            setting = matterSettingService.findOne(example).orElse(new MatterSetting());
            Dict dict = dictManager.getOne(elementId);
            setting.setDictCode(dict.getCode());
            setting.setDictName(dict.getName());
            setting.setStyle(dict.getStyle());
            setting.setSort(index + NumConstant.I1);
            matterSettingService.save(setting);
        }
    }

    public void removeSetting(MatterSettingVo settingVo) {
        Long matterId = settingVo.getMatterId();
        List<Long> elementIds = settingVo.getElementIds();
        // 为空不配置
        if (CollectionUtils.isEmpty(elementIds)) {
            return;
        }
        matterSettingService.deleteAll();
    }

    /**
     * <说明> 事项是否存在
     * @param matterId 事项id
     * @author ChenWei
     * @date 2021/4/1 16:44
     * @return cn.comesaday.avt.matter.model.Matter
     */
    public Matter getBasicMatter(Long matterId) throws PamException {
        Matter matter = super.findOne(matterId);
        if (null == matter) {
            throw new PamException("事项不存在");
        }
        return matter;
    }

    /**
     * <说明> 查事项表单
     * @param matterId 事项id
     * @author ChenWei
     * @date 2021/3/29 19:39
     * @return cn.comesaday.avt.matter.vo.MatterVo
     */
    public MatterVo getMatterInfo(Long matterId) throws PamException {
        Matter matter = this.getBasicMatter(matterId);
        List<MatterSetting> settings = matterSettingService
                .findAllByProperty("matterId", matterId);
        MatterVo matterVo = new MatterVo();
        matterVo.setMatter(matter);
        matterVo.setElements(settings);
        return matterVo;
    }

    /**
     * <说明> 检查事项
     * @param matter Matter
     * @param status Integer
     * @author ChenWei
     * @date 2021/4/1 16:40
     * @return void
     */
    public void checkMatterConfig(Matter matter, Integer status, Boolean config) throws PamException {
        int matterStatus = matter.getStatus();
        String exception = "";
        if (config) {
            // 检查是否可配置
            if (status - matterStatus <= NumConstant.I0) {
                exception = "事项已" + MatterEnum.getCurStatus(status) + ",请勿重复操作";
                exception += ",严格按照此流程配置:保存->配置->定义->部署->发布";
            } else if (status - matterStatus != NumConstant.I1) {
                exception = "事项" + MatterEnum.getCurStatus(status)
                        + "不可操作," + "当前状态:已" + MatterEnum.getCurStatus(matterStatus);
                exception += ",严格按照此流程配置:保存->配置->定义->部署->发布";
            }
        } else {
            // 检查是否可申请
            if (status != matterStatus) {
                exception += "事项不可申请,当前状态:" + MatterEnum.getCurStatus(matterStatus);
            }
        }
        if (!StringUtils.isEmpty(exception)) {
            throw new PamException(exception);
        }
    }
}
