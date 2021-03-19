package cn.comesaday.avt.frame.enums;

import cn.comesaday.coe.common.constant.NumConstant;

/**
 * <描述> MatterEnum
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-19 09:41
 */
public enum MatterEnum {

    TEMP(NumConstant.I0, "事项已暂存"),
    SAVE(NumConstant.I1, "事项已保存"),
    CONFIGURED(NumConstant.I2, "事项表单已配置"),
    DEFINED(NumConstant.I4, "事项流程已定义"),
    DEPLOY(NumConstant.I4, "事项流程已部署"),
    OPEN(NumConstant.I5, "事项流程已开启"),
    CLOSE(NumConstant.I6, "事项流程已关闭");

    // 0暂存 1保存 2已配置表单 3流程已定义 4流程已部署 5事项开启 6事项关闭

    MatterEnum(Integer status, String remark) {
        this.status = status;
        this.remark = remark;
    }

    private final Integer status;

    private final String remark;

    public Integer getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }
}
