package cn.comesaday.avt.apply.event;

import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.avt.apply.model.AskInfoTrack;
import cn.comesaday.avt.apply.service.AskInfoService;
import cn.comesaday.avt.apply.service.AskInfoTrackService;
import cn.comesaday.avt.apply.vo.ProcessVariable;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
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
public class SubmitService implements JavaDelegate {

    @Autowired
    private AskInfoService askInfoService;

    @Autowired
    private AskInfoTrackService askInfoTrackService;

    @Autowired
    private TaskService taskService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        // 申请内容
        ProcessVariable variable = (ProcessVariable) delegateExecution.getVariable("processInfo");
        // 获取当前节点内容
        Task task = taskService.createTaskQuery().processInstanceId(
                variable.getInstanceId()).active().singleResult();
        // 初始化审批版本数据
        AskInfo askInfo = variable.getAskInfoVo().getAskInfo();
        AskInfoTrack askInfoTrack = new AskInfoTrack();
        askInfoTrack.setAskId(askInfo.getId());
        askInfoTrack.setLinkCode(task.getId());
        askInfoTrack = askInfoTrackService.save(askInfoTrack);
        askInfo.setCurTrackId(askInfoTrack.getId());
        askInfoService.save(askInfo);

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
