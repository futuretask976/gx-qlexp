package gx.qlexp.impl;

import com.google.common.collect.Maps;
import com.ql.util.express.IExpressContext;
import gx.qlexp.core.ExpContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author shenping
 * 2018-12-12
 */

@Component("qlContext")
@Scope("prototype")
public class QlExpContext implements IExpressContext<String, Object>, ExpContext<String, Object> {
    private ApplicationContext context;

    private Map<String, Object> map;

    public QlExpContext(ApplicationContext context) {
        this.context = context;
        this.map = Maps.newHashMap();
    }
    
    @Override
    public Object get(Object name) {
        String k = (String) name;
        try {
            Object result = map.get(k);
            if (result == null) {
                result = getFromContext(k);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    private Object getFromContext(String name) {
        if (this.context != null && this.context.containsBean(name)) {
            return this.context.getBean(name);
        }
        return null;
    }
    
    @Override
    public Object put(String s, Object o) {
        return map.put(s, o);
    }
    
    @Override
    public void clear() {
    }
}
