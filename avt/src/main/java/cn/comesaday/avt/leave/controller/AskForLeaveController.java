package cn.comesaday.avt.leave.controller;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * <描述> 请假controller
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-08 16:10
 */
@Controller
@RequestMapping("/leave")
public class AskForLeaveController {

    @Autowired
    private RepositoryService repositoryService;

    /**
     * <说明> 流程部署
     * @author ChenWei
     * @date 2021/3/8 16:17
     * @return void
     */
    @RequestMapping("/task/deployed")
    public void deployed() {
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("/processes/ask_for_leave.bpmn");
        String id = builder.deploy().getId();
        repositoryService.setDeploymentKey(id, "ask_for_leave");
    }

    public void apply() {
        Map<String, Object> params = new HashMap<>();
        params.put("applyUserId", "111");
    }
}
