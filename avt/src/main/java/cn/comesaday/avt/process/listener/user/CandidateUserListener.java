package cn.comesaday.avt.process.listener.user;

import cn.comesaday.avt.business.matter.model.MatterUserSetting;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.business.user.service.UserGroupService;
import cn.comesaday.avt.process.constant.ProcessConstant;
import cn.comesaday.avt.process.variable.ProcessVariable;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <描述> 分组事项审批人
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-14 19:47
 */
@Component
public class CandidateUserListener extends AbstractUserListener {

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(CandidateUserListener.class);

    @Autowired
    private MatterService matterService;

    @Autowired
    private UserGroupService userGroupService;

    @Override
    public void notify(DelegateTask delegateTask) {
        ProcessVariable variable = super.getVariable(delegateTask);
        String actId = delegateTask.getTaskDefinitionKey();
        Long matterId = variable.getAskInfo().getMatterId();
        try {
            List<MatterUserSetting> settings = matterService.getMatterLinkUsers(matterId, actId);
            if (CollectionUtils.isEmpty(settings)) {
                logger.error("[获取事项审批人]事项ID:{},节点ID:{},未配置审批组", matterId, actId);
                throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
            }
            List<Long> groupIds = settings.stream().map(set -> {
                return set.getGroupId();
            }).collect(Collectors.toList());
            // 获取用户组所有人
            List<Long> groupsUserIds = userGroupService.getGroupsUserIds(groupIds);
            List<String> stringUserIds = groupsUserIds.stream().map(userId -> {
                return String.valueOf(userId);
            }).collect(Collectors.toList());
            delegateTask.addCandidateUsers(stringUserIds);
        } catch (Exception e) {
            logger.error("[获取事项审批人]事项ID:{},节点ID:{},异常信息:{}", matterId, actId, e.getMessage(), e);
        }
    }
}
