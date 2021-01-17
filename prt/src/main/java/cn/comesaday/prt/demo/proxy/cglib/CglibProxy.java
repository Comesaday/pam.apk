package cn.comesaday.prt.demo.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-10 17:15
 */
public class CglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Hello,");
        Object object = methodProxy.invokeSuper(o, objects);
        System.out.println("Mr Chen!");
        return object;
    }
}
