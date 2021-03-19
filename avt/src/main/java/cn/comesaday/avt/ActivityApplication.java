package cn.comesaday.avt;

import cn.comesaday.coe.core.basic.bean.app.BasicApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class ActivityApplication extends BasicApplication {

    private final static Logger logger = LoggerFactory.getLogger(ActivityApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ActivityApplication.class, args);
    }

}
