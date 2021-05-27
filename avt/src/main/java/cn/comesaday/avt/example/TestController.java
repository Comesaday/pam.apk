package cn.comesaday.avt.example;

import cn.comesaday.avt.example.collection.CollectionTest;
import cn.comesaday.avt.example.mode.celue.bean.FactoryList;
import cn.comesaday.avt.example.mode.celue.sevice.CelueService;
import cn.comesaday.avt.example.proxy.*;
import cn.comesaday.avt.example.retry.BuyTicketService;
import cn.comesaday.avt.example.schedule.manual.ThreadPoolTaskSchedulerService;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * <描述> TestController
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-25 14:12
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private FactoryList<CelueService, Integer> celueServiceFactory;

    @Autowired
    private ThreadPoolTaskSchedulerService threadPoolTaskSchedulerService;

    @Autowired
    private BuyTicketService modelBuyTicketServiceImpl;

    @Autowired
    private BuyTicketService aopBuyTicketServiceImpl;

    @RequestMapping("/mode/celue/{number}")
    public String test(@PathVariable(name = "number") Integer number) {
        return celueServiceFactory.getBean(number).apply();
    }

    @RequestMapping("/collection/test")
    public String test() {
        CollectionTest test = new CollectionTest();
        test.test();
        return "44444444";
    }

    @RequestMapping("/static/proxy")
    public void staticProxy() {
        StaticJavaDeveloperProxy proxy = new StaticJavaDeveloperProxy();
        proxy.debug();
        proxy.bug();
    }

    @RequestMapping("/jdk/proxy")
    public void jdkProxy() {
        Developer developer = new JavaDeveloper();
        InvocationHandler handler = new JdkDynamicJavaDeveloperProxy(developer);
        Developer proxy1 = (Developer) Proxy.newProxyInstance(developer.getClass().getClassLoader(),
                developer.getClass().getInterfaces(), handler);
        proxy1.debug();

        Developer proxy2 = (Developer) Proxy.newProxyInstance(developer.getClass().getClassLoader(),
                developer.getClass().getInterfaces(), ((proxy, method, args) -> {
            System.out.println("start...");
            Object invoke = method.invoke(developer, args);
            System.out.println("end...");
            return method.invoke(developer, invoke);
        }));
        proxy2.debug();
    }

    @RequestMapping("/cglib/proxy")
    public void cglibProxy() {
        CglibDynamicJavaDeveloperProxy cglibDynamicJavaDeveloperProxy = new CglibDynamicJavaDeveloperProxy();
        Developer developer = (JavaDeveloper) cglibDynamicJavaDeveloperProxy.newProxyInstance(JavaDeveloper.class);
        developer.debug();
    }

    @RequestMapping("/schedule/pool/start")
    public void scheduleStart(String message) {
        message = "schedule start...";
        threadPoolTaskSchedulerService.execute(message);
    }

    @RequestMapping("/schedule/pool/end")
    public void scheduleEnd() {
        threadPoolTaskSchedulerService.stop();
    }

    @RequestMapping("/retry/template")
    public JsonResult retry() throws Exception {
        return modelBuyTicketServiceImpl.buy();
    }

    @RequestMapping("/retry/aop")
    public JsonResult aop() throws Exception {
        return aopBuyTicketServiceImpl.buy();
    }
}
