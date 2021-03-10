package cn.comesaday.avt.apply.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> AskInfoTrack
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-09 17:39
 */
@Entity
@Table(name = "AVT_ASK_INFO_TRACK")
public class AskInfoTrack extends AskInfo {

    // 环节code
    private String linkCode;

    // 环节名称
    private String linkName;

    // 审核结果
    private Boolean isPass;

    // 审核备注
    private Boolean remark;

    // 审核人
    private Long checkId;

    // 审核人姓名
    private String checkName;

    public String getLinkCode() {
        return linkCode;
    }

    public void setLinkCode(String linkCode) {
        this.linkCode = linkCode;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public Boolean getPass() {
        return isPass;
    }

    public void setPass(Boolean pass) {
        isPass = pass;
    }

    public Boolean getRemark() {
        return remark;
    }

    public void setRemark(Boolean remark) {
        this.remark = remark;
    }

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }
}
