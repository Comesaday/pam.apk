package cn.comesaday.avt.example.retry.aop;

import cn.comesaday.avt.example.retry.RetryTemplate;
import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <描述> RetryAspect
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-27 14:26
 */
@Aspect
@Component
public class RetryAspect {

    private static final int corePoolSize = 3;

    private static final int maximumPoolSize = 5;

    private static final int keepAliveTime = 1;

    private static final ExecutorService executorService = new ThreadPoolExecutor(
            corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    @Around(value = "@annotation(retry)")
    public JsonResult execute(ProceedingJoinPoint joinPoint, Retry retry) throws Exception {
        return new RetryTemplate() {
            @Override
            public JsonResult doService() throws Throwable {
                return (JsonResult) joinPoint.proceed();
            }
        }.setTime(retry.time()).setSleep(retry.sleep()).execute();
    }
}
