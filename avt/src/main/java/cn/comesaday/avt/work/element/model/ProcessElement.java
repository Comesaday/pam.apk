package cn.comesaday.avt.work.element.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> 流程元素
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-08 20:02
 */
@Entity
@Table(name = "AVT_PROCESS_ELEMENT")
public class ProcessElement extends IdEntity {

    private String code;

    private String name;

    private String remark;

    private Long parentId;

    // 顶层元素-基本表单元素：1，流程类别：2，流程模板：3
    private int type;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
