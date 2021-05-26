package cn.comesaday.avt.example.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * <描述> TaskPoolConfig
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-26 19:51
 */
@Configuration
@EnableScheduling
public class TaskPoolConfig {

    @Bean
    public ThreadPoolTaskScheduler taskExecutor() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }
}
