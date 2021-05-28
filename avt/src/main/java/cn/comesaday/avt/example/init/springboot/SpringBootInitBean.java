package cn.comesaday.avt.example.init.springboot;

import cn.comesaday.avt.example.schedule.manual.ThreadPoolTaskSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

/**
 * <描述> SpringInitBean
 * <详细背景> springboot程序启动时遍历执行所有实现CommandLineRunner接口的实现类，@Order(value = 1)表示执行次序
 * @author: ChenWei
 * @CreateAt: 2021-05-27 09:42
 */
//@Component
@Order(value = 1) //
public class SpringBootInitBean implements CommandLineRunner {

    @Autowired
    private ThreadPoolTaskSchedulerService threadPoolTaskSchedulerService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("######################################");
        System.out.println("..............init start..............");
        threadPoolTaskSchedulerService.execute("99999999999");
        System.out.println("######################################");
    }
}
