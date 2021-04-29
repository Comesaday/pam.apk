package cn.comesaday.avt.process.flow.variable;

import cn.comesaday.avt.business.apply.model.ApplyTrack;
import cn.comesaday.avt.business.apply.vo.UserApply;

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
    private UserApply userApply;

    // 审批记录
    private List<ApplyTrack> auditRecords;

    // 当前审批环节
    private String curLinkCode;

    // 检查结果
    private CheckInfo checkInfo;

    public ProcessVariable() {
        if (null == auditRecords) {
            this.auditRecords = new LinkedList<>();
        }
        if (null == checkInfo) {
            checkInfo = new CheckInfo();
        }
    }

    public ProcessVariable(String sessionId, UserApply userApply) {
        if (null == auditRecords) {
            this.auditRecords = new LinkedList<>();
        }
        if (null == checkInfo) {
            checkInfo = new CheckInfo();
        }
        this.sessionId = sessionId;
        this.userApply = userApply;
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

    public UserApply getUserApply() {
        return userApply;
    }

    public void setUserApply(UserApply userApply) {
        this.userApply = userApply;
    }

    public List<ApplyTrack> getAuditRecords() {
        return auditRecords;
    }

    public void setAuditRecords(List<ApplyTrack> auditRecords) {
        this.auditRecords = auditRecords;
    }

    public String getCurLinkCode() {
        return curLinkCode;
    }

    public void setCurLinkCode(String curLinkCode) {
        this.curLinkCode = curLinkCode;
    }

    public CheckInfo getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(CheckInfo checkInfo) {
        this.checkInfo = checkInfo;
    }
}
