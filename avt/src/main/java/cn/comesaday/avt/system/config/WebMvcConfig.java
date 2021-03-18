package cn.comesaday.avt.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <描述> WebMvcConfig
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-03-09 17:22
 */

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 根路径跳转
        registry.addViewController("/").setViewName("forward:/frame/index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 工作流配置相关页面
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        // 其他流程业务页面
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
    }
}
