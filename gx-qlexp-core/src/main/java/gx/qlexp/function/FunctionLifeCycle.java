package gx.qlexp.function;

/**
 * @author shenping
 * 2019-01-31
 */
public interface FunctionLifeCycle {
    /**
     *
     */
    default void init(){}
    
    /**
     *
     */
    default void destroy(){}
}
