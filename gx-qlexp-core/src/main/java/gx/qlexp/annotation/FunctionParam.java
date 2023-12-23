package gx.qlexp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shenping
 * 2018-12-21
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionParam {
    boolean fromContext() default false;
    String desc() default "";
    String name();
    String prefix() default "";
    String suffix() default "";
    String option() default "";
}
