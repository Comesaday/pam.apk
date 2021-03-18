package cn.comesaday.avt.setting.dict.enums;

/**
 * <描述> DictEnum
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-15 16:27
 */
public enum DictEnum {

    INPUT("INPUT", "多选型", ""),
    OPTION("OPTION", "单选型", ""),
    OPTIONS("OPTIONS", "多选型", ""),
    STRING("STRING", "字符型", ""),
    NUMBER("NUMBER", "整数型", ""),
    FLOAT("FLOAT", "单浮点型", ""),
    DOUBLE("DOUBLE", "双浮点型", ""),

    PROCESS("PROCESS", "流程类别", ""),
    ASK_FOR_LEAVE("ASK_FOR_LEAVE", "员工请假", "");

    private final String code;

    private final String name;

    private final String remark;

    DictEnum(String code, String name, String remark) {
        this.code = code;
        this.name = name;
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }
}
