package gx.qlexp.helper;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import gx.qlexp.core.ExpContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/**
 * @author shenping
 * 2018-12-12
 */
public class ExpContextHelper {
    @Getter
    private static ClassPathXmlApplicationContext expContext;

    @Setter
    private static Properties properties;

    @Setter
    private static Set<String> beanNames;

    public static void init(ClassPathXmlApplicationContext expContext, Properties properties) {
        ExpContextHelper.beanNames = null;
        ExpContextHelper.expContext = expContext;
        ExpContextHelper.properties = properties;
    }

    public static Object getBean(String name) {
        return expContext.getBean(name);
    }

    @SuppressWarnings("unchecked")
    public static ExpContext<String, Object> buildContext() {
        return (ExpContext<String, Object>) expContext.getBean("qlContext", expContext);
    }

    public static String getProperty(String key) {
        return "" + properties.get(key);
    }

    public static Set<String> getBeanNames() {
        return Optional.ofNullable(beanNames)
                .orElseGet(Sets::newHashSet);
    }
}
