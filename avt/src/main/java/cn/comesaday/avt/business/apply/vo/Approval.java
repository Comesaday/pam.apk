package cn.comesaday.avt.business.apply.vo;

/**
 * <描述> 审批内容
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-23 11:10
 */
public class Approval {

    // 任务ID
    private String taskId;

    // 是否同意
    private Boolean agree;

    // 审批意见
    private String comment;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Boolean getAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
