package cn.comesaday.avt.example.schedule.auto;

import cn.comesaday.avt.example.schedule.ScheduleThread;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * <描述> ScheduleRegisterService
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-27 16:35
 */
//@Configuration
//@EnableScheduling
public class AutoScheduleRegisterConfig implements SchedulingConfigurer {

    private static final String CRON = "0/5 * * * * ?";

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ScheduleThread thread = new ScheduleThread("888");
        taskRegistrar.addCronTask(thread, CRON);
    }
}
