package org.smart4j.framework.annotation;

import java.lang.annotation.*;

/**
 * 定义切面注解
 * @author 10499
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     * @return
     */
    Class<? extends Annotation> value();
}
