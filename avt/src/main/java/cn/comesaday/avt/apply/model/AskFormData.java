package cn.comesaday.avt.apply.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <描述> 申请内容
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-08 20:07
 */
@Entity
@Table(name = "AVT_FORM_DATA")
public class AskFormData extends IdEntity implements Serializable {

    // 申请主表ID
    private Long askId;

    // 元素ID
    private Long dictId;

    // 模板元素code
    private String dictCode;

    // 模板元素名称
    private String dictName;

    // 表现样式
    private String style;

    // 申请人填写
    private String userFill;

    public Long getAskId() {
        return askId;
    }

    public void setAskId(Long askId) {
        this.askId = askId;
    }

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getUserFill() {
        return userFill;
    }

    public void setUserFill(String userFill) {
        this.userFill = userFill;
    }
}
