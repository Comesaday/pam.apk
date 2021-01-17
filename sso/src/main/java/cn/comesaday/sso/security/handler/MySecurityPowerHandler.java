package cn.comesaday.sso.security.handler;

import cn.comesaday.sso.security.constants.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import java.util.*;

/**
 * <描述> 白名单配置
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-30 16:55
 */
public class MySecurityPowerHandler {


    @Autowired
    private Resource whiteResource;

    /**
      * <说明> 静态资源放行
      * @param web WebSecurity
      * @author ChenWei
      * @date 2020/12/31 9:28
      * @return WebSecurity
      */
    public WebSecurity webHandle(WebSecurity web) throws Exception {
        Properties props = PropertiesLoaderUtils.loadProperties(whiteResource);
        List<String> list = new ArrayList<>();
        Set<Map.Entry<Object, Object>> entries = props.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            if (String.valueOf(entry.getKey()).contains(SecurityConstant.STATIC_RESOUECE)) {
                list.add(String.valueOf(entry.getValue()));
            }
        }
        String[] configs = new String[list.size()];
        list.toArray(configs);
        return web.ignoring().antMatchers(configs).and();
    }

    /**
      * <说明> 白名单url放行
      * @param http HttpSecurity
      * @author ChenWei
      * @date 2020/12/31 9:29
      * @return HttpSecurity
      */
    public HttpSecurity httpHandle(HttpSecurity http) throws Exception {
        Properties props = PropertiesLoaderUtils.loadProperties(whiteResource);
        List<String> list = new ArrayList<>();
        Set<Map.Entry<Object, Object>> entries = props.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            if (String.valueOf(entry.getKey()).contains(SecurityConstant.API_RESOUECE)) {
                list.add(String.valueOf(entry.getValue()));
            }
        }
        String[] configs = new String[list.size()];
        list.toArray(configs);
        return http.authorizeRequests().antMatchers(configs).permitAll().and();
    }
}
