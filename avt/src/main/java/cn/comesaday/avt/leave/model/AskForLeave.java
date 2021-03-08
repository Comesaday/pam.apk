package cn.comesaday.avt.leave.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> 请假申请主表
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-08 15:23
 */
@Entity
@Table(name = "ask_for_leave")
public class AskForLeave extends IdEntity {

    private Long userId;

    @Column
    private String userName;

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
