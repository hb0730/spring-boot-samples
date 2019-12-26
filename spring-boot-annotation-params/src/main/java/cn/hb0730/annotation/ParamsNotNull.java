package cn.hb0730.annotation;

import java.lang.annotation.*;

/**
 * 参数注解不能为空
 *
 * @author bing_huang
 * @version v1.0
 * @date 2019/09/02
 * @see
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamsNotNull {
    String[] message() default {};
    // params name
    String[] name() default {};
}
