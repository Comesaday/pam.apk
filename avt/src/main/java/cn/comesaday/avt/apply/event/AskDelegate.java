package cn.comesaday.avt.apply.event;

import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.avt.apply.model.AskInfoTrack;
import cn.comesaday.avt.apply.service.AskInfoService;
import cn.comesaday.avt.apply.service.AskInfoTrackService;
import cn.comesaday.avt.apply.vo.ProcessVariable;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <描述> 审批流程
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-07 19:02
 */
@Service
public class AskDelegate {

    @Autowired
    private AskInfoService askInfoService;

    @Autowired
    private AskInfoTrackService askInfoTrackService;

    @Autowired
    private TaskService taskService;

    /**
     * <说明> 初始化版本表数据
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/8 11:05
     * @return void
     */
    public void submit(DelegateExecution delegateExecution) {
        // 申请内容
        ProcessVariable variable = (ProcessVariable) delegateExecution.getVariable("processInfo");
        // 获取当前节点内容
        Task task = taskService.createTaskQuery().processInstanceId(
                variable.getInstanceId()).active().singleResult();
        // 初始化审批版本数据
        AskInfo askInfo = variable.getAskInfoVo().getAskInfo();
        AskInfoTrack askInfoTrack = askInfoTrackService.initAskTrackInfo(askInfo, task);
        // 版本、主表数据关联
        askInfo.setCurTrackId(askInfoTrack.getId());
        askInfoService.save(askInfo);
        // 保存审批记录
        List<AskInfoTrack> records = variable.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            records = Collections.EMPTY_LIST;
        }
        records.add(askInfoTrack);
        Map<String, Object> variables = new HashMap<>();
        variables.put("processInfo", variable);
        taskService.complete(task.getId(), variables);
    }
}
