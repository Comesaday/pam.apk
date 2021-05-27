package cn.comesaday.avt.example.retry.aop;

import java.lang.annotation.*;

/**
 * <描述> Retry
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-05-27 14:22
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {

    int time() default 3;

    long sleep() default 0;

}
