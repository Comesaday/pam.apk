package cn.comesaday.avt.apply.event;

import cn.comesaday.avt.apply.job.AskJob;
import cn.comesaday.avt.apply.model.AskFormData;
import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.avt.apply.model.AskProcess;
import cn.comesaday.avt.apply.service.AskFormDataService;
import cn.comesaday.avt.apply.service.AskInfoService;
import cn.comesaday.avt.apply.service.AskProcessService;
import cn.comesaday.avt.apply.vo.AskInfoVo;
import cn.comesaday.avt.apply.vo.ProcessVariable;
import cn.comesaday.avt.matter.model.Matter;
import cn.comesaday.avt.matter.service.MatterService;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.common.util.JsonUtil;
import cn.comesaday.coe.core.basic.exception.PamException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <描述> AskForDelegate
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-09 11:08
 */
@Service
public class AskForDelegate implements JavaDelegate, Serializable {

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(AskJob.class);

    public static final String PROCESS_VARIABLE = "processInfo";

    @Autowired
    private AskInfoService askInfoService;

    @Autowired
    private MatterService matterService;

    @Autowired
    private AskFormDataService askFormDataService;

    @Autowired
    private AskProcessService askProcessService;

    /**
     * <说明> 初始化流程信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 13:59
     * @return void
     */
    public void processInit(DelegateExecution delegateExecution) {
        ProcessVariable variable = this.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I2].getMethodName();
        AskProcess process = new AskProcess();
        try {
            String processInstanceId = delegateExecution.getProcessInstanceId();
            variable.setInstanceId(processInstanceId);
            process.setProcessId(processInstanceId);
            process.setSuccess(Boolean.TRUE);
            process.setResult("[流程信息初始化]成功");
            logger.info("[流程信息初始化]成功,方法:{}", methodName);
        } catch (Exception e) {
            process.setSuccess(Boolean.FALSE);
            process.setResult("[流程信息初始化]异常:" + e);
            logger.error("[流程信息初始化]异常,方法:{},异常信息:{}", methodName, e);
        } finally {
            process.setSessionId(variable.getSessionId());
            process.setParam(JsonUtil.toJson(variable));
            process.setTimes(NumConstant.I0);
            variable.setAskProcess(askProcessService.save(process));
            delegateExecution.setVariable(PROCESS_VARIABLE, variable);
        }
    }

    /**
     * <说明> 检查事项配置
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:11
     * @return void
     */
    public void checkMatterSetting(DelegateExecution delegateExecution) throws PamException {
        ProcessVariable variable = this.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I2].getMethodName();
        AskProcess process = variable.getAskProcess();
        try {
            Long matterId = variable.getAskInfoVo().getMatterId();
            Matter matter = matterService.getBasicMatter(matterId);
            // 检查事项配置
            matterService.checkMatterConfig(matter, NumConstant.I5, Boolean.FALSE);
            // 将事项信息设置到流程变量
            variable.getAskInfoVo().setMatter(matter);
            process.setResult("[检查事项配置]成功");
            logger.info("[检查事项配置]成功,方法:{}", methodName);
        } catch (Exception e) {
            process.setResult("[检查事项配置]异常:" + e);
            process.setSuccess(Boolean.FALSE);
            logger.error("[检查事项配置]异常,方法:{},异常信息:{}", methodName, e);
        } finally {
            variable.setAskProcess(askProcessService.save(process));
            delegateExecution.setVariable(PROCESS_VARIABLE, variable);
        }
    }

    /**
     * <说明> 检查申请信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:10
     * @return void
     */
    public void checkUserFill(DelegateExecution delegateExecution) throws PamException {
        ProcessVariable variable = this.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I2].getMethodName();
        AskProcess process = variable.getAskProcess();
        try {
            // 检查申请信息
            askInfoService.checkAskInfo(variable.getAskInfoVo());
            process.setResult("[检查申请信息]成功");
            logger.info("[检查申请信息]成功,方法:{}", methodName);
        } catch (Exception e) {
            process.setResult("[检查申请信息]异常:" + e);
            process.setSuccess(Boolean.FALSE);
            logger.error("[检查申请信息]异常,方法:{},异常信息:{}", methodName, e);
        } finally {
            variable.setAskProcess(askProcessService.save(process));
            delegateExecution.setVariable(PROCESS_VARIABLE, variable);
        }
    }

    /**
     * <说明> 初始化申请信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:11
     * @return void
     */
    public void initAskInfo(DelegateExecution delegateExecution) {
        ProcessVariable variable = this.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I2].getMethodName();
        AskProcess process = variable.getAskProcess();
        try {
            AskInfoVo askInfoVo = variable.getAskInfoVo();
            // 保存申请表单信息
            List<AskFormData> formDatas = askFormDataService.saveAll(askInfoVo.getAskInfos());
            // 初始化申请主表
            Matter matter = matterService.getBasicMatter(askInfoVo.getMatterId());
            AskInfo askInfo = askInfoService.initAskMainInfo(askInfoVo, matter);
            // 表单数据&主表关联
            formDatas.forEach(data -> data.setAskId(askInfo.getId()));
            askFormDataService.saveAll(formDatas);
            askInfoVo.setMatter(matter);
            askInfoVo.setAskInfos(formDatas);
            variable.setAskInfoVo(askInfoVo);
            process.setResult("[初始化申请信息]成功");
            logger.info("[初始化申请信息]成功,方法:{}", methodName);
        } catch (Exception e) {
            process.setResult("[初始化申请信息]异常:" + e);
            process.setSuccess(Boolean.FALSE);
            logger.error("[初始化申请信息]异常,方法:{},异常信息:{}", methodName, e);
        } finally {
            variable.setAskProcess(askProcessService.save(process));
            delegateExecution.setVariable(PROCESS_VARIABLE, variable);
        }
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
        ProcessVariable variable = this.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I2].getMethodName();
    }

    /**
     * <说明> 获取流程遍历
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 13:14
     * @return cn.comesaday.avt.apply.vo.ProcessVariable
     */
    private ProcessVariable getVariable(DelegateExecution delegateExecution) {
        return (ProcessVariable) delegateExecution.getVariable(PROCESS_VARIABLE);
    }
}
