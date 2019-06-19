package com.ghj.rpc.annotation;

import java.lang.annotation.*;

/**
 * 注解
 * @author GeHejun
 * @date 2019-06-18
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
