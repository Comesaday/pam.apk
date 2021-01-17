package cn.comesaday.prt.demo.proxy.jdk.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-10 16:24
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object object;

    public MyInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = method.invoke(this.object, args);
        long endTime = System.currentTimeMillis();
        System.out.println("方法耗时：" + (endTime - startTime));
        return object;
    }
}
