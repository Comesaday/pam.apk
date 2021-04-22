package cn.comesaday.avt.process.flow.listener;

import cn.comesaday.avt.process.flow.constant.ProcessConstant;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import org.activiti.engine.delegate.DelegateTask;

/**
 * <Description> VariableListener
 * @author ChenWei
 * @CreateAt 2021-04-22 21:35
 */
public class VariableListener {

    /**
     * <说明> 获取流程变量
     * @param delegateTask DelegateTask
     * @author ChenWei
     * @date 2021/4/9 13:14
     * @return cn.comesaday.avt.process.flow.variable.ProcessVariable
     */
    public ProcessVariable getVariable(DelegateTask delegateTask) {
        return (ProcessVariable) delegateTask.getVariable(ProcessConstant.PROCESS_VARIABLE);
    }

}
