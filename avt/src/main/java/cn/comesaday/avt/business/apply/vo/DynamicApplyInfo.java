package cn.comesaday.avt.business.apply.vo;

import cn.comesaday.avt.business.user.model.User;

import java.util.List;

/**
 * <描述> 动态获取申请信息
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-08 09:30
 */
public class DynamicApplyInfo {

    // 静态申请信息
    private StaticApplyInfo staticApplyInfo;

    // 当前环节
    private String curLinkCode;

    // 当前环节审批人
    private List<User> auditPersons;

    public StaticApplyInfo getStaticApplyInfo() {
        return staticApplyInfo;
    }

    public void setStaticApplyInfo(StaticApplyInfo staticApplyInfo) {
        this.staticApplyInfo = staticApplyInfo;
    }

    public String getCurLinkCode() {
        return curLinkCode;
    }

    public void setCurLinkCode(String curLinkCode) {
        this.curLinkCode = curLinkCode;
    }

    public List<User> getAuditPersons() {
        return auditPersons;
    }

    public void setAuditPersons(List<User> auditPersons) {
        this.auditPersons = auditPersons;
    }
}
