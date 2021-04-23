package cn.comesaday.avt.process.flow.listener.user;

import cn.comesaday.avt.business.matter.model.MatterUserSetting;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.business.user.service.UserGroupService;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.avt.process.water.model.Water;
import cn.comesaday.avt.process.water.service.WaterService;
import cn.comesaday.coe.core.basic.exception.PamException;
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

    @Autowired
    private WaterService waterService;

    @Override
    public void notify(DelegateTask delegateTask) {
        ProcessVariable variable = super.getVariable(delegateTask);
        String sessionId = variable.getSessionId();
        Water water = super.getProcessWater(sessionId);
        String actId = delegateTask.getTaskDefinitionKey();
        Long matterId = variable.getApplyInfo().getMatterId();
        try {
            List<MatterUserSetting> settings = matterService.getMatterLinkUsers(matterId, actId);
            if (CollectionUtils.isEmpty(settings)) {
                throw new PamException("未配置审批组");
            }
            List<String> groupCodes = settings.stream().map(set -> {
                return set.getGroupCode();
            }).collect(Collectors.toList());
            // 获取用户组所有人
            List<Long> groupsUserIds = userGroupService.getGroupsUserIds(groupCodes);
            List<String> stringUserIds = groupsUserIds.stream().map(userId -> {
                return String.valueOf(userId);
            }).collect(Collectors.toList());
            delegateTask.addCandidateUsers(stringUserIds);
            waterService.saveSuccess(water, null, "节点"
                    + actId + "审批组初始化成功");
            logger.info("审批组初始化成功,节点ID:{},sessinId:{}", actId, sessionId);
        } catch (Exception e) {
            waterService.saveFail(water, null, "节点"
                    + actId + "审批组初始化异常");
            logger.error("[获取事项审批人]事项ID:{},节点ID:{},异常信息:{}", matterId, actId, e.getMessage(), e);
        }
    }
}
