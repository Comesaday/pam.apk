package cn.comesaday.init;

import cn.comesaday.coe.common.util.JsonUtil;
import cn.comesaday.coe.core.basic.bean.app.BasicApplication;
import cn.comesaday.sso.api.model.Api;
import cn.comesaday.sso.api.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * <描述> 启动类
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-01-07 16:02
 */
public class InitApplication extends BasicApplication {

    private final static Logger logger = LoggerFactory.getLogger(BasicApplication.class);

    protected static void init(String[] args, Class clazz) {
        if (!BasicApplication.class.isAssignableFrom(clazz)) {
            logger.info("传入的类不被允许");
            return;
        }
        SpringApplication application = new SpringApplication(clazz);
        ConfigurableApplicationContext context = application.run(args);
        // 注册api
        ApiService apiService = context.getBean(ApiService.class);
        logger.info("============================初始化api开始============================");
        List<Api> apis = apiService.apis();
        logger.info("新注册api:{}", JsonUtil.toJsonIgnoreNull(apis));
        logger.info("============================初始化api结束============================");
    }
}
