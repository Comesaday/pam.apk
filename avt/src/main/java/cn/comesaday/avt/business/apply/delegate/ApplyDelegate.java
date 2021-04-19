package cn.comesaday.avt.business.apply.delegate;

import cn.comesaday.avt.business.apply.model.AskFormData;
import cn.comesaday.avt.business.apply.model.AskInfo;
import cn.comesaday.avt.business.apply.model.AskInfoTrack;
import cn.comesaday.avt.business.apply.service.AskFormDataService;
import cn.comesaday.avt.business.apply.service.AskInfoService;
import cn.comesaday.avt.business.apply.service.AskInfoTrackService;
import cn.comesaday.avt.business.apply.vo.AskInfoVo;
import cn.comesaday.avt.business.matter.enums.MatterEnum;
import cn.comesaday.avt.business.matter.model.Matter;
import cn.comesaday.avt.business.matter.service.MatterService;
import cn.comesaday.avt.process.constant.ProcessConstant;
import cn.comesaday.avt.business.water.model.Water;
import cn.comesaday.avt.business.water.service.WaterService;
import cn.comesaday.avt.process.variable.ProcessVariable;
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
    private WaterService processInfoService;

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
        Water water = new Water();
        variable.setWater(water);
        try {
            String processInstanceId = delegateExecution.getProcessInstanceId();
            variable.setProcessId(processInstanceId);
            water.setProcessId(processInstanceId);
            water.setSessionId(sessionId);
            water.setParam(JsonUtil.toJson(variable));
            water.setTimes(NumConstant.I0);
            water.setSuccess(Boolean.TRUE);
            water.setResult("[流程信息初始化]成功");
            processInfoService.save(water);
            logger.info("[流程信息初始化]成功,sessionId:{},方法:{}", sessionId, methodName);
        } catch (Exception e) {
            water.setSuccess(Boolean.FALSE);
            water.setResult("[流程信息初始化]异常:" + e);
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
        Water water = variable.getWater();
        try {
            Long matterId = variable.getAskInfo().getMatterId();
            Matter matter = matterService.getBasicMatter(matterId);
            // 检查事项配置
            matterService.checkMatterConfig(matter, MatterEnum.OPEN.getStatus(), Boolean.FALSE);
            // 将事项信息设置到流程变量
            variable.getAskInfo().setMatter(matter);
            water.setResult("[检查事项配置]成功");
            processInfoService.save(water);
            logger.info("[检查事项配置]成功,sessionId:{},方法:{}", sessionId, methodName);
        } catch (Exception e) {
            water.setSuccess(Boolean.FALSE);
            water.setResult("[检查事项配置]异常:" + e);
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
        Water water = variable.getWater();
        try {
            // 检查申请信息
            askInfoService.checkAskInfo(variable.getAskInfo());
            water.setResult("[检查申请信息]成功");
            processInfoService.save(water);
            logger.info("[检查申请信息]成功,sessionId:{},方法:{}", sessionId, methodName);
        } catch (Exception e) {
            water.setSuccess(Boolean.FALSE);
            water.setResult("[检查申请信息]异常:" + e);
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
        Water water = variable.getWater();
        try {
            AskInfoVo askInfoVo = variable.getAskInfo();
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
            water.setResult("[初始化申请信息]成功");
            processInfoService.save(water);
            logger.info("[初始化申请信息]成功,sessionId:{},方法:{}", sessionId, methodName);
        } catch (Exception e) {
            water.setSuccess(Boolean.FALSE);
            water.setResult("[初始化申请信息]异常:" + e);
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
        Water water = variable.getWater();
        try {
            // 初始化审批版本数据
            AskInfo askInfo = variable.getAskInfo().getAskInfo();
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
            water.setResult("[初始化版本信息]成功");
            processInfoService.save(water);
            logger.info("[初始化版本信息]成功,sessionId:{},方法:{}", sessionId, methodName);
        } catch (Exception e) {
            water.setSuccess(Boolean.FALSE);
            water.setResult("[初始化版本信息]异常:" + e);
            logger.error("[初始化版本信息]异常,sessionId:{},方法:{},异常信息:{}" + e, sessionId, methodName);
            throw new BpmnError(ProcessConstant.BPMNER_ERROR_EXCEPTION);
        } finally {
            super.resetProcessVariable(delegateExecution, variable);
        }
    }

    /**
     * <说明> 环节审批
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/15 11:32
     * @return void
     */
    @Override
    public void approvalAskInfo(DelegateExecution delegateExecution) {

    }

    /**
     * <说明> 获取审批结果
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/15 11:32
     * @return void
     */
    @Override
    public void isApprovalPass(DelegateExecution delegateExecution) {

    }

    /**
     * <说明> execute
     * @param delegateExecution DelegateExecution
     * @author ChenWei
     * @date 2021/4/15 11:32
     * @return void
     */
    @Override
    public void execute(DelegateExecution delegateExecution) {
    }
}
