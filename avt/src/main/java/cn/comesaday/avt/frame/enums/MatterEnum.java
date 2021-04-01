package cn.comesaday.avt.frame.enums;

import cn.comesaday.coe.common.constant.NumConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * <描述> MatterEnum
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-19 09:41
 */
public enum MatterEnum {

    TEMP(NumConstant.I0, "暂存"),
    SAVE(NumConstant.I1, "保存"),
    CONFIGURED(NumConstant.I2, "配置"),
    DEFINED(NumConstant.I4, "定义"),
    DEPLOY(NumConstant.I4, "部署"),
    OPEN(NumConstant.I5, "开启"),
    CLOSE(NumConstant.I6, "关闭");

    // 0暂存 1保存 2已配置表单 3流程已定义 4流程已部署 5事项开启 6事项关闭

    MatterEnum(Integer status, String remark) {
        this.status = status;
        this.remark = remark;
    }

    private final Integer status;

    private final String remark;

    private static final Map<Integer, String> map = new HashMap<Integer, String>() {
        {
            for (MatterEnum orderEnum : MatterEnum.values()) {
                put(orderEnum.getStatus(), orderEnum.getRemark());
            }
        }
    };

    public Integer getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public static String getCurStatus(Integer status) {
        return map.get(status);
    }
}
