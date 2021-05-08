package cn.comesaday.avt.business.user.service;

import cn.comesaday.avt.business.apply.model.ApplyTrack;
import cn.comesaday.avt.business.apply.service.ApplyTrackService;
import cn.comesaday.avt.business.apply.vo.Approval;
import cn.comesaday.avt.business.matter.model.MatterUserSetting;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.business.matter.service.MatterUserSettingService;
import cn.comesaday.avt.business.user.model.User;
import cn.comesaday.avt.business.water.model.Water;
import cn.comesaday.avt.business.water.service.WaterService;
import cn.comesaday.avt.process.flow.handler.FlowHandler;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <描述> UserService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-12 14:02
 */
@Service
@Transactional
public class UserService extends BaseService<User, Long> {

    @Autowired
    private ApplyTrackService applyTrackService;

    @Autowired
    private WaterService waterService;

    @Autowired
    private FlowHandler defaultFlowHandler;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private MatterService matterService;

    @Autowired
    private MatterUserSettingService matterUserSettingService;


    /**
     * <说明> 任务审批
     * @param approval ApprovalRequestVo
     * @author ChenWei
     * @date 2021/4/23 11:16
     * @return void
     */
    public void approval(Approval approval) {
        String taskId = approval.getTaskId();
        String comment = approval.getComment();
        Boolean agree = approval.getAgree();
        // 检验必需参数
        if (StringUtils.isEmpty(taskId) || null == agree || StringUtils.isEmpty(comment)) {
            return;
        }
        // 获取流程变量
        ProcessVariable variable = defaultFlowHandler.getVariableByTaskId(taskId);
        Water water = waterService.getProcessWater(variable.getSessionId());
        // 更新历史表
        ApplyTrack applyTrack = this.updateRecords(variable, approval);
        // 更新流程变量
        variable.getAuditRecords().stream().forEach(record -> {
            if (record.getLinkCode().equals(variable.getCurLinkCode())) {
                record = applyTrack;
            }});
        waterService.saveSuccess(water, variable, "审批成功");
        // 完成审批-流程继续
        defaultFlowHandler.complete(taskId, variable);
    }


    /**
     * <说明> 更新历史表
     * @param variable 流程变量
     * @param approval 审批参数
     * @author ChenWei
     * @date 2021/4/23 15:01
     * @return ApplyTrack
     */
    private ApplyTrack updateRecords(ProcessVariable variable, Approval approval) {
        ApplyTrack applyTrack = variable.getAuditRecords().stream().filter(record ->
                record.getLinkCode().equals(variable.getCurLinkCode())
        ).findFirst().get();
        applyTrack.setAgree(approval.getAgree());
        applyTrack.setComment(approval.getComment());
        applyTrack.setCheckId(approval.getUserId());
        applyTrack.setCheckName(approval.getUserName());
        return applyTrackService.save(applyTrack);
    }


    /**
     * <说明> 获取人员任务
     * @param userId 人员ID
     * @author ChenWei
     * @date 2021/4/25 17:46
     * @return cn.comesaday.coe.core.basic.bean.result.JsonResult
     */
    public List<Task> getUserTask(String userId) {
        return defaultFlowHandler.getUserTask(userId);
    }


    /**
     * <说明> 获取事项当前环节审批人
     * @param matterId 事项ID
     * @param curLinkCode 当前环节Code
     * @author ChenWei
     * @date 2021/5/8 11:35
     * @return java.util.List<cn.comesaday.avt.business.user.model.User>
     */
    public List<User> getAuditPersons(Long matterId, String curLinkCode) {
        if (null == matterId || StringUtils.isEmpty(curLinkCode)) {
            return null;
        }
        // 获取事项环节配置
        List<MatterUserSetting> userSettingList = matterService.getMatterLinkUsers(matterId, curLinkCode);
        if (CollectionUtils.isEmpty(userSettingList)) {
            return null;
        }
        // 获取审批人
        List<Long> userIds = userSettingList.stream().filter(userSetting ->
                null != userSetting.getUserId()).map(userSetting -> {
                    return userSetting.getUserId();}).collect(Collectors.toList());
        // 获取审批组的人
        List<String> groupCodes = userSettingList.stream().filter(userSetting ->
                null == userSetting.getUserId()).map(userSetting -> {
            return userSetting.getGroupCode();
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(groupCodes)) {
            userIds.addAll(userGroupService.getGroupsUserIds(groupCodes));
        }
        return userService.findAllById(userIds);
    }
}
