package cn.comesaday.avt.process.flow.listener.event;

import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

/**
 * <Description> AbstractVariableListener
 * @author ChenWei
 * @CreateAt 2021-04-22 21:30
 */
@Component
public class VariableStartListener extends AbstractVariableListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        ProcessVariable variable = super.getVariable(delegateTask);

    }
}
