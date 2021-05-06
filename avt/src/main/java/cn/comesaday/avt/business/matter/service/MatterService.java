package cn.comesaday.avt.business.matter.service;

import cn.comesaday.avt.business.dict.model.Dict;
import cn.comesaday.avt.business.dict.service.DictService;
import cn.comesaday.avt.business.matter.enums.MatterEnum;
import cn.comesaday.avt.business.matter.model.Matter;
import cn.comesaday.avt.business.matter.model.MatterFieldSetting;
import cn.comesaday.avt.business.matter.model.MatterUserSetting;
import cn.comesaday.avt.business.matter.vo.MatterResponse;
import cn.comesaday.avt.business.matter.vo.MatterSettingRequest;
import cn.comesaday.avt.process.flow.handler.FlowHandler;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.exception.PamException;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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
    private DictService dictService;

    @Autowired
    private MatterFieldSettingService matterFieldSettingService;

    @Autowired
    private MatterUserSettingService matterUserSettingService;

    @Autowired
    private FlowHandler defaultFlowHandler;

    /**
     * <说明> 事项表单配置
     * @param settingVo MatterSettingVo
     * @author ChenWei
     * @date 2021/4/15 14:05
     * @return void
     */
    public void setting(MatterSettingRequest settingVo) throws Exception {
        // 检查事项状态
        Long matterId = settingVo.getMatterId();
        Matter matter = this.getBasicMatter(matterId);
        this.checkMatterConfig(matter, MatterEnum.SAVE.getStatus(), false);

        List<Long> elementIds = settingVo.getElementIds();
        if (!CollectionUtils.isEmpty(elementIds)) {
            MatterFieldSetting setting = new MatterFieldSetting();
            setting.setMatterId(matterId);
            List<MatterFieldSetting> settingList = elementIds.stream().map(elementId -> {
                return this.createMatterField(setting, elementId);
            }).collect(Collectors.toList());
            matterFieldSettingService.saveAll(settingList);
        }
    }

    /**
     * <说明> 生成事项表单配置信息
     * @param setting MatterFieldSetting
     * @param elementId 数据字典id
     * @author ChenWei
     * @date 2021/4/19 20:05
     * @return cn.comesaday.avt.business.matter.model.MatterFieldSetting
     */
    private MatterFieldSetting createMatterField(MatterFieldSetting setting, Long elementId) {
        setting.setDictId(elementId);
        MatterFieldSetting set = matterFieldSettingService.findOne(setting);
        setting.setId(null != set ? set.getId() : null);
        Dict dict = dictService.findOne(elementId);
        setting.setDictCode(dict.getCode());
        setting.setDictName(dict.getName());
        setting.setStyle(dict.getStyle());
        return setting;
    }

    /**
     * <说明> 移除事项表单配置
     * @param settingVo MatterSettingVo
     * @author ChenWei
     * @date 2021/4/15 14:25
     * @return void
     */
    public void removeSetting(MatterSettingRequest settingVo) {
        // 字段配置id
        List<Long> elementIds = settingVo.getElementIds();
        // 为空不配置
        if (CollectionUtils.isEmpty(elementIds)) {
            return;
        }
        for (Long settingId : elementIds) {
            matterFieldSettingService.deleteById(settingId);
        }
    }

    /**
     * <说明> 检查事项配置并创建流程
     * @param matterId
     * @author ChenWei
     * @date 2021/4/19 19:34
     * @return org.activiti.engine.repository.Model
     */
    public Model createMatterProcess(Long matterId) throws Exception {
        // 事项是否创建
        Matter matter = this.getBasicMatter(matterId);
        this.checkMatterConfig(matter, MatterEnum.DEFINED.getStatus(), Boolean.TRUE);
        // 创建新流程
        Model model = defaultFlowHandler.createModel(matter.getCode(),
                matter.getName(), matter.getRemark(), NumConstant.I1);
        // 保存流程定义信息
        matter.setModelId(model.getId());
        matter.setStatus(MatterEnum.DEFINED.getStatus());
        this.save(matter);
        return model;
    }

    /**
     * <说明> 部署事项流程
     * @param matterId 事项id
     * @author ChenWei
     * @date 2021/4/19 19:39
     * @return org.activiti.engine.repository.Deployment
     */
    public Deployment deployMatterProcess(Long matterId) throws Exception {
        // 检查事项状态
        Matter matter = this.getBasicMatter(matterId);
        this.checkMatterConfig(matter, MatterEnum.DEPLOY.getStatus(), Boolean.TRUE);
        // 流程部署
        Deployment deployment = defaultFlowHandler.deploymentModel(matter.getModelId());
        // 保存流程部署信息
        matter.setDeployId(deployment.getId());
        matter.setStatus(MatterEnum.DEPLOY.getStatus());
        this.save(matter);
        return deployment;
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

    /**
     * <说明> 获取事项配置的所有人
     * @param matterId 事项id
     * @author ChenWei
     * @date 2021/4/15 14:31
     * @return java.util.List<cn.comesaday.avt.business.matter.model.MatterUserSetting>
     */
    public List<MatterUserSetting> getMatterUsers(Long matterId) {
        return matterUserSettingService.findAllByProperty("matterId", matterId);
    }

    /**
     * <说明> 获取事项环节审批人
     * @param matterId 事项ID
     * @param actId 事项环节
     * @author ChenWei
     * @date 2021/4/15 14:32
     * @return java.util.List<cn.comesaday.avt.business.matter.model.MatterUserSetting>
     */
    public List<MatterUserSetting> getMatterLinkUsers(Long matterId, String actId) {
        MatterUserSetting setting = new MatterUserSetting();
        setting.setMatterId(matterId);
        setting.setLinkCode(actId);
        return matterUserSettingService.findAll(setting);
    }

    /**
     * <说明> 事项是否存在
     * @param matterId 事项id
     * @author ChenWei
     * @date 2021/4/1 16:44
     * @return cn.comesaday.avt.business.matter.model.Matter
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
     * @return cn.comesaday.avt.business.matter.vo.MatterVo
     */
    public MatterResponse getMatterInfo(Long matterId) throws PamException {
        Matter matter = this.getBasicMatter(matterId);
        List<MatterFieldSetting> settings = matterFieldSettingService
                .findAllByProperty("matterId", matterId);
        MatterResponse matterResponse = new MatterResponse();
        matterResponse.setMatter(matter);
        matterResponse.setElements(settings);
        return matterResponse;
    }
}
