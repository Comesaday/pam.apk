package cn.comesaday.avt.business.apply.service;

import cn.comesaday.avt.business.apply.model.AskInfo;
import cn.comesaday.avt.business.apply.model.AskInfoTrack;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <描述> AskInfoTrackService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-16 17:58
 */
@Transactional
@Service
public class AskInfoTrackService extends BaseService<AskInfoTrack, Long> {

    /**
     * <说明> 获取版本数据
     * @param askId 申请主数据id
     * @author ChenWei
     * @date 2021/4/8 11:22
     * @return AskInfoTrack
     */
    public AskInfoTrack getAskInfo(Long askId) {
        AskInfoTrack askInfoTrack = null;
        List<AskInfoTrack> trackList = this.findAllByProperty("askId", askId);
        if (CollectionUtils.isEmpty(trackList)) {
            askInfoTrack = new AskInfoTrack();
            askInfoTrack.setAskId(askId);
        } else {
            askInfoTrack = trackList.get(NumConstant.I0);
        }
        return askInfoTrack;
    }

    /**
     * <说明> 初始化版本数据
     * @param askInfo 申请主数据
     * @param execution 当前流程节点
     * @author ChenWei
     * @date 2021/4/8 11:08
     * @return AskInfoTrack
     */
    public AskInfoTrack initAskTrackInfo(AskInfo askInfo, DelegateExecution execution) {
        AskInfoTrack askInfoTrack = new AskInfoTrack();
        askInfoTrack.setAskId(askInfo.getId());
        askInfoTrack.setLinkCode(execution.getCurrentActivityId());
        askInfoTrack.setLinkName(execution.getCurrentFlowElement().getName());
        return this.save(askInfoTrack);
    }
}
