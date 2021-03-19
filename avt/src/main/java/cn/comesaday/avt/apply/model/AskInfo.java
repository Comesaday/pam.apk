package cn.comesaday.avt.apply.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> 申请主表
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-08 15:23
 */
@Entity
@Table(name = "AVT_ASK_INFO")
public class AskInfo extends IdEntity {

    // 申请人ID
    private Long applyId;

    // 申请人姓名
    private String applyName;

    // 事项ID
    private Long matterId;

    // 事项code
    private String matterCode;

    // 事项名称
    private String matterName;

    // 当前环节ID
    private Long curTrackId;

    // 流程实例ID
    private Long processId;

    // 0:暂存 1:发布
    private boolean publish;

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

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}
