package cn.comesaday.avt.apply.service;

import cn.comesaday.avt.apply.model.AskFormData;
import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.avt.apply.vo.AskInfoVo;
import cn.comesaday.avt.matter.model.Matter;
import cn.comesaday.avt.matter.service.MatterService;
import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.exception.PamException;
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
    public AskInfoVo apply(AskInfoVo askInfoVo) throws Exception {
        Matter matter = matterService.getBasicMatter(askInfoVo.getMatterId());
        // 检查事项
        matterService.checkMatterConfig(matter, NumConstant.I5, Boolean.FALSE);
        // 保存申请表单信息
        List<AskFormData> datas = askFormDataService.saveAll(askInfoVo.getAskInfos());
        // 初始化申请主表
        AskInfo askInfo = this.initAskMainInfo(askInfoVo, matter);
        // 表单数据&主表关联
        datas.forEach(data -> data.setAskId(askInfo.getId()));
        askFormDataService.saveAll(datas);
        askInfoVo.setMatter(matter);
        askInfoVo.setAskInfos(datas);
        return askInfoVo;
    }

    /**
     * <说明> 初始化事项主表信息
     * @param askInfoVo 申请信息
     * @author ChenWei
     * @date 2021/3/29 19:52
     * @return cn.comesaday.avt.apply.model.AskInfo
     */
    public AskInfo initAskMainInfo(AskInfoVo askInfoVo, Matter matter) {
        AskInfo askInfo = new AskInfo();
        askInfo.setMatterCode(matter.getCode());
        askInfo.setMatterId(matter.getId());
        askInfo.setMatterName(matter.getName());
        askInfo.setApplyId(null);
        askInfo.setApplyName(null);
        askInfo.setStatus(NumConstant.I1);
        return this.save(askInfo);
    }

    /**
     * <说明> 查询申请信息
     * @param askId Long
     * @author ChenWei
     * @date 2021/4/1 17:35
     * @return cn.comesaday.avt.apply.vo.AskInfoVo
     */
    public AskInfoVo queryDetail(Long askId) throws PamException {
        AskInfoVo askInfoVo = new AskInfoVo();
        AskInfo askInfo = this.findOne(askId);
        if (null == askInfo) {
            throw new PamException("申请信息不存在");
        }
        askInfoVo.setAskId(askId);
        askInfoVo.setAskInfo(askInfo);
        askInfoVo.setMatter(matterService.findOne(askInfo.getMatterId()));
        askInfoVo.setApplyId(askInfo.getApplyId());
        askInfoVo.setApplyName(askInfo.getApplyName());
        askInfoVo.setAskTime(askInfo.getCreateAt());
        askInfoVo.setAskInfos(askFormDataService.findAllByProperty("askId", askInfo.getId()));
        return askInfoVo;
    }
}
