package cn.comesaday.avt.apply.service;

import cn.comesaday.avt.apply.delegate.AbstractApplyDelegate;
import cn.comesaday.avt.apply.model.AskFormData;
import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.avt.apply.model.AskInfoTrack;
import cn.comesaday.avt.apply.model.AskProcess;
import cn.comesaday.avt.apply.vo.AskInfoVo;
import cn.comesaday.avt.apply.vo.ProcessVariable;
import cn.comesaday.avt.matter.model.Matter;
import cn.comesaday.avt.matter.service.MatterService;
import cn.comesaday.avt.process.constant.ProcessConstant;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.common.util.JsonUtil;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <描述> AskForDelegate
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-09 11:08
 */
@Service
public class ApplyDelegate extends AbstractApplyDelegate implements JavaDelegate, Serializable {

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(ApplyDelegate.class);

    @Autowired
    private AskInfoService askInfoService;

    @Autowired
    private MatterService matterService;

    @Autowired
    private AskFormDataService askFormDataService;

    @Autowired
    private AskProcessService askProcessService;

    @Autowired
    private AskInfoTrackService askInfoTrackService;

    /**
     * <说明> 初始化流程信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 13:59
     * @return void
     */
    @Override
    public void processInit(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I1].getMethodName();
        String sessionId = variable.getSessionId();
        AskProcess process = new AskProcess();
        variable.setAskProcess(process);
        try {
            String processInstanceId = delegateExecution.getProcessInstanceId();
            variable.setInstanceId(processInstanceId);
            process.setProcessId(processInstanceId);
            process.setSessionId(sessionId);
            process.setParam(JsonUtil.toJson(variable));
            process.setTimes(NumConstant.I0);
            process.setSuccess(Boolean.TRUE);
            process.setResult("[流程信息初始化]成功");
            askProcessService.save(process);
            logger.info("[流程信息初始化]成功,sessionId:{},方法:{}", sessionId, methodName);
        } catch (Exception e) {
            process.setSuccess(Boolean.FALSE);
            process.setResult("[流程信息初始化]异常:" + e);
            logger.error("[流程信息初始化]异常,sessionId:{}, 方法:{},异常信息:{}" + e, sessionId, methodName);
            throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
        } finally {
            super.resetProcessVariable(delegateExecution, variable);
        }
    }

    /**
     * <说明> 检查事项配置
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:11
     * @return void
     */
    @Override
    public void checkMatterSetting(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I1].getMethodName();
        String sessionId = variable.getSessionId();
        AskProcess process = variable.getAskProcess();
        try {
            Long matterId = variable.getAskInfoVo().getMatterId();
            Matter matter = matterService.getBasicMatter(matterId);
            // 检查事项配置
            matterService.checkMatterConfig(matter, NumConstant.I5, Boolean.FALSE);
            // 将事项信息设置到流程变量
            variable.getAskInfoVo().setMatter(matter);
            process.setResult("[检查事项配置]成功");
            askProcessService.save(process);
            logger.info("[检查事项配置]成功,sessionId:{},方法:{}", sessionId, methodName);
        } catch (Exception e) {
            process.setSuccess(Boolean.FALSE);
            process.setResult("[检查事项配置]异常:" + e);
            logger.error("[检查事项配置]异常,sessionId:{},方法:{},异常信息:{}" + e, sessionId, methodName);
            throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
        } finally {
            super.resetProcessVariable(delegateExecution, variable);
        }
    }

    /**
     * <说明> 检查申请信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:10
     * @return void
     */
    @Override
    public void checkUserFill(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I1].getMethodName();
        String sessionId = variable.getSessionId();
        AskProcess process = variable.getAskProcess();
        try {
            // 检查申请信息
            askInfoService.checkAskInfo(variable.getAskInfoVo());
            process.setResult("[检查申请信息]成功");
            askProcessService.save(process);
            logger.info("[检查申请信息]成功,sessionId:{},方法:{}", sessionId, methodName);
        } catch (Exception e) {
            process.setSuccess(Boolean.FALSE);
            process.setResult("[检查申请信息]异常:" + e);
            logger.error("[检查申请信息]异常,sessionId:{},方法:{},异常信息:{}" + e, sessionId, methodName);
            throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
        } finally {
            super.resetProcessVariable(delegateExecution, variable);
        }
    }

    /**
     * <说明> 初始化申请信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 11:11
     * @return void
     */
    @Override
    public void initAskInfo(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I1].getMethodName();
        String sessionId = variable.getSessionId();
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
            // 保存最新信息到流程变量
            askInfoVo.setAskId(askInfo.getId());
            askInfoVo.setAskInfo(askInfo);
            askInfoVo.setAskInfos(formDatas);
            process.setResult("[初始化申请信息]成功");
            askProcessService.save(process);
            logger.info("[初始化申请信息]成功,sessionId:{},方法:{}", sessionId, methodName);
        } catch (Exception e) {
            process.setSuccess(Boolean.FALSE);
            process.setResult("[初始化申请信息]异常:" + e);
            logger.error("[初始化申请信息]异常,sessionId:{},方法:{},异常信息:{}" + e, sessionId, methodName);
            throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
        } finally {
            super.resetProcessVariable(delegateExecution, variable);
        }
    }

    /**
     * <说明> 初始化申请版本信息
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/9 15:04
     * @return void
     */
    @Override
    public void initAskTrack(DelegateExecution delegateExecution) {
        ProcessVariable variable = super.getVariable(delegateExecution);
        String methodName = Thread.currentThread().getStackTrace()[NumConstant.I1].getMethodName();
        String sessionId = variable.getSessionId();
        AskProcess process = variable.getAskProcess();
        try {
            // 初始化审批版本数据
            AskInfo askInfo = variable.getAskInfoVo().getAskInfo();
            AskInfoTrack askInfoTrack = askInfoTrackService.initAskTrackInfo(askInfo, delegateExecution);
            // 版本、主表数据关联
            askInfo.setCurTrackId(askInfoTrack.getId());
            askInfoService.save(askInfo);
            // 保存审批记录
            List<AskInfoTrack> records = variable.getRecords();
            if (CollectionUtils.isEmpty(records)) {
                records = new ArrayList<>();
            }
            records.add(askInfoTrack);
            process.setResult("[初始化版本信息]成功");
            askProcessService.save(process);
            logger.info("[初始化版本信息]成功,sessionId:{},方法:{}", sessionId, methodName);
        } catch (Exception e) {
            process.setSuccess(Boolean.FALSE);
            process.setResult("[初始化版本信息]异常:" + e);
            logger.error("[初始化版本信息]异常,sessionId:{},方法:{},异常信息:{}" + e, sessionId, methodName);
            throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
        } finally {
            super.resetProcessVariable(delegateExecution, variable);
        }
    }

    @Override
    public void execute(DelegateExecution delegateExecution) {
    }
}
