package cn.comesaday.avt.apply.event;

import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.avt.apply.model.AskInfoTrack;
import cn.comesaday.avt.apply.service.AskInfoService;
import cn.comesaday.avt.apply.service.AskInfoTrackService;
import cn.comesaday.avt.apply.vo.ProcessVariable;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
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
public class AskDelegate implements JavaDelegate, Serializable {

    private static final long serialVersionUID = 8513750196548027535L;

    @Autowired
    private AskInfoService askInfoService;

    @Autowired
    private AskInfoTrackService askInfoTrackService;

    @Autowired
    private TaskService taskService;

    /**
     * <说明> 初始化版本表数据
     * @param execution DelegateExecution
     * @author ChenWei
     * @date 2021/4/8 11:05
     * @return void
     */
    @Override
    public void execute(DelegateExecution execution) {
        // 申请内容
        ProcessVariable variable = (ProcessVariable) execution.getVariable("processInfo");
        variable.setInstanceId(execution.getProcessInstanceId());
        // 初始化审批版本数据
        AskInfo askInfo = variable.getAskInfoVo().getAskInfo();
        AskInfoTrack askInfoTrack = askInfoTrackService.initAskTrackInfo(askInfo, execution);
        // 版本、主表数据关联
        askInfo.setCurTrackId(askInfoTrack.getId());
        askInfoService.save(askInfo);
        // 保存审批记录
        List<AskInfoTrack> records = variable.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            records = new ArrayList<>();
        }
        records.add(askInfoTrack);
        Map<String, Object> variables = new HashMap<>();
        variables.put("processInfo", variable);
    }

    public void test(DelegateExecution execution) {
        // 申请内容
        ProcessVariable variable = (ProcessVariable) execution.getVariable("processInfo");
        variable.setInstanceId(execution.getProcessInstanceId());
        // 初始化审批版本数据
        AskInfo askInfo = variable.getAskInfoVo().getAskInfo();
        AskInfoTrack askInfoTrack = askInfoTrackService.initAskTrackInfo(askInfo, execution);
        // 版本、主表数据关联
        askInfo.setCurTrackId(askInfoTrack.getId());
        askInfoService.save(askInfo);
        // 保存审批记录
        List<AskInfoTrack> records = variable.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            records = new ArrayList<>();
        }
        records.add(askInfoTrack);
        Map<String, Object> variables = new HashMap<>();
        variables.put("processInfo", variable);
    }
}
