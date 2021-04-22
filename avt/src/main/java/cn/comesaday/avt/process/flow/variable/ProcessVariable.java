package cn.comesaday.avt.process.flow.variable;

import cn.comesaday.avt.business.apply.model.ApplyTrack;
import cn.comesaday.avt.business.apply.vo.AskInfoVo;

import java.io.Serializable;
import java.util.List;

/**
 * <描述> ProcessVariable 流程变量
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-07 19:43
 */
public class ProcessVariable implements Serializable {

    // sessionId
    private String sessionId;

    // 申请信息
    private AskInfoVo applyInfo;

    // 审批记录
    private List<ApplyTrack> records;

    public ProcessVariable() {
    }

    public ProcessVariable(String sessionId, AskInfoVo applyInfo) {
        this.sessionId = sessionId;
        this.applyInfo = applyInfo;
    }

    public ProcessVariable(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public AskInfoVo getApplyInfo() {
        return applyInfo;
    }

    public void setApplyInfo(AskInfoVo applyInfo) {
        this.applyInfo = applyInfo;
    }

    public List<ApplyTrack> getRecords() {
        return records;
    }

    public void setRecords(List<ApplyTrack> records) {
        this.records = records;
    }
}
