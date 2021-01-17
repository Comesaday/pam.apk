package cn.comesaday.sso.security.config;

import cn.comesaday.sso.security.handler.*;
import cn.comesaday.sso.security.interceptor.DynamicallyUrlInterceptor;
import cn.comesaday.sso.security.manager.DynamicallyUrlAccessDecisionManager;
import cn.comesaday.sso.security.provider.LoginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * <描述> security config
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-30 11:08
 */
// 开启安全模式
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(dynamicallyUrlInterceptor(), FilterSecurityInterceptor.class);
        // 设置白名单
        securityWhiteHandler().httpHandle(http);
        // 设置访问权限
        http.authorizeRequests()
                .anyRequest().authenticated();
        // 开启自动配置的登录功能
        http.formLogin()
                .loginPage("/frame/login/page")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/index")
                .successHandler(loginSuccessHandler())
                .failureHandler(loginFailHandler());
        // 开启自动配置的注销功能
        http.logout()
                .logoutSuccessUrl("/frame/login/page");

        // X-Content-Type-Options头设置应许加载静态资源
        http.headers()
                .frameOptions()
                .sameOrigin();

        // 关闭跨域防御
        http.csrf()
                .disable();
        // 异常处理
        http.exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // 自定义登录认证
        auth.authenticationProvider(loginAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置白名单
        securityWhiteHandler().webHandle(web);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密算法
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MySecurityPowerHandler securityWhiteHandler() {
        // 配置文件读取放行资源
        return new MySecurityPowerHandler();
    }

    @Bean
    public AuthenticationProvider loginAuthenticationProvider() {
        // 登录逻辑处理器
        return new LoginAuthenticationProvider();
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler() {
        // 登录成功逻辑处理器
        return new MyLoginSuccessHandler();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler loginFailHandler() {
        // 登录失败逻辑处理器
        return new MyLoginFailHandler();
    }

    @Bean
    public DynamicallyUrlInterceptor dynamicallyUrlInterceptor(){
        // 动态拦截鉴权
        DynamicallyUrlInterceptor interceptor = new DynamicallyUrlInterceptor();
        interceptor.setSecurityMetadataSource(myFilterSecurityMetadataSource());
        //配置RoleVoter决策
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
        decisionVoters.add(new RoleVoter());
        //设置认证决策管理器
        interceptor.setAccessDecisionManager(new DynamicallyUrlAccessDecisionManager(decisionVoters));
        return interceptor;
    }

    @Bean
    public FilterInvocationSecurityMetadataSource myFilterSecurityMetadataSource() {
        // 自定义动态鉴权
        return new MyFilterSecurityMetadataSource();
    }

    @Bean
    public Resource securityWhiteResource() {
        // 读取白名单配置
        return new ClassPathResource("/white/white_url_donfig.properties");
    }

    @Bean
    public AccessDeniedHandler myAccessDeniedHandler() {
        // 自定义错误页面
        return new MyAccessDeniedHandler();
    }

}
