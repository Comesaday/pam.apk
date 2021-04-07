package cn.comesaday.avt.apply.vo;

import cn.comesaday.avt.apply.model.AskInfoTrack;

import java.util.List;

/**
 * <描述> ProcessVariable
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-07 19:43
 */
public class ProcessVariable {

    private String instanceId;

    private AskInfoVo askInfoVo;

    private List<AskInfoTrack> records;

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
}
