package cn.comesaday.avt.apply.service;

import cn.comesaday.avt.apply.model.AskInfoTrack;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.service.BaseService;
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
}
