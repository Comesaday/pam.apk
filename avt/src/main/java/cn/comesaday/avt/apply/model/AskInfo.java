package cn.comesaday.avt.apply.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> 申请主表
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-08 15:23
 */
@Entity
@Table(name = "AVT_ASK_INFO")
public class AskInfo extends IdEntity {

    // 申请人ID
    private Long userId;

    // 申请人姓名
    private String userName;

    // 流程类别code
    private String pcCode;

    // 申请内容id
    private Long askFormId;

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

    public String getPcCode() {
        return pcCode;
    }

    public void setPcCode(String pcCode) {
        this.pcCode = pcCode;
    }
}
