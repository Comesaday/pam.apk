package cn.comesaday.avt.example.schedule;

/**
 * <描述> SchedulePlan
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-26 19:40
 */
public interface SchedulePlan {

    void execute(String message);

    void stop();
}
