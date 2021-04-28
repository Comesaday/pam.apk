package cn.comesaday.avt.process.flow.variable;

import cn.comesaday.coe.common.constant.NumConstant;

import java.io.Serializable;

/**
 * <描述> ApplyCheck
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-27 19:43
 */
public class ApplyCheck implements Serializable {

    private Boolean checked;

    private Integer code = NumConstant.I200;

    private String reason;

    public ApplyCheck(Boolean checked) {
        this.checked = checked;
    }

    public ApplyCheck(Boolean checked, Integer code, String reason) {
        this.checked = checked;
        this.code = code;
        this.reason = reason;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setNotChecked(String reason) {
        this.checked = Boolean.FALSE;
        this.code = NumConstant.I400;
        this.reason = reason;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
