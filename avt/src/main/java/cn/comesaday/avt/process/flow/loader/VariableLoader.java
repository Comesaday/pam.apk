package cn.comesaday.avt.process.flow.loader;

import cn.comesaday.avt.process.flow.constant.ProcessConstant;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.avt.process.water.model.Water;
import cn.comesaday.avt.process.water.service.WaterService;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <Description> VariableListener
 * @author ChenWei
 * @CreateAt 2021-04-22 21:35
 */
@Component
public class VariableLoader {

    // 流程记录service
    @Autowired
    private WaterService waterService;

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


    /**
     * <说明> 重新设置流程变量
     * @param delegateTask DelegateTask
     * @param variable ProcessVariable
     * @author ChenWei
     * @date 2021/4/23 17:35
     * @return void
     */
    public void resetVariable(DelegateTask delegateTask, ProcessVariable variable) {
        delegateTask.setVariable(ProcessConstant.PROCESS_VARIABLE, variable);
    }


    /**
     * <说明> 查询流程流水记录
     * @param sessionId String
     * @author ChenWei
     * @date 2021/4/22 19:34
     * @return cn.comesaday.avt.process.water.model.Water
     */
    public Water getProcessWater(String sessionId) {
        Water water = new Water(sessionId);
        return waterService.findOne(water);
    }

}
