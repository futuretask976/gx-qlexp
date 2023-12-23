package gx.qlexp.support;

import com.google.common.collect.Lists;
import gx.qlexp.core.ExpContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author shenping
 * 2019-02-17
 */
@Component
public class ExpServiceInstance {
    private ExpService expService;

    @PostConstruct
    public void init() {
        ExpService expService = new ExpService();
        expService.setChannels(Lists.newArrayList("TEST_CHANNEL"));
        expService.init();
        this.expService = expService;
    }

    public boolean executeFilter(String script, ExpContext<String, Object> expContext, String bizCode) {
        try {
            return expService.execute(script, expContext, null, true, false);
        } catch (Throwable e) {
            return false;
        }
    }

//    public <T extends BaseBizObj> List<T> executeCompose(String script, ExpContext<String, Object> expContext) {
//        try {
//            return expService.execute(script, expContext, null, true, false);
//        } catch (Throwable e) {
//            return Lists.newArrayList();
//        }
//    }
}
