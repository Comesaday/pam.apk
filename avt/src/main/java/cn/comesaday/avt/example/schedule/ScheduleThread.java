package cn.comesaday.avt.example.schedule;

/**
 * <描述> ScheduleThread
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-26 19:41
 */
public class ScheduleThread implements Runnable {

    private String message;

    public ScheduleThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(message);
    }
}
