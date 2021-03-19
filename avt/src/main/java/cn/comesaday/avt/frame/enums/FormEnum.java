package cn.comesaday.avt.frame.enums;

/**
 * <描述> FormEnum
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-15 16:27
 */
public enum FormEnum {

    OPTION("OPTION", "单选型", ""),
    RADIO("RADIO", "单选型", ""),
    CHECKBOX("CHECKBOX", "多选型", ""),
    STRING("STRING", "字符型", ""),
    NUMBER("NUMBER", "整数型", ""),
    FLOAT("FLOAT", "单浮点型", ""),
    DOUBLE("DOUBLE", "双浮点型", "");

    private final String code;

    private final String name;

    private final String remark;

    FormEnum(String code, String name, String remark) {
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
