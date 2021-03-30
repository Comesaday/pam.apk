package cn.comesaday.avt.apply.service;

import cn.comesaday.avt.apply.model.AskFormData;
import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.avt.apply.vo.AskInfoVo;
import cn.comesaday.avt.matter.model.Matter;
import cn.comesaday.avt.matter.service.MatterService;
import cn.comesaday.coe.core.basic.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <描述> AskInfoService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-08 15:22
 */
@Transactional
@Service
public class AskInfoService extends BaseService<AskInfo, Long> {

    @Autowired
    private MatterService matterService;

    @Autowired
    private AskFormDataService askFormDataService;

    /**
     * <说明> 事项申请
     * @param askInfoVo 申请信息
     * @author ChenWei
     * @date 2021/3/29 19:52
     * @return cn.comesaday.avt.apply.model.AskInfo
     */
    public AskInfo apply(AskInfoVo askInfoVo) {
        // 初始化申请主表
        AskInfo askInfo = this.initAskMainInfo(askInfoVo);
        // 保存申请表单信息
        this.saveFormData(askInfoVo.getAskInfos(), askInfo.getId());
        return null;
    }

    /**
     * <说明> 保存申请表单信息
     * @param askInfos 表单填写内容
     * @param askInfoId 主表id
     * @author ChenWei
     * @date 2021/3/29 19:57
     * @return void
     */
    private void saveFormData(List<AskFormData> askInfos, Long askInfoId) {
        for (AskFormData askFormData : askInfos) {
            askFormData.setAskId(askInfoId);
            askFormDataService.save(askFormData);
        }
    }

    /**
     * <说明> 初始化事项主表信息
     * @param askInfoVo 申请信息
     * @author ChenWei
     * @date 2021/3/29 19:52
     * @return cn.comesaday.avt.apply.model.AskInfo
     */
    public AskInfo initAskMainInfo(AskInfoVo askInfoVo) {
        Matter matter = matterService.findOne(askInfoVo.getMatterId());
        AskInfo askInfo = new AskInfo();
        askInfo.setMatterCode(matter.getCode());
        askInfo.setMatterId(matter.getId());
        askInfo.setMatterName(matter.getName());
        askInfo.setApplyId(null);
        askInfo.setApplyName(null);
        askInfo.setPublish(Boolean.FALSE);
        return this.save(askInfo);
    }
}
