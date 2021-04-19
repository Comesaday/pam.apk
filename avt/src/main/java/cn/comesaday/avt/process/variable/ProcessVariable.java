package cn.comesaday.avt.process.variable;

import cn.comesaday.avt.business.apply.model.AskInfoTrack;
import cn.comesaday.avt.business.apply.vo.AskInfoVo;
import cn.comesaday.avt.business.water.model.Water;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * <描述> ProcessVariable 流程变量
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-07 19:43
 */
public class ProcessVariable implements Serializable {

    // 流程实例id
    private String processId;

    // 申请信息
    private AskInfoVo askInfo;

    // 审批记录 new LinkedList<>()
    private List<AskInfoTrack> records;

    // 流程记录表
    private Water water;

    // sessionId
    private String sessionId = UUID.randomUUID().toString();

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public AskInfoVo getAskInfo() {
        return askInfo;
    }

    public void setAskInfo(AskInfoVo askInfo) {
        this.askInfo = askInfo;
    }

    public List<AskInfoTrack> getRecords() {
        return records;
    }

    public void setRecords(List<AskInfoTrack> records) {
        this.records = records;
    }

    public Water getWater() {
        return water;
    }

    public void setWater(Water water) {
        this.water = water;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
