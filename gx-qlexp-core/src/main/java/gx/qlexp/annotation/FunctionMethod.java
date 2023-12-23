package gx.qlexp.annotation;

import gx.qlexp.constant.Operators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author shenping
 * 2018-12-21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionMethod {
    Operators[] opera() default {};
    String desc();
    String render();
}
