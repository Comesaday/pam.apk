package cn.comesaday.avt.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <描述> JdkDynamicJavaDeveloperProxy
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-26 14:09
 */
public class JdkDynamicJavaDeveloperProxy implements InvocationHandler {

    private Developer developer;

    public JdkDynamicJavaDeveloperProxy(Developer developer) {
        this.developer = developer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start...");
        Object invoke = method.invoke(developer, args);
        System.out.println("end...");
        return invoke;
    }
}
