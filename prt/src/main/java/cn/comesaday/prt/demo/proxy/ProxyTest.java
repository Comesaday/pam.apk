package cn.comesaday.prt.demo.proxy;

import cn.comesaday.prt.demo.proxy.cglib.CglibProxy;
import cn.comesaday.prt.demo.proxy.jdk.handler.MyInvocationHandler;
import cn.comesaday.prt.demo.proxy.jdk.service.TestService;
import cn.comesaday.prt.demo.proxy.jdk.service.impl.TestServiceImpl;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

/**
 * <描述>
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2020-11-10 16:55
 */
public class ProxyTest {

    public static void main(String[] args) throws Exception {
        // JDK动态代理
        TestService service = new TestServiceImpl();
        MyInvocationHandler handler = new MyInvocationHandler(service);
        TestService proxyService = (TestService) Proxy.newProxyInstance(
                service.getClass().getClassLoader(), service.getClass().getInterfaces(), handler);
        proxyService.say();

        // cglib动态代理
        CglibProxy proxy = new CglibProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TestServiceImpl.class);
        enhancer.setCallback(proxy);
        TestService proxyService1 = (TestService) enhancer.create();
        proxyService1.say();
    }
}
