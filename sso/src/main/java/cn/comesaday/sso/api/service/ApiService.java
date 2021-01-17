package cn.comesaday.sso.api.service;

import cn.comesaday.coe.common.constant.NumConstant;
import cn.comesaday.coe.core.basic.service.BaseService;
import cn.comesaday.sso.api.anno.ApiPush;
import cn.comesaday.sso.api.manager.ApiManager;
import cn.comesaday.sso.api.model.Api;
import cn.comesaday.sso.security.constants.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-12-31 15:50
 */
@Service
public class ApiService extends BaseService<Api, Long> {

    @Autowired
    private ApiManager apiManager;

    @Autowired
    private RequestMappingHandlerMapping mapping;

    public List<Api> apis() {
        List<Api> apis = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            Method method = handlerMethod.getMethod();
            if (!method.isAnnotationPresent(ApiPush.class)) {
                continue;
            }
            // 有注册注解的注册到表中
            Api api = new Api();
            ApiPush apiPush = method.getAnnotation(ApiPush.class);
            api.setName(apiPush.name());
            api.setApiCode(apiPush.apiCode());
            // url信息
            List<String> urls = new ArrayList<>(mappingInfo.getPatternsCondition().getPatterns());
            if (!CollectionUtils.isEmpty(urls)) {
                api.setUrl(urls.get(NumConstant.I0));
            }
            // 方法类型信息
            List<RequestMethod> methods = new ArrayList<>(mappingInfo.getMethodsCondition().getMethods());
            String ms = methods.stream().map(type -> type.toString())
                    .collect(Collectors.joining(SecurityConstant.SECURITY_SPLIT));
            api.setMethod(ms);
            apis.add(api);
        }
        // 判断处理
        List<Api> list = apis.stream().map(api -> dealApi(api)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            super.saveAll(list);
        }
        return list;
    }

    public Api dealApi(Api api) {
        Api tempApi = new Api(api.getUrl());
        Example<Api> example = Example.of(tempApi);
        Api exist = this.apiManager.findOne(example).orElse(null);
        if (null != exist) {
            api.setId(exist.getId());
        }
        return api;
    }

    public Api getApiByUrl(String url) {
        Api tempApi = new Api(url);
        Example<Api> example = Example.of(tempApi);
        return this.apiManager.findOne(example).orElse(null);
    }
}
