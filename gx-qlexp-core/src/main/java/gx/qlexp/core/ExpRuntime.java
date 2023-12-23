package gx.qlexp.core;

import java.util.List;

/**
 * @author shenping
 * 2018-12-12
 */
public interface ExpRuntime {
    Object execute(String expressString, ExpContext<String, Object> context, List<String> errorList, boolean isCache, boolean isTrace) throws Exception;
    void init();
    void clear();
}