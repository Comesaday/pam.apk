package cn.comesaday.avt.dict.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> 数据字典
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-08 20:02
 */
@Entity
@Table(name = "AVT_DICT")
public class Dict extends IdEntity {

    private String code;

    private String name;

    private String remark;

    private Long parentId;

    // 字段样式
    private String style;

    // 扩展信息id
    private Long extId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Long getExtId() {
        return extId;
    }

    public void setExtId(Long extId) {
        this.extId = extId;
    }
}
