package cn.comesaday.avt.business.water.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <描述> 流程记录表
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-01 18:52
 */
@Entity
@Table(name = "AVT_ASK_PROCESS")
public class Water extends IdEntity implements Serializable {

    // 申请主表id
    private String sessionId;

    // 流程实例id
    private String processId;

    // 重试次数
    private Integer times;

    // 执行结果
    private String result;

    // 扫描组装参数
    @Column(length = 3000)
    private String param;

    // 流程当前节点
    private String actId;

    // 流程当前节点
    private String actName;

    // 是否执行成功
    private Boolean success;

    public Water() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }
}
