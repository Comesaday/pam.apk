package cn.comesaday.avt.process.flow.handler;

import cn.comesaday.avt.business.water.service.WaterService;
import cn.comesaday.avt.process.flow.constant.FlowConstant;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.exception.PamException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <描述> 流程处理器
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-25 17:31
 */
@Component
public class DefaultFlowAndWaterHandler extends WaterService implements FlowHandler {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;


    /**
     * <说明> 创建流程模型
     * @param key 模型code
     * @param name 模型名称
     * @param description 事项
     * @param version 模型版本
     * @author ChenWei
     * @date 2021/3/16 14:12
     * @return Model
     */
    @Override
    public Model createModel(String key, String name, String description, Integer version) throws Exception {
        // 创建保存流程model
        Model model = repositoryService.newModel();
        model.setName(name);
        model.setKey(key);
        model.setCategory(key);
        model.setVersion(version);
        ObjectNode modelNode = new ObjectMapper().createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, version);
        model.setMetaInfo(modelNode.toString());
        repositoryService.saveModel(model);
        //完善ModelEditorSource
        ObjectNode editorNode = new ObjectMapper().createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = new ObjectMapper().createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(model.getId(), editorNode.toString()
                .getBytes("utf-8"));
        return model;
    }


    /**
     * <说明> 流程部署
     * @param modelId 事项模型id
     * @author ChenWei
     * @date 2021/3/16 14:21
     * @return Deployment
     */
    @Override
    public Deployment deploymentModel(String modelId) throws Exception {
        // 流程部署
        Model model = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(model.getId());
        if (null == bytes) {
            throw new PamException("模型数据为空");
        }
        JsonNode jsonNode = new ObjectMapper().readTree(bytes);
        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
        if (bpmnModel.getProcesses().size() == NumConstant.I0) {
            throw new PamException("缺少流程主线");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        String processName = model.getName() + ".bpmn20.xml";
        Deployment deploy = repositoryService.createDeployment().name(processName)
                .addString(processName, new String(bpmnBytes, FlowConstant.BPMN_UTF8)).deploy();
        // 保存部署信息
        model.setDeploymentId(deploy.getId());
        repositoryService.saveModel(model);
        return deploy;
    }


    /**
     * <说明> 获取流程变量
     * @param delegateTask DelegateTask
     * @author ChenWei
     * @date 2021/4/9 13:14
     * @return cn.comesaday.avt.process.flow.variable.ProcessVariable
     */
    @Override
    public ProcessVariable getVariable(DelegateTask delegateTask) {
        return (ProcessVariable) delegateTask.getVariable(FlowConstant.VARIABLE);
    }


    /**
     * <说明> 获取流程变量
     * @param taskId 任务ID
     * @author ChenWei
     * @date 2021/4/25 17:28
     * @return cn.comesaday.avt.process.flow.variable.ProcessVariable
     */
    @Override
    public ProcessVariable getVariable(String taskId) {
        return (ProcessVariable) taskService.getVariable(taskId, FlowConstant.VARIABLE);
    }



    /**
     * <说明> 获取流程变量
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 13:14
     * @return cn.comesaday.avt.process.flow.variable.ProcessVariable
     */
    @Override
    public ProcessVariable getVariable(DelegateExecution delegateExecution) {
        return (ProcessVariable) delegateExecution.getVariable(FlowConstant.VARIABLE);
    }


    /**
     * <说明> 设置流程变量
     * @param delegateExecution DelegateExecution
     * @param variable ProcessVariable
     * @author ChenWei
     * @date 2021/4/14 17:53
     * @return void
     */
    @Override
    public void resetVariable(DelegateExecution delegateExecution, ProcessVariable variable) {
        delegateExecution.setVariable(FlowConstant.VARIABLE, variable);
    }


    /**
     * <说明> 重新设置流程变量
     * @param delegateTask DelegateTask
     * @param variable ProcessVariable
     * @author ChenWei
     * @date 2021/4/23 17:35
     * @return void
     */
    @Override
    public void resetVariable(DelegateTask delegateTask, ProcessVariable variable) {
        delegateTask.setVariable(FlowConstant.VARIABLE, variable);
    }


    /**
     * <说明> 完成此节点任务
     * @param taskId 任务ID
     * @param variable 流程变量
     * @author ChenWei
     * @date 2021/4/25 17:34
     * @return void
     */
    @Override
    public void complete(String taskId, Object variable) {
        Map<String, Object> variables = new HashMap<>();
        variables.put(FlowConstant.VARIABLE, variable);
        taskService.complete(taskId, variables);
    }


    /**
     * <说明> 获取人员任务
     * @param userId 人员ID
     * @author ChenWei
     * @date 2021/4/25 17:42
     * @return java.util.List<org.activiti.engine.task.Task>
     */
    @Override
    public List<Task> getUserTask(String userId) {
        return taskService.createTaskQuery().taskCandidateOrAssigned(userId).list();
    }


    /**
     * <说明> 设置审批人
     * @param taskId 任务ID
     * @param userId 用户ID
     * @author ChenWei
     * @date 2021/4/26 19:06
     * @return void
     */
    @Override
    public void addAssigneeUser(String taskId, String userId) {
        taskService.setAssignee(taskId, userId);
    }


    /**
     * <说明> 设置审批人
     * @param delegateTask DelegateTask
     * @param userId 用户ID
     * @author ChenWei
     * @date 2021/4/27 15:29
     * @return void
     */
    @Override
    public void addAssigneeUser(DelegateTask delegateTask, String userId) {
        delegateTask.setAssignee(userId);
    }


    /**
     * <说明> 变更审批人
     * @param taskId 任务ID
     * @param userId 用户ID
     * @author ChenWei
     * @date 2021/4/26 19:06
     * @return void
     */
    @Override
    public void addCandidateUser(String taskId, String userId) {
        taskService.addCandidateUser(taskId, userId);
    }


    /**
     * <说明> 变更审批人
     * @param taskId 任务ID
     * @param userIds 用户IDS
     * @author ChenWei
     * @date 2021/4/26 19:07
     * @return void
     */
    @Override
    public void addCandidateUsers(String taskId, List<String> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        userIds.stream().forEach(userId -> {
            this.addCandidateUser(taskId, userId);
        });
    }


    /**
     * <说明> 删除办理人
     * @param taskId 任务ID
     * @param userId 用户ID
     * @author ChenWei
     * @date 2021/4/27 15:11
     * @return void
     */
    @Override
    public void deleteCandidateUser(String taskId, String userId) {
        taskService.deleteCandidateUser(taskId, userId);
    }


    /**
     * <说明> 删除办理人
     * @param taskId 任务ID
     * @param userIds 用户IDS
     * @author ChenWei
     * @date 2021/4/27 15:11
     * @return void
     */
    @Override
    public void deleteCandidateUsers(String taskId, List<String> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return;
        }
        userIds.stream().forEach(userId -> {
            this.deleteCandidateUser(taskId, userId);
        });
    }


    /**
     * <说明> 设置审批人
     * @param delegateTask DelegateTask
     * @param userIds 用户IDS
     * @author ChenWei
     * @date 2021/4/27 15:31
     * @return void
     */
    @Override
    public void addCandidateUsers(DelegateTask delegateTask, List<String> userIds) {
        delegateTask.addCandidateUsers(userIds);
    }


    /**
     * <说明> 任务变更审批人
     * @param taskId 任务ID
     * @param oldUserId 前任审批人ID
     * @param userId 更换审批人ID
     * @author ChenWei
     * @date 2021/4/27 14:44
     * @return void
     */
    @Override
    public void changeUser(String taskId, String oldUserId, String userId) {
        if (isAssigneeOrCandidateUser(taskId, oldUserId)) {
            return;
        }
        if (isAssigneeUser(taskId, userId)) {
            taskService.setAssignee(taskId, userId);
        } else {
            this.deleteCandidateUser(taskId, oldUserId);
            this.addCandidateUser(taskId, userId);
        }
    }


    /**
     * <说明> 是否是办理人
     * @param taskId 任务ID
     * @param userId 审批人ID
     * @author ChenWei
     * @date 2021/4/27 15:18
     * @return java.lang.Boolean
     */
    @Override
    public Boolean isAssigneeUser(String taskId, String userId) {
        return null != this.getTaskQuery(taskId).taskAssignee(userId).singleResult();
    }


    /**
     * <说明> 是否是办理人
     * @param taskId 任务ID
     * @param userId 审批人ID
     * @author ChenWei
     * @date 2021/4/27 15:18
     * @return java.lang.Boolean
     */
    @Override
    public Boolean isCandidateUser(String taskId, String userId) {
        return null != this.getTaskQuery(taskId).taskCandidateUser(userId).singleResult();
    }


    /**
     * <说明> 是否是办理人
     * @param taskId 任务ID
     * @param userId 审批人ID
     * @author ChenWei
     * @date 2021/4/27 15:18
     * @return java.lang.Boolean
     */
    @Override
    public Boolean isAssigneeOrCandidateUser(String taskId, String userId) {
        return null != this.getTaskQuery(taskId).taskCandidateOrAssigned(userId).singleResult();
    }


    /**
     * <说明> 获取TaskQuery
     * @param taskId 任务ID
     * @author ChenWei
     * @date 2021/4/27 15:16
     * @return org.activiti.engine.task.TaskQuery
     */
    private TaskQuery getTaskQuery(String taskId) {
        return taskService.createTaskQuery().taskId(taskId);
    }
}
