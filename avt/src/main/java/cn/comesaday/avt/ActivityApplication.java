package cn.comesaday.avt;

import cn.comesaday.coe.core.basic.bean.app.BasicApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

public class ActivityApplication extends BasicApplication {

    private final static Logger logger = LoggerFactory.getLogger(ActivityApplication.class);

    public static void main(String[] args) {
        logger.info("ActivityApplication 启动开始");
        SpringApplication.run(ActivityApplication.class, args);
    }

}
