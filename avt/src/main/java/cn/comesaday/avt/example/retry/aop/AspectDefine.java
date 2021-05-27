package cn.comesaday.avt.example.retry.aop;

import cn.comesaday.coe.core.basic.bean.result.JsonResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <描述> DefinedRetry
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-27 17:02
 */
public interface AspectDefine {

    void webLog();

    void before(JoinPoint joinPoint);

    JsonResult around(ProceedingJoinPoint joinPoint, Retry retry) throws Exception;

    void after(JoinPoint joinPoint);

    void afterReturning(Object o);

    void afterThrowing();

}
