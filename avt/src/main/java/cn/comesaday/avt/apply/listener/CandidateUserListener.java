package cn.comesaday.avt.apply.listener;

import cn.comesaday.avt.apply.vo.ProcessVariable;
import cn.comesaday.avt.matter.model.MatterUserSetting;
import cn.comesaday.avt.matter.service.MatterUserSettingService;
import cn.comesaday.avt.process.constant.ProcessConstant;
import cn.comesaday.avt.process.listener.AbstractUserListener;
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
    private MatterUserSettingService matterUserSettingService;

    @Override
    public void notify(DelegateTask delegateTask) {
        ProcessVariable variable = super.getVariable(delegateTask);
        String actId = delegateTask.getId();
        Long matterId = variable.getAskInfoVo().getMatterId();
        try {
            MatterUserSetting setting = new MatterUserSetting();
            setting.setLinkCode(actId);
            setting.setMatterId(matterId);
            List<MatterUserSetting> settings = matterUserSettingService.findAll(setting);
            if (CollectionUtils.isEmpty(settings)) {
                logger.error("[获取事项审批人]事项ID:{},节点ID:{},未配置审批人", matterId, actId);
                throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
            }
            for (MatterUserSetting set : settings) {
                Long userId = settings.get(NumConstant.I0).getUserId();
                delegateTask.addCandidateUser(String.valueOf(userId));
            }
        } catch (Exception e) {
            logger.error("[获取事项审批人]事项ID:{},节点ID:{},异常信息:{}", matterId, actId, e.getMessage(), e);
        }
    }
}
