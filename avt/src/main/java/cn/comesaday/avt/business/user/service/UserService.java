package cn.comesaday.avt.business.user.service;

import cn.comesaday.avt.business.apply.model.ApplyTrack;
import cn.comesaday.avt.business.apply.service.ApplyTrackService;
import cn.comesaday.avt.business.apply.vo.ApprovalVo;
import cn.comesaday.avt.business.user.model.User;
import cn.comesaday.avt.business.water.model.Water;
import cn.comesaday.avt.business.water.service.WaterService;
import cn.comesaday.avt.process.flow.handler.AbstractFlowHandler;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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
    private AbstractFlowHandler abstractFlowHandler;


    /**
     * <说明> 任务审批
     * @param approvalRequest ApprovalRequestVo
     * @author ChenWei
     * @date 2021/4/23 11:16
     * @return void
     */
    public void approval(ApprovalVo approvalRequest) {
        String taskId = approvalRequest.getTaskId();
        String comment = approvalRequest.getComment();
        Boolean agree = approvalRequest.getAgree();
        // 检验必需参数
        if (StringUtils.isEmpty(taskId) || null == agree || StringUtils.isEmpty(comment)) {
            return;
        }
        // 获取流程变量
        ProcessVariable variable = abstractFlowHandler.getVariable(taskId);
        Water water = waterService.getProcessWater(variable.getSessionId());
        // 更新历史表
        ApplyTrack applyTrack = this.updateRecords(variable, approvalRequest);
        // 重新设置流程变量
        variable.getRecords().stream().forEach(record -> {
            if (record.getLinkCode().equals(variable.getCurLinkCode())) {
                record = applyTrack;
            }});
        waterService.saveSuccess(water, variable, "审批成功");
        abstractFlowHandler.complete(taskId, variable);
    }


    /**
     * <说明> 更新历史表
     * @param variable 流程变量
     * @param approvalRequest 审批参数
     * @author ChenWei
     * @date 2021/4/23 15:01
     * @return ApplyTrack
     */
    private ApplyTrack updateRecords(ProcessVariable variable, ApprovalVo approvalRequest) {
        ApplyTrack applyTrack = variable.getRecords().stream().filter(record ->
                record.getLinkCode().equals(variable.getCurLinkCode())
        ).findFirst().get();
        applyTrack.setAgree(approvalRequest.getAgree());
        applyTrack.setComment(approvalRequest.getComment());
        applyTrack.setCheckId(NumConstant.L100);
        applyTrack.setCheckName("系统");
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
        return abstractFlowHandler.getUserTask(userId);
    }
}
