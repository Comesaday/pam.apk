package cn.comesaday.avt.apply.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> 申请内容
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-08 20:07
 */
@Entity
@Table(name = "AVT_ASK_FORM_DATA")
public class AskFormData extends IdEntity {

    // 模板元素code
    private String elemCode;

    // 模板元素名称
    private String elemName;

    // 申请人填写
    private String userFill;

    public String getElemCode() {
        return elemCode;
    }

    public void setElemCode(String elemCode) {
        this.elemCode = elemCode;
    }

    public String getElemName() {
        return elemName;
    }

    public void setElemName(String elemName) {
        this.elemName = elemName;
    }

    public String getUserFill() {
        return userFill;
    }

    public void setUserFill(String userFill) {
        this.userFill = userFill;
    }
}
