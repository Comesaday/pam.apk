package cn.comesaday.avt.business.apply.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <描述> AskInfoTrack
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-09 17:39
 */
@Entity
@Table(name = "AVT_APPLY_INFO_TRACK")
public class ApplyTrack extends IdEntity implements Serializable {

    // 申请主表ID
    private Long askId;

    // 环节code
    private String linkCode;

    // 环节名称
    private String linkName;

    // 审核结果
    private Boolean agree;

    // 审核备注
    private String comment;

    // 审核人
    private Long checkId;

    // 审核人姓名
    private String checkName;

    public Long getAskId() {
        return askId;
    }

    public void setAskId(Long askId) {
        this.askId = askId;
    }

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

    public Boolean getAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
