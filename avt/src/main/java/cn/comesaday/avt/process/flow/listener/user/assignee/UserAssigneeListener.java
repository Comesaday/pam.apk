package cn.comesaday.avt.process.flow.listener.user.assignee;

import cn.comesaday.avt.business.matter.model.MatterUserSetting;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.business.water.model.Water;
import cn.comesaday.avt.process.flow.listener.user.UserListener;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.exception.PamException;
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
public class UserAssigneeListener extends UserListener {

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(UserAssigneeListener.class);

    @Autowired
    private MatterService matterService;

    @Override
    public void notify(DelegateTask delegateTask) {
        ProcessVariable variable = super.getVariable(delegateTask);
        String sessionId = variable.getSessionId();
        Water water = super.getProcessWater(sessionId);
        String actId = delegateTask.getId();
        Long matterId = variable.getUserApplyRequest().getMatterId();
        try {
            List<MatterUserSetting> settings = matterService.getMatterLinkUsers(matterId, actId);
            if (CollectionUtils.isEmpty(settings)) {
                throw new PamException("未配置审批人");
            }
            Long userId = settings.get(NumConstant.I0).getUserId();
            super.addAssigneeUser(delegateTask, String.valueOf(userId));
            super.saveSuccess(water, null, "节点"
                    + actId + "审批人初始化成功");
            logger.info("审批人初始化成功,节点ID:{},sessinId:{}", actId, sessionId);
        } catch (Exception e) {
            super.saveSuccess(water, null, "节点"
                    + actId + "审批人初始化异常:" + e.getMessage());
            logger.error("[获取事项审批人]事项ID:{},节点ID:{},异常信息:{}", matterId, actId, e.getMessage(), e);
        }
    }
}
