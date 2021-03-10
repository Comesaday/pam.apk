package cn.comesaday.avt.apply.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> 申请主表
 * <详细背景>
 *
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

    // 流程类别code
    private String pcCode;

    // 申请内容id
    private Long askFormId;

    // 当前环节ID
    private Long curTrackId;

    // 流程实例ID
    private Long processId;

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

    public String getPcCode() {
        return pcCode;
    }

    public void setPcCode(String pcCode) {
        this.pcCode = pcCode;
    }

    public Long getAskFormId() {
        return askFormId;
    }

    public void setAskFormId(Long askFormId) {
        this.askFormId = askFormId;
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
}
