package cn.comesaday.avt.matter.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> Matter
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-18 16:13
 */
@Entity
@Table(name = "AVT_MATTER_INFO")
public class Matter extends IdEntity {

    private String code;

    private String name;

    private String remark;

    // 0暂存 1生成 2已配置表单 3已配置流程 4发布
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
