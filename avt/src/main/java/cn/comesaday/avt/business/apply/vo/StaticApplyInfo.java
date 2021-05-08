package cn.comesaday.avt.business.apply.vo;

import cn.comesaday.avt.business.apply.model.ApplyTrack;

import java.util.Date;
import java.util.List;

/**
 * <描述> 静态获取申请信息
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-08 09:21
 */
public class StaticApplyInfo {

    // 获取时间
    private Date time;

    // 申请信息
    private UserApply userApply;

    // 审批记录
    private List<ApplyTrack> auditRecords;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
}
