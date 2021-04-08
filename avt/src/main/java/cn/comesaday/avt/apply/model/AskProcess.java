package cn.comesaday.avt.apply.model;

import cn.comesaday.coe.core.basic.model.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <描述> 流程记录表
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-01 18:52
 */
@Entity
@Table(name = "AVT_ASK_PROCESS")
public class AskProcess extends IdEntity {

    // 申请主表id
    private Long askId;

    // 流程实例id
    private String processId;

    // 重试次数
    private Integer retryTimes;

    // 执行结果
    private String result;

    // 扫描组装参数
    private String param;

    // 是否执行成功
    private Boolean success;

    public AskProcess() {
    }

    public AskProcess(Long askId) {
        this.askId = askId;
    }

    public Long getAskId() {
        return askId;
    }

    public void setAskId(Long askId) {
        this.askId = askId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
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
}
