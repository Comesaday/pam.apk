package cn.comesaday.avt.business.apply.delegate;

import cn.comesaday.avt.business.apply.model.ApplyFormData;
import cn.comesaday.avt.business.apply.model.ApplyInfo;
import cn.comesaday.avt.business.apply.model.ApplyTrack;
import cn.comesaday.avt.business.apply.service.ApplyFormDataService;
import cn.comesaday.avt.business.apply.service.ApplyService;
import cn.comesaday.avt.business.apply.service.ApplyTrackService;
import cn.comesaday.avt.business.apply.vo.AskInfoVo;
import cn.comesaday.avt.business.matter.enums.MatterEnum;
import cn.comesaday.avt.business.matter.model.Matter;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.process.flow.constant.ProcessConstant;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.avt.process.water.model.Water;
import cn.comesaday.avt.process.water.service.WaterService;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * <描述> AskForDelegate
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-09 11:08
 */
@Component
public class ApplyDelegate extends AbstractApplyDelegate implements JavaDelegate, Serializable {

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(ApplyDelegate.class);

    @Autowired
    private ApplyService applyService;

    @Autowired
    private MatterService matterService;

    @Autowired
    private ApplyFormDataService applyFormDataService;

    @Autowired
    private WaterService waterService;

    @Autowired
    private ApplyTrackService applyTrackService;

    /**
     * <说明> 检查事项配置
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:11
     * @return void
     */
    @Override
    public void checkMatterSetting(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        String sessionId = variable.getSessionId();
        Water water = super.getProcessWater(sessionId);
        try {
            Long matterId = variable.getApplyInfo().getMatterId();
            Matter matter = matterService.getBasicMatter(matterId);
            // 检查事项配置
            matterService.checkMatterConfig(matter, MatterEnum.OPEN.getStatus(), Boolean.FALSE);
            // 将事项信息设置到流程变量
            variable.getApplyInfo().setMatter(matter);
            // 流程记录信息
            waterService.saveSuccess(water, variable, "检查事项配置成功");
            logger.info("检查事项配置成功,sessionId:{}", sessionId);
        } catch (Exception e) {
            waterService.saveFail(water, variable, "检查事项配置异常:" + e);
            logger.error("检查事项配置异常,sessionId:{},异常信息:{}" + e, sessionId);
            throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
        } finally {
            super.resetProcessVariable(delegateExecution, variable);
        }
    }


    /**
     * <说明> 检查申请信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:10
     * @return void
     */
    @Override
    public void checkUserFill(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        String sessionId = variable.getSessionId();
        Water water = super.getProcessWater(sessionId);
        try {
            // 检查申请信息
            applyService.checkAskInfo(variable.getApplyInfo());
            // 流程记录信息
            waterService.saveSuccess(water, variable, "检查申请信息成功");
            logger.info("检查申请信息成功,sessionId:{}", sessionId);
        } catch (Exception e) {
            waterService.saveFail(water, variable, "检查申请信息异常:" + e);
            logger.error("检查申请信息异常,sessionId:{},异常信息:{}" + e, sessionId);
            throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
        } finally {
            super.resetProcessVariable(delegateExecution, variable);
        }
    }


    /**
     * <说明> 初始化申请信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:11
     * @return void
     */
    @Override
    public void initAskInfo(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        String sessionId = variable.getSessionId();
        Water water = super.getProcessWater(sessionId);
        try {
            AskInfoVo askInfoVo = variable.getApplyInfo();
            // 保存申请表单信息
            List<ApplyFormData> formDatas = applyFormDataService.saveAll(askInfoVo.getAskInfos());
            // 初始化申请主表
            Matter matter = matterService.getBasicMatter(askInfoVo.getMatterId());
            ApplyInfo applyInfo = applyService.initAskMainInfo(askInfoVo, matter);
            // 表单数据&主表关联
            formDatas.stream().forEach(data -> data.setAskId(applyInfo.getId()));
            applyFormDataService.saveAll(formDatas);
            // 保存最新信息到流程变量
            askInfoVo.setAskId(applyInfo.getId());
            askInfoVo.setApplyInfo(applyInfo);
            askInfoVo.setAskInfos(formDatas);
            // 流程记录信息
            waterService.saveSuccess(water, variable,"初始化申请信息成功");
            logger.info("初始化申请信息成功,sessionId:{}", sessionId);
        } catch (Exception e) {
            waterService.saveFail(water, variable, "初始化申请信息异常:" + e);
            logger.error("初始化申请信息异常,sessionId:{},异常信息:{}" + e, sessionId);
            throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
        } finally {
            super.resetProcessVariable(delegateExecution, variable);
        }
    }


    /**
     * <说明> 初始化申请版本信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 15:04
     * @return void
     */
    @Override
    public void initAskTrack(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        String sessionId = variable.getSessionId();
        Water water = super.getProcessWater(sessionId);
        try {
            // 初始化审批版本数据
            ApplyInfo applyInfo = variable.getApplyInfo().getApplyInfo();
            ApplyTrack applyTrack = applyTrackService.initAskTrackInfo(applyInfo, delegateExecution);
            // 版本、主表数据关联
            applyService.createRelation(applyInfo, applyTrack.getId());
            // 保存审批记录
            variable.getRecords().add(applyTrack);
            // 流程记录信息
            waterService.saveSuccess(water, variable, "初始化版本信息成功");
            logger.info("初始化版本信息成功,sessionId:{}", sessionId);
        } catch (Exception e) {
            waterService.saveFail(water, variable, "初始化版本信息异常:" + e);
            logger.error("初始化版本信息异常,sessionId:{},异常信息:{}" + e, sessionId);
            throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
        } finally {
            super.resetProcessVariable(delegateExecution, variable);
        }
    }


    /**
     * <说明> 获取审核结果
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/23 14:04
     * @return java.lang.Boolean
     */
    @Override
    public Boolean approved(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        ApplyTrack applyTrack = variable.getRecords().stream().filter(record ->
            record.getLinkCode().equals(variable.getCurLinkCode())
        ).findFirst().get();
        return applyTrack.getAgree();
    }


    /**
     * <说明> execute
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/15 11:32
     * @return void
     */
    @Override
    public void execute(DelegateExecution delegateExecution) {

    }
}
