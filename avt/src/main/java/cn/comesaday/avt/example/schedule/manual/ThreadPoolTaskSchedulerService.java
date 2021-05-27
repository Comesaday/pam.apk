package cn.comesaday.avt.example.schedule.manual;

import cn.comesaday.avt.example.schedule.ScheduleThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

/**
 * <描述> ThreadPoolTaskSchedulerService
 * <详细背景> dynamic schedule动态定时计划。使用前配置ThreadPoolTaskScheduler bean，开启EnableScheduling
 * @author: ChenWei
 * @CreateAt: 2021-05-26 19:37
 */
@Service
public class ThreadPoolTaskSchedulerService implements SchedulePlan {

    private static final String CRON = "0/5 * * * * ?";

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> schedule;

    @Override
    public void execute(String message) {
        ScheduleThread thread = new ScheduleThread(message);
        schedule = threadPoolTaskScheduler.schedule(thread, new CronTrigger(CRON));
    }

    @Override
    public void stop() {
        if (!schedule.isCancelled()) {
            schedule.cancel(true);
        }
    }
}
