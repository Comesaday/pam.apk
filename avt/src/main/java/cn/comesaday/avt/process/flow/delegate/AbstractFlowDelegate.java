package cn.comesaday.avt.process.flow.delegate;

import cn.comesaday.avt.process.flow.constant.FlowConstant;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.avt.business.water.model.Water;
import cn.comesaday.avt.business.water.service.WaterService;
import cn.comesaday.coe.common.constant.NumConstant;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <描述> ProcessDelegate
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-14 19:50
 */
public abstract class AbstractFlowDelegate {


    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(AbstractFlowDelegate.class);


    @Autowired
    private WaterService waterService;


    /**
     * <说明> 初始化流程信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 13:59
     * @return void
     */
    public void processInit(DelegateExecution delegateExecution) {
        ProcessVariable variable = this.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I1].getMethodName();
        String sessionId = variable.getSessionId();
        Water water = new Water();
        try {
            String processInstanceId = delegateExecution.getProcessInstanceId();
            water.setProcessId(processInstanceId);
            water.setSessionId(sessionId);
            water.setTimes(NumConstant.I0);
            waterService.saveSuccess(water, variable, "[流程信息初始化]成功");
            logger.info("[流程信息初始化]成功,sessionId:{},方法:{}", sessionId, methodName);
        } catch (Exception e) {
            waterService.saveFail(water, variable, "[流程信息初始化]异常:" + e);
            logger.error("[流程信息初始化]异常,sessionId:{}, 方法:{},异常信息:{}" + e, sessionId, methodName);
            throw new BpmnError(FlowConstant.BPMNER_ERROR_EXCEPTION);
        } finally {
            this.resetProcessVariable(delegateExecution, variable);
        }
    }


    /**
     * <说明> 获取流程变量
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 13:14
     * @return cn.comesaday.avt.process.flow.variable.ProcessVariable
     */
    public ProcessVariable getVariable(DelegateExecution delegateExecution) {
        return (ProcessVariable) delegateExecution.getVariable(FlowConstant.PROCESS_VARIABLE);
    }


    /**
     * <说明> 设置流程变量
     * @param delegateExecution DelegateExecution
     * @param variable ProcessVariable
     * @author ChenWei
     * @date 2021/4/14 17:53
     * @return void
     */
    public void resetProcessVariable(DelegateExecution delegateExecution, ProcessVariable variable) {
        delegateExecution.setVariable(FlowConstant.PROCESS_VARIABLE, variable);
    }
}
