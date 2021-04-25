package cn.comesaday.avt.process.flow.handler;

import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * <描述> FlowService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-25 17:52
 */
public interface AbstractFlowHandler {

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
    Model createModel(String key, String name, String description, Integer version) throws Exception;


    /**
     * <说明> 流程部署
     * @param modelId 事项模型id
     * @author ChenWei
     * @date 2021/3/16 14:21
     * @return Deployment
     */
    Deployment deploymentModel(String modelId) throws Exception;


    /**
     * <说明> 获取流程变量
     * @param delegateTask DelegateTask
     * @author ChenWei
     * @date 2021/4/9 13:14
     * @return cn.comesaday.avt.process.flow.variable.ProcessVariable
     */
    ProcessVariable getVariable(DelegateTask delegateTask);


    /**
     * <说明> 获取流程变量
     * @param taskId 任务ID
     * @author ChenWei
     * @date 2021/4/25 17:28
     * @return cn.comesaday.avt.process.flow.variable.ProcessVariable
     */
    ProcessVariable getVariable(String taskId);


    /**
     * <说明> 重新设置流程变量
     * @param delegateTask DelegateTask
     * @param variable ProcessVariable
     * @author ChenWei
     * @date 2021/4/23 17:35
     * @return void
     */
    void resetVariable(DelegateTask delegateTask, ProcessVariable variable);


    /**
     * <说明> 完成此节点任务
     * @param taskId 任务ID
     * @param variable 流程变量
     * @author ChenWei
     * @date 2021/4/25 17:34
     * @return void
     */
    void complete(String taskId, Object variable);


    /**
     * <说明> 获取人员任务
     * @param userId 人员ID
     * @author ChenWei
     * @date 2021/4/25 17:42
     * @return java.util.List<org.activiti.engine.task.Task>
     */
    List<Task> getUserTask(String userId);
}
