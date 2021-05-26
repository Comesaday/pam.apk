package cn.comesaday.avt.example.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * <描述> CglibDynamicJavaDeveloperProxy
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-26 14:32
 */
public class CglibDynamicJavaDeveloperProxy implements MethodInterceptor {

    private final Enhancer enhancer = new Enhancer();

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("start...");
        Object invoke = methodProxy.invokeSuper(o, objects);
        System.out.println("end...");
        return invoke;
    }

    public Object newProxyInstance(Class<?> clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }
}
