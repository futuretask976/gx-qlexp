package gx.qlexp.container;

import gx.qlexp.helper.ExpContextHelper;
import lombok.Builder;
import lombok.Data;
import gx.qlexp.core.ExpRuntime;
import gx.qlexp.function.FunctionLifeCycle;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author shenping
 * 2018-12-15
 */
@Builder
public class FunctionFactory {
    private ExpClassLoader classLoader;
    private Set<String> oldBeanNameSet;
    private Set<String> newBeanNameSet;
    private Map<String, ClassObj> classObjMap;
    private Boolean success;
    private ExpRuntime expRuntime;
    private DefaultListableBeanFactory defaultListableBeanFactory;

    public static FunctionFactory buildFactory(ExpRuntime expRuntime) {
        return FunctionFactory.builder()
                .newBeanNameSet(ExpContextHelper.getBeanNames())
                .classLoader(new ExpClassLoader())
                .defaultListableBeanFactory((DefaultListableBeanFactory) ExpContextHelper.getExpContext().getBeanFactory())
                .success(Boolean.TRUE)
                .expRuntime(expRuntime)
                .build();
    }

    public boolean production() {
        // 加载class
        loadFunctionClass();
        // 实例化
        newInstance();
        // 填充字段
        fullFields();
        // 调用初始化方法
        init();
        // 加载到spring容器中
        loadIntoSpringContext();
        return true;
    }

    private void loadIntoSpringContext() {
        // 加载新bean到容器中
        loadNewBeans();
        // 移除旧bean
        removeOldBeans();
    }

    private void removeOldBeans() {
        expRuntime.clear();
        ExpContextHelper.setBeanNames(this.newBeanNameSet);
    }

    private void loadNewBeans() {
        classObjMap.forEach((k, v) -> defaultListableBeanFactory.registerSingleton(buildBeanName(k), v.getObj()));
    }

    private String buildBeanName(String beanName) {
        newBeanNameSet.add(beanName);
        return beanName;
    }

    private void loadFunctionClass() {
//        this.classObjMap = functionDataDTOS.stream()
//                .peek(functionDataDTO -> functionDataDTO.getInnerClasses()
//                        .forEach(innerClass -> classLoader.defineInnerClass(innerClass.getClassName(), innerClass.getFunctionData())))
//                .collect(Collectors.toMap(FunctionDataDTO::getFunctionName
//                        , functionDataDTO -> ClassObj.builder()
//                            .clazz(classLoader.defineClass(functionDataDTO.getClassName(), functionDataDTO.getFunctionData()))
//                            .build(),
//                        (o, n) -> n
//                ));
    }

    private void newInstance() {
        this.classObjMap.values()
                .forEach(classObj -> {
                    try {
                        classObj.setObj(classObj.getClazz().newInstance());
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                });
    }

    private void fullFields() {
        FieldHandlerChainBuilder fieldHandlerChainBuilder = FieldHandlerChainBuilder.build(this.classObjMap);
        this.classObjMap.values()
                .forEach(classObj -> Arrays
                        .stream(classObj.getClazz().getDeclaredFields())
                        .forEach(field -> fieldHandlerChainBuilder.handle(classObj.obj, field)));
    }

    private void init() {
        this.classObjMap.values()
                .stream()
                .map(ClassObj::getObj)
                .forEach(FunctionLifeCycle::init);
    }
    @Builder
    @Data
    static class ClassObj {
        private Class<? extends FunctionLifeCycle> clazz;
        private FunctionLifeCycle obj;
    }
}
