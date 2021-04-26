package cn.comesaday.avt.business.apply.vo;

import cn.comesaday.avt.business.apply.model.ApplyFormData;
import cn.comesaday.avt.business.apply.model.ApplyInfo;
import cn.comesaday.avt.business.matter.model.Matter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <描述> 申请信息vo
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-17 14:37
 */
public class UserApplyRequest implements Serializable {

    // 申请人ID
    private Long applyId;

    // 申请人姓名
    private String applyName;

    // 申请时间
    private Date askTime = new Date();

    // 申请事项id
    private Long matterId;

    private String matterCode;

    private Matter matter;

    private Long askId;

    private ApplyInfo applyInfo;

    // 申请表单数据
    private List<ApplyFormData> askInfos;

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

    public List<ApplyFormData> getAskInfos() {
        return askInfos;
    }

    public void setAskInfos(List<ApplyFormData> askInfos) {
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

    public ApplyInfo getApplyInfo() {
        return applyInfo;
    }

    public void setApplyInfo(ApplyInfo applyInfo) {
        this.applyInfo = applyInfo;
    }

    public String getMatterCode() {
        return matterCode;
    }

    public void setMatterCode(String matterCode) {
        this.matterCode = matterCode;
    }
}
