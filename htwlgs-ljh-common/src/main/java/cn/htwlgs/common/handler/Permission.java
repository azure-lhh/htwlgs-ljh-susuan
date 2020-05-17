package cn.htwlgs.common.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Permission
 * @Description
 * @Author lihouhai
 * @Date 2020/5/11 18:35
 * @Version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    String value() default "";
}
