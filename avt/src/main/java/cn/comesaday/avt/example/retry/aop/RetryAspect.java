package cn.comesaday.avt.example.retry.aop;

import cn.comesaday.avt.example.retry.RetryTemplate;
import cn.comesaday.coe.common.util.JsonUtil;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * <描述> RetryAspect
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-27 14:26
 */
@Aspect
@Component
public class RetryAspect implements AspectDefine {

    private static final Logger logger = LoggerFactory.getLogger(RetryAspect.class);

    @Pointcut("execution(* cn.comesaday.avt.example..*.*(..))")
    @Override
    public void webLog() {

    }


    @Before("webLog()")
    @Override
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == requestAttributes) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        //打印请求内容
        System.out.println("========================请求内容======================");
        System.out.println("请求地址:" + request.getRequestURI().toString());
        System.out.println("请求方式" + request.getMethod());
        System.out.println("请求类方法" + joinPoint.getSignature());
        System.out.println("请求类方法参数" + Arrays.toString(joinPoint.getArgs()));
        System.out.println("========================请求内容======================");
    }

    @Around("@annotation(retry)")
    @Override
    public JsonResult around(ProceedingJoinPoint joinPoint, Retry retry) throws Exception {
        return new RetryTemplate() {
            @Override
            public JsonResult doService() throws Throwable {
                return (JsonResult) joinPoint.proceed();
            }
        }.setTime(retry.time()).setSleep(retry.sleep()).execute();
    }

    @AfterReturning(returning = "o", pointcut = "webLog()")
    @Override
    public void afterReturning(Object o) {
        if (null == o) {
            return;
        }
        System.out.println("--------------返回内容----------------");
        System.out.println("Response内容:" + JsonUtil.toJson(o));
        System.out.println("--------------返回内容----------------");
    }

    @After("webLog()")
    @Override
    public void after(JoinPoint joinPoint) {

    }

    @AfterThrowing("webLog()")
    @Override
    public void afterThrowing() {

    }
}
