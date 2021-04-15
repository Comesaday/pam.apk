package cn.comesaday.avt.process.listener;

import cn.comesaday.avt.process.constant.ProcessConstant;
import cn.comesaday.avt.process.variable.ProcessVariable;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * <描述> UserListener获取事项审批人
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-14 19:44
 */
public abstract class AbstractUserListener implements TaskListener {

    @Override
    public abstract void notify(DelegateTask delegateTask);

    /**
     * <说明> 获取流程变量
     * @param delegateTask DelegateTask
     * @author ChenWei
     * @date 2021/4/9 13:14
     * @return cn.comesaday.avt.process.variable.ProcessVariable
     */
    public ProcessVariable getVariable(DelegateTask delegateTask) {
        return (ProcessVariable) delegateTask.getVariable(ProcessConstant.PROCESS_VARIABLE);
    }

}
