package cn.comesaday.sso.api.anno;

import java.lang.annotation.*;

/**
 * <描述> api注册方法注解
 * <详细背景>
 *
 * @author: ChenWei
 * @CreateAt: 2021-01-08 15:53
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiPush {

    String name() default "";

    String apiCode() default "";
}
