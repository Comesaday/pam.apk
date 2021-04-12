package cn.comesaday.avt.apply.vo;

import cn.comesaday.avt.apply.model.AskInfoTrack;
import cn.comesaday.avt.apply.model.AskProcess;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * <描述> ProcessVariable
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-07 19:43
 */
public class ProcessVariable implements Serializable {

    // 流程实例id
    private String instanceId;

    // 申请信息
    private AskInfoVo askInfoVo;

    // 审批记录
    private List<AskInfoTrack> records;

    // 流程记录表
    private AskProcess askProcess;

    // sessionId
    private String sessionId = UUID.randomUUID().toString();

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public AskInfoVo getAskInfoVo() {
        return askInfoVo;
    }

    public void setAskInfoVo(AskInfoVo askInfoVo) {
        this.askInfoVo = askInfoVo;
    }

    public List<AskInfoTrack> getRecords() {
        return records;
    }

    public void setRecords(List<AskInfoTrack> records) {
        this.records = records;
    }

    public AskProcess getAskProcess() {
        return askProcess;
    }

    public void setAskProcess(AskProcess askProcess) {
        this.askProcess = askProcess;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
