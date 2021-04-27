package cn.comesaday.avt.process.flow.variable;

import cn.comesaday.avt.business.apply.model.ApplyTrack;
import cn.comesaday.avt.business.apply.vo.UserApplyRequest;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * <描述> ProcessVariable 流程变量
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-07 19:43
 */
// 必需实现序列化，否则操作流程变量会失败
public class ProcessVariable implements Serializable {

    // sessionId
    private String sessionId;

    // 申请信息
    private UserApplyRequest userApplyRequest;

    // 审批记录
    private List<ApplyTrack> records;

    // 当前审批环节
    private String curLinkCode;

    // 检查结果
    private ApplyCheck applyCheck = new ApplyCheck(Boolean.TRUE);

    public ProcessVariable() {
        if (null == records) {
            this.records = new LinkedList<>();
        }
    }

    public ProcessVariable(String sessionId, UserApplyRequest userApplyRequest) {
        if (null == records) {
            this.records = new LinkedList<>();
        }
        this.sessionId = sessionId;
        this.userApplyRequest = userApplyRequest;
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

    public UserApplyRequest getUserApplyRequest() {
        return userApplyRequest;
    }

    public void setUserApplyRequest(UserApplyRequest userApplyRequest) {
        this.userApplyRequest = userApplyRequest;
    }

    public List<ApplyTrack> getRecords() {
        return records;
    }

    public void setRecords(List<ApplyTrack> records) {
        this.records = records;
    }

    public String getCurLinkCode() {
        return curLinkCode;
    }

    public void setCurLinkCode(String curLinkCode) {
        this.curLinkCode = curLinkCode;
    }

    public ApplyCheck getApplyCheck() {
        return applyCheck;
    }

    public void setApplyCheck(ApplyCheck applyCheck) {
        this.applyCheck = applyCheck;
    }
}
