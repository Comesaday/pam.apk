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
public class UserApply implements Serializable {

    // 申请人ID
    private Long userId;

    // 申请人姓名
    private String userName;

    // 申请时间
    private Date askTime = new Date();

    // 申请事项id
    private Long matterId;

    private String matterCode;

    private Matter matter;

    private ApplyInfo applyInfo;

    private String sessionId;

    // 申请表单数据
    private List<ApplyFormData> askInfos;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
