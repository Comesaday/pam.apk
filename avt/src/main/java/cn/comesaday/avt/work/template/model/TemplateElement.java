package cn.comesaday.avt.work.template.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> 表单模板
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-08 20:00
 */
@Entity
@Table(name = "AVT_TEMPLATE_ELEMENT")
public class TemplateElement extends IdEntity {

    // 流程模板code
    private String templateCode;

    // 表单元素code
    private String elemCode;

    // 表单元素名称
    private String elemName;

    // 排序
    private int sort;

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getElemCode() {
        return elemCode;
    }

    public void setElemCode(String elemCode) {
        this.elemCode = elemCode;
    }

    public String getElemName() {
        return elemName;
    }

    public void setElemName(String elemName) {
        this.elemName = elemName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
