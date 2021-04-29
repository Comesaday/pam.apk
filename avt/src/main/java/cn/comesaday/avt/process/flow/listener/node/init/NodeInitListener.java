package cn.comesaday.avt.process.flow.listener.node.init;

import cn.comesaday.avt.business.apply.model.ApplyInfo;
import cn.comesaday.avt.business.apply.model.ApplyTrack;
import cn.comesaday.avt.business.apply.service.ApplyService;
import cn.comesaday.avt.business.apply.service.ApplyTrackService;
import cn.comesaday.avt.business.water.model.Water;
import cn.comesaday.avt.business.water.service.WaterService;
import cn.comesaday.avt.process.flow.listener.node.AbstractNodeListener;
import cn.comesaday.avt.process.flow.variable.ProcessVariable;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <Description> NodeInitListener
 * @author ChenWei
 * @CreateAt 2021-04-22 21:30
 */
@Component
public class NodeInitListener extends AbstractNodeListener {

    // 日志打印
    private final static Logger logger = LoggerFactory.getLogger(NodeInitListener.class);

    @Autowired
    private ApplyTrackService applyTrackService;

    @Autowired
    private ApplyService applyService;

    @Autowired
    private WaterService waterService;

    /**
     * <说明> 审批节点初始化
     * @param delegateTask DelegateTask
     * @author ChenWei
     * @date 2021/4/23 14:38
     * @return void
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        ProcessVariable variable = super.getVariable(delegateTask);
        Water water = waterService.getProcessWater(variable.getSessionId());
        try {
            String linkCode = delegateTask.getTaskDefinitionKey();
            ApplyTrack applyTrack = new ApplyTrack();
            applyTrack.setAskId(variable.getUserApply().getAskId());
            applyTrack.setLinkCode(linkCode);
            applyTrack.setLinkName(delegateTask.getName());
            applyTrack = applyTrackService.save(applyTrack);
            // 更新流程变量数据
            variable.getAuditRecords().add(applyTrack);
            variable.setCurLinkCode(linkCode);
            // 更新主表
            this.updateMainInfo(variable.getUserApply().getApplyInfo(), applyTrack.getId());
            waterService.saveSuccess(water, variable, "审批节点初始化成功");
            logger.info("审批节点初始化成功,sessionId:{}", variable.getSessionId());
        } catch (Exception e) {
            waterService.saveSuccess(water, variable, "审批节点初始化异常:" + e.getMessage());
            logger.error("审批节点初始化异常:{}", e.getMessage(), e);
        } finally {
            super.resetVariable(delegateTask, variable);
        }
    }

    /**
     * <说明> 更新主表数据
     * @param applyInfo ApplyInfo
     * @param applyTrackId 版本id
     * @author ChenWei
     * @date 2021/4/28 14:25
     * @return void
     */
    @Override
    protected void updateMainInfo(ApplyInfo applyInfo, Long applyTrackId) {
        applyInfo.setCurTrackId(applyTrackId);
        applyService.save(applyInfo);
    }
}
