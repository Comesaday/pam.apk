package cn.comesaday.avt.process.approval.service;

import cn.comesaday.avt.business.apply.model.ApplyTrack;
import cn.comesaday.avt.business.apply.service.ApplyTrackService;
import cn.comesaday.avt.process.approval.vo.ApprovalRequestVo;
import cn.comesaday.avt.process.flow.constant.ProcessConstant;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.coe.common.constant.NumConstant;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <描述> 审批service
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-23 11:07
 */
@Service
public class ApprovalService {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ApplyTrackService applyTrackService;

    /**
     * <说明> 任务审批
     * @param approvalRequest ApprovalRequestVo
     * @author ChenWei
     * @date 2021/4/23 11:16
     * @return void
     */
    public void approval(ApprovalRequestVo approvalRequest) {
        String taskId = approvalRequest.getTaskId();
        String comment = approvalRequest.getComment();
        Boolean agree = approvalRequest.getAgree();
        // 检验必需参数
        if (StringUtils.isEmpty(taskId) || null == agree || StringUtils.isEmpty(comment)) {
            return;
        }
        // 获取流程变量
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        ProcessVariable variable = (ProcessVariable) task.getProcessVariables()
                .get(ProcessConstant.PROCESS_VARIABLE);
        // 更新历史表
        ApplyTrack applyTrack = this.updateRecords(variable, approvalRequest);
        // 重新设置流程变量
        variable.getRecords().stream().forEach(record -> {
            if (record.getLinkCode().equals(variable.getCurLinkCode())) {
                record = applyTrack;
            }});
        Map<String, Object> variables = new HashMap<>();
        variables.put(ProcessConstant.PROCESS_VARIABLE, variable);
        taskService.complete(taskId, variables);
    }

    /**
     * <说明> 更新历史表
     * @param variable 流程变量
     * @param approvalRequest 审批参数
     * @author ChenWei
     * @date 2021/4/23 15:01
     * @return ApplyTrack
     */
    private ApplyTrack updateRecords(ProcessVariable variable, ApprovalRequestVo approvalRequest) {
        ApplyTrack applyTrack = variable.getRecords().stream().filter(record ->
                record.getLinkCode().equals(variable.getCurLinkCode())
        ).findFirst().get();
        applyTrack.setAgree(approvalRequest.getAgree());
        applyTrack.setCheckName(approvalRequest.getComment());
        applyTrack.setCheckId(NumConstant.L100);
        applyTrack.setCheckName("系统");
        return applyTrackService.save(applyTrack);
    }
}
