package cn.comesaday.avt.matter.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> 表单模板
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-08 20:00
 */
@Entity
@Table(name = "AVT_MATTER_FIELD_SETTING")
public class MatterFieldSetting extends IdEntity {

    // 事项id
    private Long matterId;

    // 元素id
    private Long dictId;

    // 表单元素code
    private String dictCode;

    // 表单元素名称
    private String dictName;

    // 表现样式
    private String style;

    // 是否必填
    private Boolean required;

    // 排序
    private Integer sort;

    public Long getMatterId() {
        return matterId;
    }

    public void setMatterId(Long matterId) {
        this.matterId = matterId;
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

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
