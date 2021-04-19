package cn.comesaday.avt.process.delegate;

import cn.comesaday.avt.business.water.model.Water;
import cn.comesaday.avt.business.water.service.WaterService;
import cn.comesaday.avt.process.variable.ProcessVariable;
import cn.comesaday.avt.process.constant.ProcessConstant;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.common.util.JsonUtil;
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
public abstract class AbstractProcessDelegate {


    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(AbstractProcessDelegate.class);


    @Autowired
    private WaterService waterService;


    /**
     * <说明> 初始化流程信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 13:59
     * @return void
     */
    public abstract void processInit(DelegateExecution delegateExecution);

    /**
     * <说明> 获取流程变量
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 13:14
     * @return cn.comesaday.avt.process.variable.ProcessVariable
     */
    public ProcessVariable getVariable(DelegateExecution delegateExecution) {
        return (ProcessVariable) delegateExecution.getVariable(ProcessConstant.PROCESS_VARIABLE);
    }

    /**
     * <说明> 记录错误信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 16:55
     * @return void
     */
    public void takeErrorInfo(DelegateExecution delegateExecution) {
        ProcessVariable variable = this.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I1].getMethodName();
        String sessionId = variable.getSessionId();
        Water water = variable.getWater();
        try {
            if (!water.getSuccess()) {
                waterService.save(water);
            }
            logger.info("[保存错误信息]成功,sessionId:{},错误信息:{}", sessionId, JsonUtil.toJson(water));
        } catch (Exception e) {
            logger.error("[保存错误信息]异常,sessionId:{},异常信息:{}", sessionId, e);
        }
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
        delegateExecution.setVariable(ProcessConstant.PROCESS_VARIABLE, variable);
    }
}
