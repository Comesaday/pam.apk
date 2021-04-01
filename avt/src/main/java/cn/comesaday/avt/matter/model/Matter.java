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

    // 事项类型
    private String type;

    // 事项流程modelID
    private String modelId;

    // 事项流程部署ID
    private String deployId;

    // 0暂存 1保存 2已配置表单 3流程已定义 4流程已部署 5事项开启 6事项关闭
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }
}
