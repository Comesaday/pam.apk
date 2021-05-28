package cn.comesaday.avt.example.retry.aop;

import cn.comesaday.avt.example.retry.RetryTemplate;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        System.out.println("========================AOP进入======================");
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
        System.out.println("========================AOP出去======================");
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
