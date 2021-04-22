package cn.comesaday.avt.process.flow.listener.event;

import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * <Description> VariableEndListener
 * @author ChenWei
 * @CreateAt 2021-04-22 21:31
 */
@Component
public class VariableEndListener extends AbstractVariableListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        ProcessVariable variable = super.getVariable(delegateTask);
    }
}
