package gx.qlexp.container;

import gx.qlexp.function.FunctionLifeCycle;

/**
 * @author shenping
 * 2018-12-12
 */

public class ExpClassLoader extends ClassLoader {
    public ExpClassLoader() {
        super(Thread.currentThread().getContextClassLoader());
    }

    @SuppressWarnings("unchecked")
    public Class<? extends FunctionLifeCycle> defineClass(String className, byte[] classByte) {
        return (Class<? extends FunctionLifeCycle>) defineClass(className, classByte, 0, classByte.length);
    }

    public Class<?> defineInnerClass(String className, byte[] classByte) {
        if (!className.contains("$")) {
            throw new RuntimeException("load outer class in inner class loader");
        }
        return defineClass(className, classByte, 0, classByte.length);
    }
}
