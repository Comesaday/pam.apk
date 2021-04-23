package cn.comesaday.avt.process.flow.listener.event;

import cn.comesaday.avt.business.apply.model.ApplyTrack;
import cn.comesaday.avt.business.apply.service.ApplyTrackService;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <Description> AbstractVariableListener
 * @author ChenWei
 * @CreateAt 2021-04-22 21:30
 */
@Component
public class VariableStartListener extends AbstractVariableListener {

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(VariableStartListener.class);

    @Autowired
    private ApplyTrackService applyTrackService;

    /**
     * <说明> 审批节点初始化
     * @param delegateTask DelegateTask
     * @author ChenWei
     * @date 2021/4/23 14:38
     * @return void
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        ProcessVariable variable = super.getVariable(delegateTask);
        try {
            String linkCode = delegateTask.getTaskDefinitionKey();
            ApplyTrack applyTrack = new ApplyTrack();
            applyTrack.setAskId(variable.getApplyInfo().getAskId());
            applyTrack.setLinkCode(linkCode);
            applyTrack.setLinkName(delegateTask.getName());
            applyTrack = applyTrackService.save(applyTrack);
            // 更新流程变量数据
            variable.getRecords().add(applyTrack);
            variable.setCurLinkCode(linkCode);
        } catch (Exception e) {
            logger.error("审批节点初始化异常:{}", e.getMessage(), e);
        } finally {
            super.resetVariable(delegateTask, variable);
        }
    }
}
