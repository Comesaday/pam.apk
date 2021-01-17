package cn.comesaday.sso.security.handler;

import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.sso.api.model.Api;
import cn.comesaday.sso.api.service.ApiService;
import cn.comesaday.sso.role.model.Role;
import cn.comesaday.sso.role.service.RoleService;
import cn.comesaday.sso.security.constants.SecurityConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <描述> 动态鉴权
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-31 16:48
 */
public class MyFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ApiService apiService;

    @Autowired
    private Resource whiteResource;

    @Autowired
    private RoleService roleService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        // 白名单中的url无需鉴权
        if (interceptor(url)) return null;
        // 获取url访问权限
        Api api = apiService.getApiByUrl(url);
        if (null != api && StringUtils.isNotEmpty(api.getApiPowers())) {
            String[] powers = api.getApiPowers().split(SecurityConstant.SECURITY_SPLIT);
            List<String> pos = Arrays.asList(powers);
            List<ConfigAttribute> list = pos.stream().map(power
                    -> new SecurityConfig(SecurityConstant.SECURITY_ROLE + power))
                    .collect(Collectors.toList());
            return list;
        }
        return null;
    }

    private boolean interceptor(String url) {
        List<Object> list = Collections.EMPTY_LIST;
        try {
            Properties props = PropertiesLoaderUtils.loadProperties(whiteResource);
            Set<Map.Entry<Object, Object>> entries = props.entrySet();
            list = entries.stream().filter(entity
                    -> checkIgnore(entity.getValue(), url))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.size() > NumConstant.I0;
    }

    private Boolean checkIgnore(Object key, String url) {
        String whiteUrl = String.valueOf(key);
        if (whiteUrl.endsWith(SecurityConstant.SECURITY_ALL)) {
            whiteUrl = whiteUrl.substring(NumConstant.I0, whiteUrl.length() - NumConstant.I2);
        }
        return url.startsWith(whiteUrl) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        List<Role> roles = roleService.findAll();
        List<ConfigAttribute> list = roles.stream().map(role
                -> new SecurityConfig(SecurityConstant.SECURITY_ROLE + role.getCode()))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
