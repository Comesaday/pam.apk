package cn.comesaday.avt.work.element.enums;

import cn.comesaday.coe.common.constant.NumConstant;

/**
 * <描述> element枚举
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-09 11:12
 */
public enum PEEnum {

    PROCESS_ELEMENT(NumConstant.I1, "ELEMENT", "流程元素"),
    PROCESS_CATEGORY(NumConstant.I2, "CATEGORY", "流程类别");

    private final int type;

    private final String code;

    private final String name;

    PEEnum(int type, String code, String name) {
        this.type = type;
        this.code = code;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
