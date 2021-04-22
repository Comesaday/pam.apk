package cn.comesaday.avt.process.flow.listener.user;

import cn.comesaday.avt.business.matter.model.MatterUserSetting;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.process.flow.constant.ProcessConstant;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.coe.common.constant.NumConstant;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <描述> 获取个人事项审批人
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-14 19:45
 */
@Component
public class AssigneeUserListener extends AbstractUserListener {

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(AssigneeUserListener.class);

    @Autowired
    private MatterService matterService;

    @Override
    public void notify(DelegateTask delegateTask) {
        ProcessVariable variable = super.getVariable(delegateTask);
        String actId = delegateTask.getId();
        Long matterId = variable.getApplyInfo().getMatterId();
        try {
            List<MatterUserSetting> settings = matterService.getMatterLinkUsers(matterId, actId);
            if (CollectionUtils.isEmpty(settings)) {
                logger.error("[获取事项审批人]事项ID:{},节点ID:{},未配置审批人", matterId, actId);
                throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
            }
            Long userId = settings.get(NumConstant.I0).getUserId();
            delegateTask.setAssignee(String.valueOf(userId));
        } catch (Exception e) {
            logger.error("[获取事项审批人]事项ID:{},节点ID:{},异常信息:{}", matterId, actId, e.getMessage(), e);
        }
    }
}
