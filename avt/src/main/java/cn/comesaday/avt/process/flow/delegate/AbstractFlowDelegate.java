package cn.comesaday.avt.process.flow.delegate;

import cn.comesaday.avt.business.water.model.Water;
import cn.comesaday.avt.business.water.service.WaterService;
import cn.comesaday.avt.process.flow.constant.FlowConstant;
import cn.comesaday.avt.process.flow.handler.DefaultFlowHandler;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import cn.comesaday.coe.common.constant.NumConstant;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <描述> ProcessDelegate
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-14 19:50
 */
public abstract class AbstractFlowDelegate extends DefaultFlowHandler implements JavaDelegate {


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
    public void init(DelegateExecution delegateExecution) {
        ProcessVariable variable = this.getVariable(delegateExecution);
        String sessionId = variable.getSessionId();
        Water water = waterService.getProcessWater(sessionId);
        try {
            String processInstanceId = delegateExecution.getProcessInstanceId();
            water.setProcessId(processInstanceId);
            water.setTimes(NumConstant.I0);
            waterService.saveSuccess(water, variable, "流程信息初始化成功");
            logger.info("流程信息初始化成功,sessionId:{},方法:{}", sessionId);
        } catch (Exception e) {
            waterService.saveFail(water, variable, "流程信息初始化异常:" + e);
            logger.error("流程信息初始化异常,sessionId:{}, 方法:{},异常信息:{}" + e, sessionId);
            throw new BpmnError(FlowConstant.VARIABLE);
        } finally {
            super.resetVariable(delegateExecution, variable);
        }
    }

}
