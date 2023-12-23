package gx.qlexp.support;

import gx.qlexp.container.FunctionFactory;
import gx.qlexp.core.ExpRuntime;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author shenping
 * 2019-01-28
 */
@Component
public class FunctionSyncService {
    private static final Lock LOCK = new ReentrantLock();
    
    private volatile boolean initialized;
    
    private ExpRuntime expRuntime;

    public void init(ExpRuntime expRuntime) {
        initialized = false;
        this.expRuntime = expRuntime;
        this.sync();
        initialized = true;
    }

    private void sync() {
        doSync();
    }

    private void doSync() {
        LOCK.lock();
        try {
            loadFunction();
        } catch (Throwable t) {
            throw t;
        } finally {
            LOCK.unlock();
        }
    }

    private boolean loadFunction() {
        FunctionFactory factory = FunctionFactory.buildFactory(this.expRuntime);
        return factory.production();
    }
}
