package gx.qlexp.impl;

import com.ql.util.express.ExpressRunner;
import com.ql.util.express.ExpressUtil;
import com.ql.util.express.IExpressContext;
import gx.qlexp.impl.ext.QlExtJoiner;
import gx.qlexp.core.ExpContext;
import gx.qlexp.core.ExpRuntime;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author shenping
 * 2018-12-12
 */

@Component
public class QlExpressImpl implements ExpRuntime {
    private ExpressRunner expressRunner = new ExpressRunner();
    
    @Override
    @SuppressWarnings("unchecked")
    public Object execute(String expressString, ExpContext<String, Object> context
            , List<String> errorList
            , boolean isCache
            , boolean isTrace) throws Exception {
        return expressRunner.execute(expressString, (IExpressContext<String, Object>) context, errorList, isCache, isTrace);
    }
    
    @Override
    public void init() {
        try {
            expressRunner.addOperator("join", new QlExtJoiner());
        } catch (Exception e) {
            throw new RuntimeException("join²Ù×÷·ûÌí¼ÓÊ§°Ü", e);
        }
    }
    
    @Override
    public void clear() {
        ExpressUtil.methodCache.clear();
    }
}
