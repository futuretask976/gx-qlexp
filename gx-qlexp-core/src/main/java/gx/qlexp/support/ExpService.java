package gx.qlexp.support;

import gx.qlexp.core.ExpContext;
import gx.qlexp.core.ExpRuntime;
import gx.qlexp.helper.ExpContextHelper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;


/**
 * @author shenping
 * 2018-12-11
 */
@Slf4j
public class ExpService {
    @Setter
    private List<String> channels;
    
    @Setter
    private String propertiesLocation;
    
    private ExpRuntime expRuntime;

    public void init() {
        Properties properties;
        try {
            // TODO 文件为空
            properties = PropertiesLoaderUtils.loadAllProperties("classpath:"
                    + Optional.ofNullable(propertiesLocation)
                    .orElse("exp-config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("读取配置文件出错", e);
        }
        ClassPathXmlApplicationContext expContext = new ClassPathXmlApplicationContext("classpath:qlexp-spring.xml");
        ExpContextHelper.init(expContext, properties);
        this.expRuntime = expContext.getBean(ExpRuntime.class);
        this.expRuntime.init();
        FunctionSyncService functionSyncService = expContext.getBean(FunctionSyncService.class);
        functionSyncService.init(expRuntime);
    }

    @SuppressWarnings("unchecked")
    public <T> T execute(String expressString, ExpContext<String, Object> context, List<String> errorList,
                         boolean isCache, boolean isTrace) throws Exception {
        if (expRuntime == null) {
            throw new RuntimeException("init error,make sure you call init method", null);
        }
        T t = (T) expRuntime.execute(expressString, context, errorList, isCache, isTrace);
        context.clear();
        return t;
    }
}
