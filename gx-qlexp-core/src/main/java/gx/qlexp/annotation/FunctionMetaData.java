package gx.qlexp.annotation;

import gx.qlexp.constant.FunctionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author shenping
 * 2018-12-12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionMetaData {
    String value();
    FunctionType type();
}
