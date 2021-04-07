package cn.comesaday.avt.apply.vo;

import cn.comesaday.avt.apply.model.AskFormData;
import cn.comesaday.avt.apply.model.AskInfo;
import cn.comesaday.avt.matter.model.Matter;

import java.util.Date;
import java.util.List;

/**
 * <描述> 申请信息vo
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-17 14:37
 */
public class AskInfoVo {

    // 申请人ID
    private Long applyId;

    // 申请人姓名
    private String applyName;

    // 申请时间
    private Date askTime;

    // 申请事项id
    private Long matterId;

    private Matter matter;

    private Long askId;

    private AskInfo askInfo;

    // 申请表单数据
    private List<AskFormData> askInfos;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Date getAskTime() {
        return askTime;
    }

    public void setAskTime(Date askTime) {
        this.askTime = askTime;
    }

    public Long getMatterId() {
        return matterId;
    }

    public void setMatterId(Long matterId) {
        this.matterId = matterId;
    }

    public List<AskFormData> getAskInfos() {
        return askInfos;
    }

    public void setAskInfos(List<AskFormData> askInfos) {
        this.askInfos = askInfos;
    }

    public Matter getMatter() {
        return matter;
    }

    public void setMatter(Matter matter) {
        this.matter = matter;
    }

    public Long getAskId() {
        return askId;
    }

    public void setAskId(Long askId) {
        this.askId = askId;
    }

    public AskInfo getAskInfo() {
        return askInfo;
    }

    public void setAskInfo(AskInfo askInfo) {
        this.askInfo = askInfo;
    }
}
