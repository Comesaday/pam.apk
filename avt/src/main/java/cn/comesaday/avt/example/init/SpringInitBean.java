package cn.comesaday.avt.example.init;

import cn.comesaday.avt.example.schedule.manual.ThreadPoolTaskSchedulerService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * <描述> SpringInitBean
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-27 10:07
 */
//@Component
public class SpringInitBean implements ApplicationContextAware, InitializingBean,
        ServletContextAware, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ThreadPoolTaskSchedulerService threadPoolTaskSchedulerService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        threadPoolTaskSchedulerService.execute("SpringInitBean ApplicationContextAware.....");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        threadPoolTaskSchedulerService.execute("SpringInitBean InitializingBean.....");
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        threadPoolTaskSchedulerService.execute("SpringInitBean ServletContextAware.....");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        threadPoolTaskSchedulerService.execute("SpringInitBean ApplicationListener.....");
    }
}
