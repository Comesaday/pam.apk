package cn.comesaday.avt.matter.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> 事项环节审批人
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-07 19:49
 */
@Entity
@Table(name = "AVT_MATTER_USER_SETTING")
public class MatterUserSetting extends IdEntity {

    private Long matterId;

    private String linkCode;

    private String linkName;

    private Long userId;

    private String userName;

    public Long getMatterId() {
        return matterId;
    }

    public void setMatterId(Long matterId) {
        this.matterId = matterId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
