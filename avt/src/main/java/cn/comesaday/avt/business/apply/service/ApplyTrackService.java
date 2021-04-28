package cn.comesaday.avt.business.apply.service;

import cn.comesaday.avt.business.apply.model.ApplyInfo;
import cn.comesaday.avt.business.apply.model.ApplyTrack;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * <描述> AskInfoTrackService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-16 17:58
 */
@Transactional
@Service
public class ApplyTrackService extends BaseService<ApplyTrack, Long> {

    /**
     * <说明> 获取版本数据
     * @param askId 申请主数据id
     * @author ChenWei
     * @date 2021/4/8 11:22
     * @return AskInfoTrack
     */
    public ApplyTrack getAskInfo(Long askId) {
        ApplyTrack applyTrack = null;
        List<ApplyTrack> trackList = this.findAllByProperty("askId", askId);
        if (CollectionUtils.isEmpty(trackList)) {
            applyTrack = new ApplyTrack();
            applyTrack.setAskId(askId);
        } else {
            applyTrack = trackList.get(NumConstant.I0);
        }
        return applyTrack;
    }

    /**
     * <说明> 初始化版本数据
     * @param applyInfo 申请主数据
     * @param execution 当前流程节点
     * @author ChenWei
     * @date 2021/4/8 11:08
     * @return AskInfoTrack
     */
    public ApplyTrack saveTrackInfo(ApplyInfo applyInfo, DelegateExecution execution) {
        ApplyTrack applyTrack = new ApplyTrack();
        applyTrack.setAskId(applyInfo.getId());
        applyInfo.setApplyId(applyInfo.getApplyId());
        applyInfo.setApplyName(applyInfo.getApplyName());
        applyTrack.setLinkCode(execution.getCurrentActivityId());
        applyTrack.setLinkName(execution.getCurrentFlowElement().getName());
        applyTrack.setAgree(Boolean.TRUE);
        applyTrack.setCreateAt(applyInfo.getCreateAt());
        applyTrack.setCheckId(NumConstant.L100);
        applyTrack.setCheckName("系统");
        applyTrack.setComment("系统自动检查申请信息通过");
        applyTrack.setUpdateAt(new Date());
        return this.save(applyTrack);
    }
}
