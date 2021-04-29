package cn.comesaday.avt.business.apply.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <描述> 申请主表
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-08 15:23
 */
@Entity
@Table(name = "AVT_APPLY_INFO")
public class ApplyInfo extends IdEntity implements Serializable {

    // 申请人ID
    private Long userId;

    // 申请人姓名
    private String userName;

    // 事项ID
    private Long matterId;

    // 事项code
    private String matterCode;

    // 事项名称
    private String matterName;

    // 当前环节ID
    private Long curTrackId;

    // 0:暂存 1:发布 2:处理中 3:处理结束
    private Integer status;

    // sessionId
    private String sessionId;

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

    public Long getMatterId() {
        return matterId;
    }

    public void setMatterId(Long matterId) {
        this.matterId = matterId;
    }

    public String getMatterCode() {
        return matterCode;
    }

    public void setMatterCode(String matterCode) {
        this.matterCode = matterCode;
    }

    public String getMatterName() {
        return matterName;
    }

    public void setMatterName(String matterName) {
        this.matterName = matterName;
    }

    public Long getCurTrackId() {
        return curTrackId;
    }

    public void setCurTrackId(Long curTrackId) {
        this.curTrackId = curTrackId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
