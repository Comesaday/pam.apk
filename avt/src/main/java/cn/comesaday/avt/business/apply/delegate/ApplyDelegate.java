package cn.comesaday.avt.business.apply.delegate;

import cn.comesaday.avt.business.apply.model.ApplyInfo;
import cn.comesaday.avt.business.apply.model.ApplyTrack;
import cn.comesaday.avt.business.apply.service.ApplyService;
import cn.comesaday.avt.business.apply.service.ApplyTrackService;
import cn.comesaday.avt.business.apply.vo.UserApply;
import cn.comesaday.avt.business.matter.enums.MatterEnum;
import cn.comesaday.avt.business.matter.model.Matter;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.process.flow.constant.FlowConstant;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.coe.core.basic.exception.PamException;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * <描述> AskForDelegate
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-09 11:08
 */
@Component
public class ApplyDelegate extends AbstractApplyDelegate implements Serializable {

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(ApplyDelegate.class);

    @Autowired
    private ApplyService applyService;

    @Autowired
    private MatterService matterService;

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
    public void check(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        String sessionId = variable.getSessionId();
        try {
            Long matterId = variable.getUserApply().getMatterId();
            Matter matter = matterService.getBasicMatter(matterId);
            // 检查事项配置
            matterService.checkMatterConfig(matter, MatterEnum.OPEN.getStatus(), Boolean.FALSE);
            // 将事项信息设置到流程变量
            variable.getUserApply().setMatter(matter);
            // 检查申请信息
            applyService.checkAskInfo(variable.getUserApply());
            // 流程记录信息
            logger.info("检查事项配置通过,sessionId:{}", sessionId);
        } catch (PamException e) {
            variable.getCheckInfo().setNotChecked("检查不通过:" + e.getMessage());
            logger.error("检查不通过,sessionId:{},原因:{}", sessionId, e.getMessage());
        } finally {
            super.resetVariable(delegateExecution, variable);
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
    public void init(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        String sessionId = variable.getSessionId();
        try {
            UserApply userApply = variable.getUserApply();
            ApplyInfo applyInfo = applyService.saveMainInfo(userApply);
            // 初始化审批版本数据
            ApplyTrack applyTrack = applyTrackService.saveTrackInfo(applyInfo, delegateExecution);
            // 版本、主表数据关联
            applyService.createRelation(applyInfo, applyTrack.getId());
            // 保存审批记录
            variable.getAuditRecords().add(applyTrack);
            logger.info("初始化成功,sessionId:{}", sessionId);
        } catch (Exception e) {
            logger.error("初始化失败,sessionId:{},异常信息:{}", sessionId, e.getMessage());
            throw new BpmnError(FlowConstant.BPMNER_ERROR);
        } finally {
            super.resetVariable(delegateExecution, variable);
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
    public Boolean agree(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        ApplyTrack applyTrack = variable.getAuditRecords().stream().filter(record ->
            record.getLinkCode().equals(variable.getCurLinkCode())
        ).findFirst().get();
        return applyTrack.getAgree();
    }


    /**
     * <说明> 获取检查结果
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/27 19:58
     * @return java.lang.Boolean
     */
    @Override
    public Boolean checked(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        return variable.getCheckInfo().getChecked();
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
